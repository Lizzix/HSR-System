package com.esoe.service;

import com.esoe.dao.DiscountDAO;
import com.esoe.dao.OrderDAO;
import com.esoe.dao.PriceDAO;
import com.esoe.dao.TicketDAO;
import com.esoe.enums.*;
import com.esoe.exception.BLException;
import com.esoe.model.Discount;
import com.esoe.model.Order;
import com.esoe.model.Price;
import com.esoe.model.Ticket;
import com.esoe.util.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Booking {

    private static final OrderDAO orderDAO = new OrderDAO();
    private static final TicketDAO ticketDAO = new TicketDAO();
    private static final DiscountDAO discountDAO = new DiscountDAO();
    private static final PriceDAO priceDAO = new PriceDAO();

    private Order order = null;
    private List<Ticket> tickets = null;
    DiscountType discountType;
    // Input Values
    String userID;
    int uniQuantity;
    int genQuantity;
    int quantity;
    String date;
    int tripID;
    StationName start;
    StationName dest;
    SeatPreference pref;
    SeatType seatType;


    private Booking() {}

    public Booking(String userID, int uniQuantity, int genQuantity, String date, int tripID, StationName start, StationName dest, SeatPreference pref, SeatType seatType, DiscountType discountType) {
        this.userID = userID;
        this.uniQuantity = uniQuantity;
        this.genQuantity = genQuantity;
        this.quantity = uniQuantity + genQuantity;
        this.date = date;
        this.tripID = tripID;
        this.start = start;
        this.dest = dest;
        this.pref = pref;
        this.seatType = seatType;
        this.discountType = discountType;
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
        if (discountType == DiscountType.UNIVERSITY && uniQuantity == 0) {
            discountType = DiscountType.NONE;
        }
        Date tripDate = Util.stringToDate(date);
        assert tripDate != null;
        Date todayDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(todayDate);
        c.add(Calendar.DATE, 28);
        todayDate = c.getTime();
        if (tripDate.after(todayDate)) {
            throw new BLException("Invalid date");
        }
        Seat seat = new Seat(date,start,dest,quantity, pref,seatType);
        List<String> seatList = seat.arrangeSeats();
        if (seatList.size() < quantity) {
            throw new BLException("Not enough seats");
        }
        updateData(seatList, seat, tripDate);
        return true;
    }

    private void updateData(List<String> seatList, Seat seat, Date tripDate) throws BLException {
        int ticketID = ticketDAO.getNextID();
        seatList = seatList.subList(0, quantity);
        int orderID = Util.getRandomCode();
        Date payDeadline = Util.datePlusDays(new Date(), 3);
        int payment = calPrice();
        this.order = new Order(orderID, userID, payDeadline, payment);
        orderDAO.create(this.order);
        for (String seatCode : seatList) {
            Ticket ticket = new Ticket(ticketID, orderID, discountType, tripDate, start.getCode(), dest.getCode(), seatType, seatCode);
            ticketDAO.create(ticket);
            ticketID++;
            this.tickets.add(ticket);
        }
        seat.updateReservedSeats(seatList, ticketID);
    }

    private int calPrice() throws BLException {
        int basePrice = 0;
        int basePriceUni = 0;
        if (discountType == DiscountType.NONE) {
            Optional<Price> p = priceDAO.get(start, dest, seatType.getName());
            if (p.isEmpty()) {
                throw new BLException("Price not found");
            }
            basePrice = p.get().getPrice();
        }
        if (discountType == DiscountType.EARLY) {
            basePrice = getBasePrice();
        }
        if (discountType == DiscountType.UNIVERSITY) {
            basePriceUni = getBasePrice();
        }
        return basePrice * genQuantity + basePriceUni * uniQuantity;
    }


    private int getBasePrice() throws BLException {
        int percentage;
        Optional<Price> basePrice;
        List<Discount> discounts = discountDAO.list(discountType, tripID, Util.stringToDate(date));
        if (discounts.isEmpty()) {
            throw new BLException("No discount found");
        }
        percentage =  discounts.get(0).getPercentage();
        String ticketTYpe = "P" + percentage;
        basePrice = priceDAO.get(start, dest, ticketTYpe);
        if (basePrice.isEmpty()) {
            return 0;
        }
        return basePrice.get().getPrice();
    }

    public Order getOrder() {
        return order;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
}