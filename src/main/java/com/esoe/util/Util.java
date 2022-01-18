package com.esoe.util;

import com.esoe.enums.DayOfWeek;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Util {

    private Util() {}

    /**
     * @return a random 8 bit digits [10000000, 99999999]
     */
    public static int getRandomCode() {
        Random random = new Random();
        int lastBits = random.nextInt(10000000);
        int firstBit = random.nextInt(9) + 1;
        String code = String.format("%d%07d", firstBit, lastBits);
        return Integer.parseInt(code);
    }

    public static DayOfWeek getDayOfWeek(Date date) {
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated
        String day = simpleDateformat.format(date);
        switch (day) {
            case "Mon":
                return DayOfWeek.MON;
            case "Tue":
                return DayOfWeek.TUE;
            case "Wed":
                return DayOfWeek.WED;
            case "Thu":
                return DayOfWeek.THU;
            case "Fri":
                return DayOfWeek.FRI;
            case "Sat":
                return DayOfWeek.SAT;
            case "Sun":
                return DayOfWeek.SUN;
            default:
                return null;
        }

    }

}
