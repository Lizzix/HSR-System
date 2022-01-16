package com.esoe.enums;

public enum StationName {
    南港(990, "南港", "Nangang"),
    台北(1000, "台北", "Taipei"),
    板橋(1010, "板橋", "Banciao"),
    桃園(1020, "桃園", "Taoyuan"),
    新竹(1030, "新竹", "Hsinchu"),
    苗栗(1035, "苗栗", "Miaoli"),
    台中(1040, "台中", "Taichung"),
    彰化(1043, "彰化", "Changhua"),
    雲林(1047, "雲林", "Yunlin"),
    嘉義(1050, "嘉義", "Chiayi"),
    台南(1060, "台南", "Tainan"),
    左營(1070, "左營", "Zuoying");

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
