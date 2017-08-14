package com.meo.stonymoon.enrichedday.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by A on 2017/8/14.
 */

public class DateUtil {
    public static String getDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }


}
