package com.pdkj.carschool.activity.base;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.pdkj.carschool.R;
import com.pdkj.carschool.utils.SharedPreferenceUtils;

import crossoverone.statuslib.StatusUtil;

public class SelectTypeActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_return;
    private ImageView im_huoche;
    private ImageView im_chuzuche;
    private ImageView im_keyun;
    private ImageView im_yayun;
    private ImageView im_weihuo;

    @Override
    protected int getLayout() {
        return R.layout.activity_selecr_type;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#33000000"));
        StatusUtil.setSystemStatus(this, true, true);
        rl_return = findViewById(R.id.rl_return);
        im_huoche = findViewById(R.id.im_huoche);
        im_chuzuche = findViewById(R.id.im_chuzuche);
        im_keyun = findViewById(R.id.im_keyun);
        im_yayun = findViewById(R.id.im_yayun);
        im_weihuo = findViewById(R.id.im_weihuo);
    }

    @Override
    protected void initEvent() {
        rl_return.setOnClickListener(this);
        im_huoche.setOnClickListener(this);
        im_chuzuche.setOnClickListener(this);
        im_keyun.setOnClickListener(this);
        im_yayun.setOnClickListener(this);
        im_weihuo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_return:
                finish();
                break;
            case R.id.im_huoche:
                setUserType("1","普货资格证");
                break;
            case R.id.im_chuzuche:
                setUserType("5","出租车资格证");
                break;
            case R.id.im_keyun:
                setUserType("2","客运资格证");
                break;
            case R.id.im_yayun:
                setUserType("4","押运资格证");
                break;
            case R.id.im_weihuo:
                setUserType("3","危货资格证");
                break;
        }
    }

    public enum ExamTypeId {
        普货资格证,客运资格证,危货资格证,押运资格证,出租车资格证,网约车资格证,再教育
    }

    private void setUserType(String userType,String type){
        StyledDialog.buildIosAlertVertical("提示！", "您确定将您的账号绑定为" + type + "类型吗？", new MyDialogListener() {
            @Override
            public void onFirst() {
                SharedPreferenceUtils.saveParams(mContext,"examTypeId",userType);
                finish();
            }

            @Override
            public void onSecond() {

            }
        }).show();

    }
}
