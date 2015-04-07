package com.ted.lcmanager.app.util;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    /***
     * 获取默认格式的日期字符串
     * @param date
     * @return
     */
    public static String getFormatDateStr(final Date date){
        if(null == date)return null;
        return DateFormat.getDateInstance(DateFormat.DEFAULT).format(date);
    }

    public static Date FormatDateFromStr(final String dateStr){
        Date date = new Date();
        if(!TextUtils.isEmpty(dateStr)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
            try {
                date = sdf.parse(dateStr);
            }catch (Exception e){
                System.out.print("Error,format Date error");
            }
        }
        return date;
    }
}
