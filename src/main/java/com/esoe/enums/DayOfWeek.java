package com.esoe.enums;

public enum DayOfWeek {
    MON(1, "MON"),
    TUE(2, "TUE"),
    WED(3, "WED"),
    THU(4, "THU"),
    FRI(5, "FRI"),
    SAT(6, "SAT"),
    SUN(7, "SUN");

    final int code;
    final String abbreviation;

    DayOfWeek(int code, String abbreviation) {
        this.code = code;
        this.abbreviation = abbreviation;
    }

    public int getCode() {
        return code;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

}
