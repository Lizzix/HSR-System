package com.esoe.util;

import java.util.Calendar;

public class Util {
    static final public String[] WeekofDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    /**
     * 取得對應日期星期幾
     *
     * @param date 格式: yyyy-mm-dd
     * @return 星期幾字串(Monday 、 Tuesday 、 ...)
     */
    static public String date2DayOfWeek(String date) {

        String[] DateArray = date.split("-");
        int day = Integer.parseInt(DateArray[2]);
        int month = Integer.parseInt(DateArray[1]);
        int year = Integer.parseInt(DateArray[0]);
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day);
        String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int n;
        n = c.get(Calendar.DAY_OF_WEEK);
        return days[n - 1];
    }
}
