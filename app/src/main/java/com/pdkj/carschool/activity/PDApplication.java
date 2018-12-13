package com.pdkj.carschool.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.hss01248.dialog.ActivityStackManager;
import com.hss01248.dialog.StyledDialog;
import com.pdkj.carschool.bean.Qustion;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import cn.bmob.v3.Bmob;
import okhttp3.OkHttpClient;

public class PDApplication extends Application {

    public static  DbManager.DaoConfig getDaoConfig() {
        return new DbManager.DaoConfig()
                .setDbName("carSchool.db")//创建数据库的名称
                .setDbVersion(2)//数据库版本号
                .setDbUpgradeListener((db, oldVersion, newVersion) -> {
                        try {
                            db.dropTable(Qustion.class);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AutoLayoutConifg.getInstance().useDeviceSize();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        StyledDialog.init(this);
        Bmob.initialize(this, "4c97fcaac9b2548e99e7f95ea3a8285f");
        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityStackManager.getInstance().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityStackManager.getInstance().removeActivity(activity);
            }
        });
    }
}
