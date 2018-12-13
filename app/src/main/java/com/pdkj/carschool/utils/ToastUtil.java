package com.pdkj.carschool.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Toast工具类
 * Created by Jorstin on 2015/3/17.
 */
public class ToastUtil {

    private static Handler handler = new Handler(Looper.getMainLooper());

    private static Toast toast = null;

    private static Object synObj = new Object();

    public static void showToast(Context context, final CharSequence msg) {
        showToast(context,msg, Toast.LENGTH_SHORT);
    }

    /**
     * 根据设置的文本显示
     *
     * @param msg
     */
    public static void showToast(Context context,final int msg) {
        showToast(context,msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示一个文本并且设置时长
     *
     * @param msg
     * @param len
     */
    public static void showToast(final Context context, final CharSequence msg, final int len) {
        if (msg == null || msg.equals("")) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (synObj) { //加上同步是为了每个toast只要有机会显示出来
                    if (toast != null) {
                        //toast.stop();
                        toast.setText(msg);
                        toast.setDuration(len);
                    } else {
                        toast = Toast.makeText(context.getApplicationContext(), msg, len);
                    }
                    toast.show();
                }
            }
        });
    }

    private static void showToastForGravity(final Context context, final CharSequence msg, final int gravity) {
        if (msg == null || msg.equals("")) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (synObj) { //加上同步是为了每个toast只要有机会显示出来
                    if (toast != null) {
                        //toast.stop();
                        toast.setText(msg);
                        toast.setDuration(Toast.LENGTH_SHORT);
                    } else {
                        toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
                    }
                    toast.setGravity(gravity, 0, 0);
                    toast.show();
                }
            }
        });
    }

    /**
     * 资源文件方式显示文本
     *
     * @param msg
     * @param len
     */
    public static void showToast(final Context context, final int msg, final int len) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (synObj) {
                    if (toast != null) {
                        //toast.stop();
                        toast.setText(msg);
                        toast.setDuration(len);
                    } else {
                        toast = Toast.makeText(context.getApplicationContext(), msg, len);
                    }
                    toast.show();
                }
            }
        });
    }

    public static void showToast(final View v, final CharSequence msg) {
        ((TextView) v).setText(msg);
    }
}
