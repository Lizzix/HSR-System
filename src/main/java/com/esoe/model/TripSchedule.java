package com.esoe.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TripSchedule {
    private int id;
    private int tripID;
    //TODO: check if these time need @Nullable
    private Time departNangang;
    private Time departTaipei;
    private Time departBanciao;
    private Time departTaoyuan;
    private Time departHsinchu;
    private Time departMiaoli;
    private Time departTaichung;
    private Time departChanghua;
    private Time departYunlin;
    private Time departChiayi;
    private Time departTainan;
    private Time departZuoying;
}
