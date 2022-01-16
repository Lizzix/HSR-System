package com.esoe.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Station {
    private int id;
    private String nameEn;
    private String nameZh_tw;
    private String address;

    public Station(int id, String nameEn, String nameZh_tw, String address) {
        this.id = id;
        this.nameEn = nameEn;
        this.nameZh_tw = nameZh_tw;
        this.address = address;
    }

    public Station() {
    }
}
