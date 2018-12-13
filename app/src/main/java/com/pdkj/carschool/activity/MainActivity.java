package com.pdkj.carschool.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import crossoverone.statuslib.StatusUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.pdkj.carschool.R;
import com.pdkj.carschool.utils.SharedPreferenceUtils;
import com.zhy.autolayout.AutoLayoutActivity;

public class MainActivity extends AutoLayoutActivity implements View.OnClickListener {


    private ImageView tvQuestions;
    private long exitTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#33000000"));
        StatusUtil.setSystemStatus(this, true, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init(){
        tvQuestions = findViewById(R.id.tvQuestions);
        tvQuestions.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(TextUtils.isEmpty(SharedPreferenceUtils.getParams("token",this))){
            startActivity(new Intent(this,LoginActivity.class));
        }else{
            startActivity(new Intent(this,HomeNewActivity.class));
        }
        finish();
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