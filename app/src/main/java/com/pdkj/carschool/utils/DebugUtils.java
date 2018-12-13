package com.pdkj.carschool.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

public class DebugUtils {
    private static Boolean isDebug = null;

    public static boolean isDEBUG(){
        return isDebug ==null ? false: isDebug;
    }

    public static void syncIsDebug(Context context){
        if (isDebug == null){
            isDebug = context.getApplicationInfo()!=null &&
                    (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) !=0;
        }
    }

}
