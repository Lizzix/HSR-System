package com.esoe.model;

import com.esoe.enums.DiscountType;
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
public class Ticket {
    private int id;
    private int orderID;
    private DiscountType discountType;
    private Date date;
    private short startStationID;
    private short destStationID;
    private SeatType seatType;
    private String seatCode;
}
