package com.example.administrator.boshide2.Tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BsdUtil {
    /**
     * 把日期转化为yyyy-MM-dd HH:mm:ss格式
     * @param date
     * @return
     */
    public static final String dateToStr(Date date) {
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.format(date);
        }
        return "";
    }
}
