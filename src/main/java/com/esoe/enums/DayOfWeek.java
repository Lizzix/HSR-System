package com.esoe.enums;

public enum DayOfWeek {
    MONDAY(1, "MON"),
    TUESDAY(2, "TUE"),
    WEDNESDAY(3, "WED"),
    THURSDAY(4, "THU"),
    FRIDAY(5, "FRI"),
    SATURDAY(6, "SAT"),
    SUNDAY(7, "SUN");

    final int code;
    final String Abbreviation;

    DayOfWeek(int code, String Abbreviation) {
        this.code = code;
        this.Abbreviation = Abbreviation;
    }

    public int getCode() {
        return code;
    }

    public String getAbbreviation() {
        return Abbreviation;
    }

}
