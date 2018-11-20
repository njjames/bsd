package com.example.administrator.boshide2.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BsdUtil {
    /**
     * 把日期转化为yyyy-MM-dd HH:mm:ss格式
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.format(date);
        }
        return "";
    }

    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date strtodate = null;
        try {
            strtodate = formatter.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
        return strtodate;
    }
}
