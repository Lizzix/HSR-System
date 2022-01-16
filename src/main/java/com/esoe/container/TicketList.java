package com.esoe.container;

import java.util.*;

public class TicketList implements Aggregate {
    private Map<Ticket, Integer> ticket_map = new HashMap<>();
    private List<Integer> discount_list = new ArrayList<>();

    @Override
    public void add(Object obj1, Object Obj2) {

    }

    @Override
    public void remove(Object obj) {

    }

    @Override
    public Iterator getIterator() {
        return null;
    }
}