package com.pdkj.carschool.activity.base;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.necer.ndialog.NDialog;
import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.HomeActivity;
import com.pdkj.carschool.activity.HomeNewActivity;
import com.pdkj.carschool.activity.LoginActivity;
import com.pdkj.carschool.bean.SchoolListBean;
import com.pdkj.carschool.bean.TokenModel;
import com.pdkj.carschool.utils.NetConstant;
import com.pdkj.carschool.utils.SharedPreferenceUtils;
import com.pdkj.carschool.utils.SysConfig;
import com.pdkj.carschool.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import crossoverone.statuslib.StatusUtil;

public class DriverMessageActvity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_select_type;
    private RelativeLayout rl_school;
    private RelativeLayout rl_return;
    private List<String> list;
    private List<Integer> listInt;
    private String selectID = "";
    private TextView tv_school;
    private TextView tv_type;
    private Button btn_finish;


    @Override
    protected int getLayout() {
        return R.layout.activity_driver_message;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#33000000"));
        StatusUtil.setSystemStatus(this, true, true);
        rl_select_type = findViewById(R.id.rl_select_type);
        rl_school = findViewById(R.id.rl_school);
        rl_return = findViewById(R.id.rl_return);
        tv_school = findViewById(R.id.tv_school);
        btn_finish = findViewById(R.id.btn_finish);
        tv_type = findViewById(R.id.tv_type);
        list = new ArrayList<>();
        listInt = new ArrayList<>();
        getSchool();
    }

    @Override
    protected void initEvent() {
        rl_select_type.setOnClickListener(this);
        rl_school.setOnClickListener(this);
        rl_return.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_select_type:
                startActivity(SelectTypeActivity.class);
                break;
            case R.id.rl_school:
                showSchoolDialog();
                break;
            case R.id.rl_return:
                finish();
                break;
            case R.id.btn_finish:
                if(TextUtils.isEmpty(tv_school.getText().toString()) || TextUtils.isEmpty(tv_type.getText().toString())){
                    ToastUtil.showToast(mContext,"请选择信息！");
                }else{
                    upload();
                }

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(SharedPreferenceUtils.getStringValue(mContext,"examTypeId"))){
            int type = Integer.parseInt(SharedPreferenceUtils.getStringValue(mContext,"examTypeId"));
            switch (type){
                case 1:
                    tv_type.setText(ExamTypeId.普货资格证.toString());
                    break;
                case 2:
                    tv_type.setText(ExamTypeId.客运资格证.toString());
                    break;
                case 3:
                    tv_type.setText(ExamTypeId.危货资格证.toString());
                    break;
                case 4:
                    tv_type.setText(ExamTypeId.押运资格证.toString());
                    break;
                case 5:
                    tv_type.setText(ExamTypeId.出租车资格证.toString());
                    break;
                case 6:
                    tv_type.setText(ExamTypeId.网约车资格证.toString());
                    break;
                case 7:
                    tv_type.setText(ExamTypeId.再教育.toString());
                    break;
            }
        }
    }

    public enum ExamTypeId {
        普货资格证,客运资格证,危货资格证,押运资格证,出租车资格证,网约车资格证,再教育
    }

    private void getSchool(){
        RequestParams params = new RequestParams(NetConstant.BASE_URL + "/app/school/list");
        TokenModel tokenModel = new TokenModel(SharedPreferenceUtils.getIntValue(mContext,"userId"),SharedPreferenceUtils.getStringValue(mContext,"token"));
        params.addBodyParameter("model", new Gson().toJson(tokenModel));
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SchoolListBean schoolListBean = new Gson().fromJson(result,SchoolListBean.class);
                if(schoolListBean.getCode() == 200){
                    for(SchoolListBean.Data str:schoolListBean.getData()){
                        list.add(str.getName());
                        listInt.add(str.getId());
                    }
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

            }
        });
    }

    /**
     * 选择驾校
     */
    private void showSchoolDialog(){
        new NDialog(mContext)
                .setItems(list.toArray(new String[list.size()]))
                .setItemGravity(Gravity.LEFT)
                .setItemColor(Color.parseColor("#000000"))
                .setItemHeigh(50)
                .setItemSize(14)
                .setDividerHeigh(1)
                .setAdapter(null)
                .setDividerColor(Color.parseColor("#c1c1c1"))
                .setHasDivider(true)
                .setOnChoiceListener((item, which) -> {
                    selectID = listInt.get(which)+"";
                    SharedPreferenceUtils.saveParams(mContext,"schoolNum",selectID);
                    tv_school.setText(item);
                }).create(NDialog.CHOICE).show();
    }



    private void upload(){
        RequestParams params = new RequestParams(NetConstant.BASE_URL + "/app/usermember/editInfo");
        TokenModel tokenModel = new TokenModel(SharedPreferenceUtils.getIntValue(mContext,"userId"),SharedPreferenceUtils.getParams("token",mContext));
        params.addBodyParameter("model",new Gson().toJson(tokenModel));
        params.addBodyParameter("examTypeId",SharedPreferenceUtils.getParams("examTypeId",mContext));
        params.addBodyParameter("schoolId",SharedPreferenceUtils.getParams("schoolNum",mContext));
        params.addBodyParameter("sex",SharedPreferenceUtils.getParams("sex",mContext));
        params.addBodyParameter("schoolName",tv_school.getText().toString());
        params.addBodyParameter("name",SharedPreferenceUtils.getParams("userName",mContext));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SchoolListBean schoolListBean = new Gson().fromJson(result,SchoolListBean.class);
                if(schoolListBean.getCode() == 200){
                    if(SysConfig.getConfig(mContext).getXgToken().equals("true")){
                        startActivity(HomeNewActivity.class);
                        ToastUtil.showToast(mContext,"信息完善成功，请继续使用！");
                    }else{
                        ToastUtil.showToast(mContext,"注册成功，请登录！");
                        startActivity(LoginActivity.class);
                    }

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

            }
        });
    }
}
