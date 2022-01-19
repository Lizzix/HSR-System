package com.esoe.enums;

public enum SeatType {
    STANDARD("STANDARD"), BUSINESS("BUSINESS");
    String name;

    SeatType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
