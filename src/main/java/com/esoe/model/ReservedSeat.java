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
    private int TicketID;
    private short stationID;
    private Date date;
    private int car;
    private int row;
    private String col;

    public ReservedSeat(int ticketID, short stationID, Date date, int car, int row, String col) {
        TicketID = ticketID;
        this.stationID = stationID;
        this.date = date;
        this.car = car;
        this.row = row;
        this.col = col;
    }
}
