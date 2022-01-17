package com.esoe.enums;

public enum SeatPosition {
    A_WINDOW("A"), B_MIDDLE("B"), C_AISLE("C"), D_AISLE("D"), E_WINDOW("E");
    final String codeStr;

    private SeatPosition(String codeStr) {
        this.codeStr = codeStr;
    }

    public String getCodeStr() {
        return codeStr;
    }
}
