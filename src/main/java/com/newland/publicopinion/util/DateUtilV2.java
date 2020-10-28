package com.newland.publicopinion.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @Description:
 * @Author: Ljh
 * @Date 2020/6/3 17:30
 */
public class DateUtilV2 {
    public static Date getDate(long second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(second);
        return calendar.getTime();
    }
}
