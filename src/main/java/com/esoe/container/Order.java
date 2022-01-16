package com.esoe.container;

import java.util.Date;

public class Order {
    private String code;
    private String uid;
    private TicketList TicketList;
    private int price;
    private Date expireDate;

    public Order(String code, String uid, TicketList ticketList, Date expireDate) {
        this.code = code;
        this.uid = uid;
        TicketList = ticketList;
        this.expireDate = expireDate;
    }
    // TODO: implement the method
    public Order createOrder(String code, String uid, TicketList ticketList, Date expireDate) {
        return new Order(code, uid, ticketList, expireDate);
    }


}
