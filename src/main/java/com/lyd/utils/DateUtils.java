package com.lyd.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date getWeekBeg() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    public static Date getWeekEnd() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getWeekBeg());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return cal.getTime();
    }

}
