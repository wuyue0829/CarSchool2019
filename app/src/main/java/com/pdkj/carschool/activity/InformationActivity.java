package com.pdkj.carschool.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.base.BaseActivity;
import com.pdkj.carschool.activity.base.DriverMessageActvity;
import com.pdkj.carschool.utils.SharedPreferenceUtils;
import com.pdkj.carschool.utils.ToastUtil;

import crossoverone.statuslib.StatusUtil;

public class InformationActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_next;
    private EditText et_name;
    private RadioGroup rg_sex;
    @Override
    protected int getLayout() {
        return R.layout.activity_perfect_information;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#33000000"));
        StatusUtil.setSystemStatus(this, true, true);
        btn_next = findViewById(R.id.btn_next);
        et_name = findViewById(R.id.et_name);
        rg_sex = findViewById(R.id.rg_sex);
    }

    @Override
    protected void initEvent() {
        btn_next.setOnClickListener(this);
        rg_sex.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = findViewById(rg_sex.getCheckedRadioButtonId());
                if(radioButton.getText().toString().equals("男")){
                    SharedPreferenceUtils.saveParams(mContext,"sex","1");
                }else{
                    SharedPreferenceUtils.saveParams(mContext,"sex","2");
                }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                if(checkInput()){
                    startActivity(DriverMessageActvity.class);
                }
                break;
        }
    }

    private boolean checkInput(){
        SharedPreferenceUtils.saveParams(mContext,"userName",et_name.getText().toString());
        if(TextUtils.isEmpty(et_name.getText().toString())){
            ToastUtil.showToast(mContext,"请输入您的姓名");
            return false;
        }
        if(TextUtils.isEmpty(SharedPreferenceUtils.getParams("sex",mContext))){
            ToastUtil.showToast(mContext,"请选择您的性别");
            return false;
        }
        return true;
    }
}
