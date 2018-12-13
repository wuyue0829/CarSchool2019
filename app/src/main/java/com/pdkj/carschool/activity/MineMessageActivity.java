package com.pdkj.carschool.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.base.BaseActivity;
import com.pdkj.carschool.utils.AppInfoUtil;
import com.pdkj.carschool.utils.SharedPreferenceUtils;
import com.pdkj.carschool.utils.SysConfig;
import com.pdkj.carschool.utils.ToastUtil;

import crossoverone.statuslib.StatusUtil;

public class MineMessageActivity extends BaseActivity {

    private TextView tv_type;
    private TextView tv_pay;
    private TextView tv_version;
    private RelativeLayout rl_isPay;
    private RelativeLayout rl_version;
    private RelativeLayout rl_return;
    private RelativeLayout rl_about;

    @Override
    protected int getLayout() {
        return R.layout.activity_mine_message;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#33000000"));
        StatusUtil.setSystemStatus(this, true, true);
        tv_type = findViewById(R.id.tv_type);
        tv_pay = findViewById(R.id.tv_pay);
        tv_version = findViewById(R.id.tv_version);
        rl_isPay = findViewById(R.id.rl_isPay);
        rl_version = findViewById(R.id.rl_version);
        rl_return = findViewById(R.id.rl_return);
        rl_about = findViewById(R.id.rl_about);
    }

    @Override
    protected void initEvent() {
        rl_return.setOnClickListener(view -> finish());
        rl_version.setOnClickListener(view -> {
            ToastUtil.showToast(mContext,"当前版本是最新版本！");
        });


        rl_about.setOnClickListener(v -> startActivity(AboutMeActivity.class));

        tv_version.setText("V    " + AppInfoUtil.getAppVersionName(mContext));
        if(TextUtils.isEmpty(SharedPreferenceUtils.getParams("examTypeId",this))){
            rl_isPay.setClickable(false);
            tv_pay.setText("已付费");
        }else{
            tv_pay.setText("未付费");
            rl_isPay.setClickable(true);
            rl_isPay.setOnClickListener(view -> {
                StyledDialog.buildIosAlert("提示", "您确定付费开启Vip模式吗？", new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        startActivity(PayActivity.class);
                    }

                    @Override
                    public void onSecond() {
                    }
                }).show();
            });
        }
        switch (Integer.parseInt(SharedPreferenceUtils.getStringValue(mContext,"examTypeId"))){
            case 1:
                tv_type.setText(MineActivity.ExamTypeId.普货资格证.toString());
                break;
            case 2:
                tv_type.setText(MineActivity.ExamTypeId.客运资格证.toString());
                break;
            case 3:
                tv_type.setText(MineActivity.ExamTypeId.危货资格证.toString());
                break;
            case 4:
                tv_type.setText(MineActivity.ExamTypeId.押运资格证.toString());
                break;
            case 5:
                tv_type.setText(MineActivity.ExamTypeId.出租车资格证.toString());
                break;
            case 6:
                tv_type.setText(MineActivity.ExamTypeId.网约车资格证.toString());
                break;
            case 7:
                tv_type.setText(MineActivity.ExamTypeId.再教育.toString());
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SysConfig.getConfig(mContext).getUserVip(SharedPreferenceUtils.getParams("examTypeId",mContext)).equals("99")){
            rl_isPay.setClickable(false);
            tv_pay.setText("已付费");
        }else{
            tv_pay.setText("未付费");
            rl_isPay.setClickable(true);
            rl_isPay.setOnClickListener(view -> {
                StyledDialog.buildIosAlert("提示", "您确定付费开启Vip模式吗？", new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        startActivity(PayActivity.class);
                        finish();
                    }

                    @Override
                    public void onSecond() {

                    }
                }).show();
            });
        }
    }
}
