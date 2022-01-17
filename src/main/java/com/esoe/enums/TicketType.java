package com.esoe.enums;

public enum TicketType {
    STANDARD(100), BUSINESS(100), P90(90), P88(88), P80(80), P75(75), P65(65), P50(50);

    private final int percentage;

    TicketType(int percentage) {
        this.percentage = percentage;
    }

    public int getPercentage() {
        return percentage;
    }
}
