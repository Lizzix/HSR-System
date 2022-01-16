package com.esoe.container;

import java.util.Date;

import com.esoe.enums.SeatType;

public class Ticket {
    private Date date;
    private SeatType seat_type;
    private int amount;
    private int start_station_id;
    private int end_station_id;
    private int car_number;
    private int column_number;
    private char row_code;
    private int discount;
    private int price;

    public Ticket(Date date, SeatType seat_type, int amount, int start_station_id, int end_station_id, int car_number, int column_number, char row_code, int discount, int price) {
        this.date = date;
        this.seat_type = seat_type;
        this.amount = amount;
        this.start_station_id = start_station_id;
        this.end_station_id = end_station_id;
        this.car_number = car_number;
        this.column_number = column_number;
        this.row_code = row_code;
        this.discount = discount;
        this.price = price;
    }

    public int cost() {
        return 0;
    }

    public Date getDate() {
        return date;
    }

    public SeatType getSeat_type() {
        return seat_type;
    }

    String getSeatString() {
        String seat_string = String.format("%2d%d%c",
            car_number, column_number, row_code);
        return seat_string;
    }
    // TODO
    String getStartStationName() {
        // use id to find Zh-tw name in database
        return "";
    }
    // TODO
    String getEndStationName() {
        // use id to find Zh-tw name in database
        return "";
    }
}
