package com.pdkj.carschool.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {



    /**
     * 将输入的时间与当前时间比较
     * @param date1
     * @return
     */
    public static boolean DateCompare24(String date1){
        long currentTime = System.currentTimeMillis();
        long dateTime = MyUtils.getInstance().string2Date("yyyy-MM-dd HH:mm",date1);
        int hour24 = 1000 * 60 * 60 * 24;//一天的时间
        return dateTime - currentTime >= hour24;
    }

    public static boolean DateCompare(String date1){
        long currentTime = System.currentTimeMillis();
        long dateTime =  MyUtils.getInstance().string2Date("yyyy-MM-dd HH:mm",date1);
        return dateTime - currentTime >0;
    }


    /**
     * 比较两个时间大小
     * @param date1   开始时间
     * @param date2   结束时间
     * @return
     */
    public static boolean startDateCompareEndDate(String date1, String date2){
        long date1Time =   MyUtils.getInstance().string2Date("yyyy-MM-dd HH:mm",date1);
        long date2Time = MyUtils.getInstance().string2Date("yyyy-MM-dd HH:mm",date2);
        return date2Time - date1Time >0;
    }

    public static String getNow()
    {
        return getFormatDate("yyyy-MM-dd HH:mm:ss",new Date());
    }

    /**
     * 根据格式和日期获取按格式显示的日期字符串
     * @param sFormat 格式
     * @param date    日期
     */
    public static String getFormatDate(String sFormat, Date date)
    {
        if (date == null)
            return null;

        if ((sFormat == "yy") || (sFormat == "yyyy") || (sFormat == "yyyy-MM")||(sFormat == "yy.MM.dd")||(sFormat == "yyyy.MM.dd")||
                (sFormat == "MM") || (sFormat == "dd") || (sFormat == "MM-dd") || (sFormat == "M-dd") ||
                (sFormat == "MM.dd")||(sFormat == "M/dd")||(sFormat == "MM/dd") || (sFormat == "yyyyMM") ||
                (sFormat == "yyyyMMdd") || (sFormat == "yyyy/MM") ||
                (sFormat == "yy/MM/dd") ||
                (sFormat == "yyyy/MM/dd") ||
                (sFormat == "yyyy-MM-dd") || (sFormat == "yyyy-MM-dd HH:mm:ss") ||
                (sFormat == "yyyy/MM/dd HH:mm:ss") || (sFormat == "M月d日 HH:mm")||
                (sFormat == "yyyyMMddHHmmss") || (sFormat == "yyyyMMddHHmmssSSS") ||
                (sFormat == "yyyy年MM月dd日") ||(sFormat == "yyyy年M月d日") || (sFormat == "yyyy年") ||
                (sFormat == "yyyy年MM月") || (sFormat == "MM月dd日") ||(sFormat == "M月d日") ||
                (sFormat == "dd日") || (sFormat == "HH") || (sFormat == "H") ||
                (sFormat == "mm") || (sFormat == "ss")|| (sFormat == "SSS") || (sFormat == "HH:mm")||
                (sFormat == "yyyy/MM/dd HH:mm")||(sFormat == "yyyy.MM.dd HH:mm")||(sFormat == "yyyy年M月d日 HH:mm")) {
            SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
            return formatter.format(date);
        }else{
            if ((sFormat == "HH:mm")) {
                sFormat = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
                return formatter.format(date).substring(11, 16);
            }
        }
        return null;
    }


    public static Date getDate(String sFormat, String date)
    {
        if ((date == null) || ("".equals(date)))
            return null;

        if ((sFormat == "yy") || (sFormat == "yyyy") ||
                (sFormat == "MM") || (sFormat == "dd") ||
                (sFormat == "MM/dd") || (sFormat == "yyyyMM") ||
                (sFormat == "yyyyMMdd") || (sFormat == "yyyy/MM") ||
                (sFormat == "yy/MM/dd") ||
                (sFormat == "yyyy/MM/dd") ||
                (sFormat == "yyyy-MM-dd") ||
                (sFormat == "yyyy/MM/dd HH:mm:ss") || (sFormat == "yyyy-MM-dd HH:mm:ss") ||
                (sFormat == "yyyyMMddHHmm") ||
                (sFormat == "yyyyMMddHHmmss") ||
                (sFormat == "yyyyMMdd-HHmmss") ||
                (sFormat == "yyyy年MM月dd日") ||
                (sFormat == "yyyy年MM月") || (sFormat == "MM月dd日") ||
                (sFormat == "dd日") || (sFormat == "HH") ||(sFormat == "MM.dd") ||
                (sFormat == "mm")) {
            SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
            try {
                return formatter.parse(date);
            }
            catch (Exception e) {
            }
        }else{
            if (sFormat == "HH:mm") {
                sFormat = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
                try {
                    return formatter.parse("1970-01-01 "+date+":00");
                }
                catch (Exception e) {
                }
            }
        }
        return null;
    }


}
