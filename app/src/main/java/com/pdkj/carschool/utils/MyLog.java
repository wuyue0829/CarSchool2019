package com.pdkj.carschool.utils;

import android.util.Log;

public class MyLog {

    private static final String TAG = "TAG";

    public static void i(String msg){
        Log.i(TAG,msg);
    }

    public static void e(String msg){
        Log.e(TAG,msg);
    }

    public static void i(String tag,String msg){
        Log.i(tag,msg);
    }

}
