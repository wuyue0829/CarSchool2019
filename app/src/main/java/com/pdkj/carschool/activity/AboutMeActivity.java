package com.pdkj.carschool.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.base.BaseActivity;

import crossoverone.statuslib.StatusUtil;

public class AboutMeActivity extends BaseActivity {

    private RelativeLayout rl_return;


    @Override
    protected int getLayout() {
        return R.layout.activity_about_me;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#33000000"));
        StatusUtil.setSystemStatus(this, true, true);
        rl_return = findViewById(R.id.rl_return);
    }

    @Override
    protected void initEvent() {
        rl_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
