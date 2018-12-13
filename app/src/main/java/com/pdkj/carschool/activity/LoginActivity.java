package com.pdkj.carschool.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hss01248.dialog.StyledDialog;
import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.base.BaseActivity;
import com.pdkj.carschool.bean.IsPayMemory;
import com.pdkj.carschool.bean.LoginBean;
import com.pdkj.carschool.bean.TokenModel;
import com.pdkj.carschool.utils.MyUtils;
import com.pdkj.carschool.utils.NetConstant;
import com.pdkj.carschool.utils.SharedPreferenceUtils;
import com.pdkj.carschool.utils.SysConfig;
import com.pdkj.carschool.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import crossoverone.statuslib.StatusUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private long exitTime = 0;
    private Button btn_regist;
    private Button btn_login;
    private TextView tv_forget_password;
    private AutoCompleteTextView actv_phone;
    private AutoCompleteTextView actv_password;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#33000000"));
        StatusUtil.setSystemStatus(this, true, true);
        btn_regist = (Button) findViewById(R.id.btn_regist);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);
        actv_phone = findViewById(R.id.actv_phone);
        actv_password = findViewById(R.id.actv_password);
    }

    @Override
    protected void initEvent() {
        btn_regist.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_regist:
                startActivity(RegistActivity.class);
                break;
            case R.id.btn_login:
                if(checkInput()){
                    login(actv_phone.getText().toString().trim(),actv_password.getText().toString().trim());
                }
                break;
            case R.id.tv_forget_password:
                startActivity(ForgetPasswordActivity.class);
                break;
        }
    }



    private boolean checkInput(){
        if(TextUtils.isEmpty(actv_phone.getText().toString().trim())){
            ToastUtil.showToast(getApplicationContext(),"请输入手机号码");
            return false;
        }

        if(!MyUtils.getInstance().isMobileNO(actv_phone.getText().toString().trim())){
            ToastUtil.showToast(getApplicationContext(),"请输入正确的手机号码");
            return false;
        }
        if(TextUtils.isEmpty(actv_password.getText().toString().trim())){
            ToastUtil.showToast(getApplicationContext(),"请输入密码");
            return false;
        }
        return true;
    }

    private void login(String phone,String password){
        StyledDialog.buildLoading("登录中...").show();
        RequestParams params = new RequestParams(NetConstant.BASE_URL + "/api/login/memberlogin");
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("password", password);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LoginBean loginBeanCla = new Gson().fromJson(result,LoginBean.class);
                if(loginBeanCla.getCode().equals("200")){
                    SharedPreferenceUtils.saveParams(mContext,"token",loginBeanCla.getData().getToken().getToken());
                    SharedPreferenceUtils.saveIntValue(mContext,"userId",loginBeanCla.getData().getToken().getUserId());
                    SharedPreferenceUtils.saveStringValue(mContext,"schoolName",loginBeanCla.getData().getInformationBean()
                    .getSchoolName());
                    SharedPreferenceUtils.saveStringValue(mContext,"userName",loginBeanCla.getData().getInformationBean()
                            .getName());
                    SharedPreferenceUtils.saveParams(mContext,"examTypeId",loginBeanCla.getData().getInformationBean().getExamTypeId()+"");
                    ToastUtil.showToast(mContext,"登录成功");



                    for(int i = 1;i<6;i++){
                        RequestParams params1 = new RequestParams(NetConstant.BASE_URL + "/app/usermember/isPayment");
                        TokenModel tokenMode2 = new TokenModel(SharedPreferenceUtils.getIntValue(mContext,"userId"),SharedPreferenceUtils.getParams("token",mContext));
                        params1.addBodyParameter("model",new Gson().toJson(tokenMode2));
                        params1.addBodyParameter("examTypeId", i+"");
                        int finalI = i;
                        x.http().post(params1, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                IsPayMemory isPayMemory = new Gson().fromJson(result, IsPayMemory.class);
                                if(isPayMemory.isData()){
                                    SysConfig.getConfig(mContext).setUserVip("99", finalI +"");
                                }
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {
                            }

                            @Override
                            public void onFinished() {

                            }
                        });

                    }


                    startActivity(HomeNewActivity.class);
                    finish();
                }else{
                    ToastUtil.showToast(mContext,loginBeanCla.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e(cex.getMessage());
            }

            @Override
            public void onFinished() {
                StyledDialog.dismissLoading(mActivity);
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
