package com.esoe.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Trip {
    private int id;
    private int trainID;
    private short startStationID;
    private short destStationID;
    private byte direction;
    private Date updateDate;
    private Date effectiveDate;
    private Boolean serveMonday;
    private Boolean serveTuesday;
    private Boolean serveWednesday;
    private Boolean serveThursday;
    private Boolean serveFriday;
    private Boolean serveSaturday;
    private Boolean serveSunday;
}
