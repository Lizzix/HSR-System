package com.esoe.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Station {
    private int id;
    private String name_En;
    private String name_Zh_tw;
    private String address;

    public Station(){
    }

    public Station(int id, String name_En, String name_Zh_tw, String address) {
        this.id = id;
        this.name_En = name_En;
        this.name_Zh_tw = name_Zh_tw;
        this.address = address;
    }
}
