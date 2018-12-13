package com.pdkj.carschool.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hss01248.dialog.StyledDialog;
import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.base.BaseActivity;
import com.pdkj.carschool.bean.LoginBeanCla;
import com.pdkj.carschool.bean.RegisterBean;
import com.pdkj.carschool.utils.CountDownUtil;
import com.pdkj.carschool.utils.NetConstant;
import com.pdkj.carschool.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import crossoverone.statuslib.StatusUtil;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_return;
    private TextView btn_message;
    private AutoCompleteTextView actv_phone;
    private AutoCompleteTextView actv_yzm;
    private AutoCompleteTextView actv_password;
    private AutoCompleteTextView actv_repassword;
    private ImageView im_quered;
    private TextView btn_regist;

    @Override
    protected int getLayout() {
        return R.layout.activity_getpassword;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#33000000"));
        StatusUtil.setSystemStatus(this, true, true);
        rl_return = findViewById(R.id.rl_return);
        btn_message = findViewById(R.id.btn_message);
        actv_phone = findViewById(R.id.actv_phone);
        actv_yzm = findViewById(R.id.actv_yzm);
        actv_password = findViewById(R.id.actv_password);
        actv_repassword = findViewById(R.id.actv_repassword);
        im_quered = findViewById(R.id.im_quered);
        btn_regist = findViewById(R.id.btn_regist);
    }

    @Override
    protected void initEvent() {
        rl_return.setOnClickListener(this);
        btn_message.setOnClickListener(this);
        btn_regist.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_return:
                finish();
                break;
            case R.id.btn_message:
                if(TextUtils.isEmpty(actv_phone.getText().toString())){
                    Toast.makeText(getApplicationContext(),"请输入要修改密码的电话号码！",Toast.LENGTH_LONG).show();
                }else{
                    getMessage(actv_phone.getText().toString());
                }
                break;
            case R.id.btn_regist:
                if(checkPassword(actv_password.getText().toString(),actv_repassword.getText().toString())){
                    xiugaiPassword(actv_phone.getText().toString(),actv_yzm.getText().toString(),actv_password.getText().toString());
                }
                break;
        }
    }

    private void getMessage(String phone){
        StyledDialog.buildLoading("获取验证码...").show();
        RequestParams params = new RequestParams(NetConstant.BASE_URL + "/api/login/mobileCode");
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("type", "find");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LoginBeanCla loginBean = new Gson().fromJson(result, LoginBeanCla.class);
                LogUtil.e(loginBean.getData());
                if(loginBean.getCode().equals("200")){
                    new CountDownUtil(btn_message)
                            .setCountDownMillis(60_000L)//倒计时60000ms
                            .setCountDownColor(android.R.color.white,android.R.color.white)//不同状态字体颜色
                            .start();
                }else{
                    ToastUtil.showToast(getApplicationContext(),loginBean.getMessage());
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

    private boolean checkPassword(String password1,String password2){
        if(TextUtils.isEmpty(actv_yzm.getText().toString())){
            ToastUtil.showToast(mContext,"请填写验证码！");
            return false;
        }
        if(TextUtils.isEmpty(password1) ||TextUtils.isEmpty(password2) ){
            ToastUtil.showToast(mContext,"请填写密码！");
            return false;
        }else{
            if(password1.equals(password2)){
                return true;
            }else{
                ToastUtil.showToast(mContext,"两次填写的密码不一致！");
                return false;
            }
        }
    }

    private void xiugaiPassword(String phone,String message,String password){
        StyledDialog.buildLoading("提交数据中...").show();
        RequestParams params = new RequestParams(NetConstant.BASE_URL + "/api/login/editPassword");
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("password", password);
        params.addBodyParameter("mobileCode", message);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e(result);
                RegisterBean registerBean = new Gson().fromJson(result,RegisterBean.class);
                if(registerBean.getCode().equals("200")){
                    ToastUtil.showToast(mContext,"密码修改成功，请重新登录！");
                    finish();
                }else{
                    ToastUtil.showToast(mContext,registerBean.getMessage());
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
}
