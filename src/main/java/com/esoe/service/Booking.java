package com.esoe.service;

import com.esoe.dao.OrderDAO;
import com.esoe.dao.TicketDAO;
import com.esoe.enums.*;
import com.esoe.exception.BLException;
import com.esoe.model.Order;
import com.esoe.model.Ticket;
import com.esoe.util.Util;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private static final OrderDAO orderDAO = new OrderDAO();
    private static final TicketDAO ticketDAO = new TicketDAO();

    private Order order = null;
    private List<Ticket> tickets = null;

    String userID;
    int quantity;
    String date;
    int tripID;
    StationName start;
    StationName dest;
    SeatPreference pref;
    SeatType seatType;
    DiscountType discountType;

    public Booking(Order order) {
        this.order = order;
        this.tickets = ticketDAO.list(order.getId());
    }

    public static boolean cancelOrder(int orderId, String userId) throws BLException {
        Optional<Order> searchResult = orderDAO.get(orderId, userId);
        if (searchResult.isEmpty()) {
            throw new BLException("Order not found");
        }
        List<Ticket> ticketList = ticketDAO.list(orderId);
        for (Ticket ticket : ticketList) {
            ticketDAO.delete(ticket.getId());
        }
        orderDAO.delete(orderId);
        return true;
    }

    boolean bookTickets() throws BLException {
        if (quantity >= 10 || quantity <= 0) {
            throw new BLException("Invalid quantity");
        }
        Date ticketDate = Util.stringToDate(date);
        assert ticketDate != null;
        Date todayDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(todayDate);
        c.add(Calendar.DATE, 28);
        todayDate = c.getTime();
        if (ticketDate.after(todayDate)) {
            throw new BLException("Invalid date");
        }
        int ticketID = ticketDAO.getNextID();
        Seat seat = new Seat(date,start,dest,quantity, pref,seatType);
        List<String> seatList = seat.arrangeSeats();
        if (seatList.size() < quantity) {
            throw new BLException("Not enough seats");
        }
        seatList = seatList.subList(0, quantity);
        int orderID = Util.getRandomCode();
        Date payDeadline = Util.datePlusDays(todayDate, 3);
        int payment = 0; // TODO
        this.order = new Order(orderID, userID, payDeadline, payment);
        orderDAO.create(this.order);
        for (String seatCode : seatList) {
            Ticket ticket = new Ticket(ticketID, orderID, discountType, ticketDate, start.getCode(), dest.getCode(), seatType, seatCode);
            ticketDAO.create(ticket);
            ticketID++;
            this.tickets.add(ticket);
        }
        seat.updateReservedSeats(seatList, ticketID);
        return true;
    }

    // TODO: Modify Order
    boolean modifyOrder() {
        return false;
    }

    public Order getOrder() {
        return order;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
}