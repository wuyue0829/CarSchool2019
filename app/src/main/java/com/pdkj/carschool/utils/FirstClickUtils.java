package com.pdkj.carschool.utils;

public class FirstClickUtils {

    private static long lastClickTime;

    public static boolean isClickSoFast(long gapTime) {
        long currentTime = System.currentTimeMillis();
        long time = currentTime - lastClickTime;
        if (time > 0 && time < gapTime) {
            return true;
        }
        lastClickTime = currentTime;
        return false;
    }

}
