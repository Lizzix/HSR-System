package com.esoe.util;

import com.esoe.enums.SeatType;

import java.util.ArrayList;
import java.util.List;

public class Booking {

    public boolean bookTickets() {
        // TODO: implement booking algorithm
        int quantity = 0;
        SeatType seatType = SeatType.STANDARD;
        arrangeSeats(quantity, seatType);
        return true;
    }
    public List<String> arrangeSeats(int quantity, SeatType seatType) {
        List<String> seats = new ArrayList<>();
        // TODO: arrange seats algorithm
        return seats;
    }

}

