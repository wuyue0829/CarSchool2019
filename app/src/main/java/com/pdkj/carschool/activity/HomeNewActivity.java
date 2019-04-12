package com.pdkj.carschool.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.base.BaseActivity;
import com.pdkj.carschool.bean.version;
import com.pdkj.carschool.utils.SharedPreferenceUtils;
import com.pdkj.carschool.utils.SysConfig;
import com.pdkj.carschool.utils.ToastUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import crossoverone.statuslib.StatusUtil;
import util.UpdateAppUtils;

public class HomeNewActivity extends BaseActivity implements View.OnClickListener {


    private RelativeLayout rl_return;
    private ImageView im_huoche;
    private ImageView im_chuzuche;
    private ImageView im_keyun;
    private ImageView im_yayun;
    private ImageView im_weihuo;
    private long exitTime = 0;
    private TextView tv_title;
    private LinearLayout ly_fufei_one;
    private LinearLayout ly_fufei_two;
    private Button bt_huoche_fufei;
    private Button bt_chuzuche_fufei;
    private Button bt_keyun_fufei;
    private Button bt_yayun_fufei;
    private Button bt_weihuo_fufei;

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
        tv_title = findViewById(R.id.tv_title);
        ly_fufei_one = findViewById(R.id.ly_fufei_one);
        ly_fufei_two = findViewById(R.id.ly_fufei_two);
        bt_huoche_fufei = findViewById(R.id.bt_huoche_fufei);
        bt_chuzuche_fufei = findViewById(R.id.bt_chuzuche_fufei);
        bt_keyun_fufei = findViewById(R.id.bt_keyun_fufei);
        bt_yayun_fufei = findViewById(R.id.bt_yayun_fufei);
        bt_weihuo_fufei = findViewById(R.id.bt_weihuo_fufei);
    }

    @Override
    protected void initEvent() {
        rl_return.setVisibility(View.GONE);
        im_huoche.setOnClickListener(this);
        im_chuzuche.setOnClickListener(this);
        im_keyun.setOnClickListener(this);
        im_yayun.setOnClickListener(this);
        im_weihuo.setOnClickListener(this);
        tv_title.setText("安妍驾考");
        ly_fufei_one.setVisibility(View.VISIBLE);
        ly_fufei_two.setVisibility(View.VISIBLE);
        BmobQuery<version> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(mContext,"", new GetListener<version>() {
            @Override
            public void onSuccess(version version) {
                UpdateAppUtils.from(HomeNewActivity.this)
                        .checkBy(UpdateAppUtils.CHECK_BY_VERSION_CODE) //更新检测方式，默认为VersionCode
                        .serverVersionCode(version.getVersionCode())
                        .apkPath(version.getDownload().getFileUrl(mContext))
                        .showNotification(true) //是否显示下载进度到通知栏，默认为true
                        .downloadBy(UpdateAppUtils.DOWNLOAD_BY_BROWSER) //下载方式：app下载、手机浏览器下载。默认app下载
                        .isForce(false) //是否强制更新，默认false 强制更新情况下用户不同意更新则不能使用app
                        .update();
            }

            @Override
            public void onFailure(int i, String s) {

            }

        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(SysConfig.getConfig(mContext).getUserVip("1").equals("99")){
            bt_huoche_fufei.setText("已付费");
            bt_huoche_fufei.setOnClickListener(v -> ToastUtil.showToast(mContext,"您已经付费解锁了货车题库，请点击进入！"));
        }else{
            bt_huoche_fufei.setText("去付费");
            bt_huoche_fufei.setOnClickListener(v -> {
                SharedPreferenceUtils.saveParams(mContext,"examTypeId","1");
                startActivity(PayActivity.class);
            });
        }

        if(SysConfig.getConfig(mContext).getUserVip5().equals("99")){
            bt_chuzuche_fufei.setText("已付费");
            bt_chuzuche_fufei.setOnClickListener(v -> ToastUtil.showToast(mContext,"您已经付费解锁了出租车题库，请点击进入！"));
        }else{
            bt_chuzuche_fufei.setText("去付费");
            bt_chuzuche_fufei.setOnClickListener(v -> {
                SharedPreferenceUtils.saveParams(mContext,"examTypeId","5");
                startActivity(PayActivity.class);
            });
        }

        if(SysConfig.getConfig(mContext).getUserVip2().equals("99")){
            bt_keyun_fufei.setText("已付费");
            bt_keyun_fufei.setOnClickListener(v -> ToastUtil.showToast(mContext,"您已经付费解锁了客运题库，请点击进入！"));
        }else{
            bt_keyun_fufei.setText("去付费");
            bt_keyun_fufei.setOnClickListener(v -> {
                SharedPreferenceUtils.saveParams(mContext,"examTypeId","2");
                startActivity(PayActivity.class);
            });
        }

        if(SysConfig.getConfig(mContext).getUserVip4().equals("99")){
            bt_yayun_fufei.setText("已付费");
            bt_yayun_fufei.setOnClickListener(v -> ToastUtil.showToast(mContext,"您已经付费解锁了押运题库，请点击进入！"));
        }else{
            bt_yayun_fufei.setText("去付费");
            bt_yayun_fufei.setOnClickListener(v -> {
                SharedPreferenceUtils.saveParams(mContext,"examTypeId","4");
                startActivity(PayActivity.class);
            });

        }

        if(SysConfig.getConfig(mContext).getUserVip3().equals("99")){
            bt_weihuo_fufei.setText("已付费");
            bt_weihuo_fufei.setOnClickListener(v -> ToastUtil.showToast(mContext,"您已经付费解锁了危货题库，请点击进入！"));
        }else{
            bt_weihuo_fufei.setText("去付费");
            bt_weihuo_fufei.setOnClickListener(v -> {
                SharedPreferenceUtils.saveParams(mContext,"examTypeId","3");
                startActivity(PayActivity.class);
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.im_huoche:
                SharedPreferenceUtils.saveParams(mContext,"examTypeId","1");
                startActivity(HomeActivity.class);
                break;
            case R.id.im_chuzuche:
                SharedPreferenceUtils.saveParams(mContext,"examTypeId","5");
                startActivity(HomeActivity.class);
                break;
            case R.id.im_keyun:
                SharedPreferenceUtils.saveParams(mContext,"examTypeId","2");
                startActivity(HomeActivity.class);
                break;
            case R.id.im_yayun:
                SharedPreferenceUtils.saveParams(mContext,"examTypeId","4");
                startActivity(HomeActivity.class);
                break;
            case R.id.im_weihuo:
                SharedPreferenceUtils.saveParams(mContext,"examTypeId","3");
                startActivity(HomeActivity.class);
                break;
        }
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
