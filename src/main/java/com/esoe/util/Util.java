package com.esoe.util;

import com.esoe.enums.Car;
import com.esoe.enums.DayOfWeek;
import com.esoe.enums.StationName;

import java.text.SimpleDateFormat;
import java.util.*;

public class Util {
    private static final Random random = new Random();
    private Util() {}

    /**
     * @return a random 8 bit digits [10000000, 99999999]
     */
    public static int getRandomCode() {
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

    public static String getCurrentTimeStr() {
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateformat.format(new Date());
    }

    public static String getToday() {
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateformat.format(new Date());
    }

    public static Date stringToDate(String date) {
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateformat.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean goSouth(StationName start, StationName dest) {
        return start.getCode() < dest.getCode();
    }

    public static List<StationName> getContinuousStations(StationName start, StationName dest) {
        List<StationName> stations = new ArrayList<>();
        if (goSouth(start, dest)) {
            for (int i = start.getCode(); i <= dest.getCode(); i++) {
                stations.add(StationName.getStationByCode(i));
            }
        } else {
            for (int i = start.getCode(); i >= dest.getCode(); i--) {
                stations.add(StationName.getStationByCode(i));
            }
        }
        return stations;
    }

    public static Car NumToCar(int num) {
        switch (num) {
            case 1:
                return Car.one;
            case 2:
                return Car.two;
            case 3:
                return Car.three;
            case 4:
                return Car.four;
            case 5:
                return Car.five;
            case 6:
                return Car.six;
            case 7:
                return Car.seven;
            case 8:
                return Car.eight;
            case 9:
                return Car.nine;
            case 10:
                return Car.ten;
            case 11:
                return Car.eleven;
            case 12:
                return Car.twelve;
            default:
                return null;
        }
    }

    public static Date datePlusDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static StationName strToStationName (String str) {
        if (str.equals("南港")) return StationName.Nangang;
        if (str.equals("Nangang")) return StationName.Nangang;
        if (str.equals("台北")) return StationName.Taipei;
        if (str.equals("Taipei")) return StationName.Taipei;
        if (str.equals("板橋")) return StationName.Banciao;
        if (str.equals("Banciao")) return StationName.Banciao;
        if (str.equals("桃園")) return StationName.Taoyuan;
        if (str.equals("Taoyuan")) return StationName.Taoyuan;
        if (str.equals("新竹")) return StationName.Hsinchu;
        if (str.equals("Hsinchu")) return StationName.Hsinchu;
        if (str.equals("苗栗")) return StationName.Miaoli;
        if (str.equals("Miaoli")) return StationName.Miaoli;
        if (str.equals("台中")) return StationName.Taichung;
        if (str.equals("Taichung")) return StationName.Taichung;
        if (str.equals("彰化")) return StationName.Changhua;
        if (str.equals("Changhua")) return StationName.Changhua;
        if (str.equals("雲林")) return StationName.Yunlin;
        if (str.equals("Yunlin")) return StationName.Yunlin;
        if (str.equals("嘉義")) return StationName.Chiayi;
        if (str.equals("Chiayi")) return StationName.Chiayi;
        if (str.equals("台南")) return StationName.Tainan;
        if (str.equals("Tainan")) return StationName.Tainan;
        if (str.equals("左營")) return StationName.Zuoying;
        if (str.equals("Zuoying")) return StationName.Zuoying;
        return null;
    }
}
