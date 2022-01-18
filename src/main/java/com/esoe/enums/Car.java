package com.esoe.enums;

public enum Car {

    one(1,13),
    two(2,20),
    three(3,18),
    four(4,20),
    five(5,17),
    six(6,17),
    seven(7,12),
    eight(8,20),
    nine(9,18),
    ten(10,20),
    eleven(11,18),
    twelve(12,14);

    int no;
    int maxRow;

    Car(int no, int maxRow) {
        this.no = no;
        this.maxRow = maxRow;
    }

    public int getNo() {
        return no;
    }

    public int getMaxRow() {
        return maxRow;
    }
}
