package com.esoe.model;

import com.esoe.enums.SeatType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReservedSeat {
    private int id;
    private int ticktID;
    private short stationID;
    private Date date;
    private byte car;
    private byte row;
    private char col;
    private SeatType seatType;
}
