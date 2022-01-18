package com.esoe.service;

import com.esoe.enums.SeatType;
import com.esoe.model.Order;
import com.esoe.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private Order order;
    private List<Ticket> tickets;

    public Booking(Order order) {
        this.order = order;
    }

    public Order createOrder(int trip_id, int quantity, SeatType seatType) {
        Order newOrder = new Order();

        return newOrder;
    }

    public List<Ticket> createTickets(int order_id, int trip_id, int seat_id, SeatType seatType) {
        List<Ticket> newTickets = new ArrayList<>();


        return newTickets;
    }

    public boolean bookTickets() {
        // TODO: implement booking algorithm
        // + why booking fail
        // 1. seat not available
        // 2. exceed max quantity
        // 3. can't book yet (date)
        int quantity = 0;
        SeatType seatType = SeatType.STANDARD;
        arrangeSeats(quantity, seatType);
        return true;
    }
    public List<String> arrangeSeats(int quantity, SeatType preferredSeatType) {
        List<String> seats = new ArrayList<>();
        // TODO: arrange seats algorithm

        return seats;
    }

    public boolean cancelOrder() {
        // TODO: implement cancel order algorithm
        // + why cancel fail
        return true;
    }


}

