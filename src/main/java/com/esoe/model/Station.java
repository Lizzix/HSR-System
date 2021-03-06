package com.esoe.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Station {
    private int id;
    private String nameEn;
    private String nameZh_tw;
    private String address;
}
