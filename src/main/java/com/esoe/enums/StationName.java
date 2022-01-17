package com.esoe.enums;

public enum StationName {
    Nangang((short)990, "南港", "Nangang"),
    Taipei((short)1000, "台北", "Taipei"),
    Banciao((short)1010, "板橋", "Banciao"),
    Taoyuan((short)1020, "桃園", "Taoyuan"),
    Hsinchu((short)1030, "新竹", "Hsinchu"),
    Miaoli((short)1035, "苗栗", "Miaoli"),
    Taichung((short)1040, "台中", "Taichung"),
    Changhua((short)1043, "彰化", "Changhua"),
    Yunlin((short)1047, "雲林", "Yunlin"),
    Chiayi((short)1050, "嘉義", "Chiayi"),
    Tainan((short)1060, "台南", "Tainan"),
    Zuoying((short)1070, "左營", "Zuoying");

    private final short code;
    private final String name_Zh_tw;
    private final String name_En;

    StationName(short code, String name_Zh_tw, String name_En) {
        this.code = code;
        this.name_Zh_tw = name_Zh_tw;
        this.name_En = name_En;
    }


    public short getCode() {
        return code;
    }
    public String getName_Zh_tw() {
        return name_Zh_tw;
    }
    public String getName_En() {
        return name_En;
    }

}
