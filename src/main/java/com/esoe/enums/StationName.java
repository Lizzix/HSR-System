package com.esoe.enums;

public enum StationName {
    Nangang(990, "南港", "Nangang"),
    Taipei(1000, "台北", "Taipei"),
    Banciao(1010, "板橋", "Banciao"),
    Taoyuan(1020, "桃園", "Taoyuan"),
    Hsinchu(1030, "新竹", "Hsinchu"),
    Miaoli(1035, "苗栗", "Miaoli"),
    Taichung(1040, "台中", "Taichung"),
    Changhua(1043, "彰化", "Changhua"),
    Yunlin(1047, "雲林", "Yunlin"),
    Chiayi(1050, "嘉義", "Chiayi"),
    Tainan(1060, "台南", "Tainan"),
    Zuoying(1070, "左營", "Zuoying");

    private final int code;
    private final String name_Zh_tw;
    private final String name_En;

    StationName(int code, String name_Zh_tw, String name_En) {
        this.code = code;
        this.name_Zh_tw = name_Zh_tw;
        this.name_En = name_En;
    }

    public int getCode() {
        return code;
    }
    public String getName_Zh_tw() {
        return name_Zh_tw;
    }
    public String getName_En() {
        return name_En;
    }

}
