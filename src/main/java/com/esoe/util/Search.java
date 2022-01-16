package com.esoe.util;


import com.esoe.container.Order;
import com.esoe.container.TripList;
import com.esoe.enums.DiscountType;

import java.sql.Time;
import java.util.Date;

public class Search {

    public static TripList searchTrips(Date date, Time time, String from, String to, DiscountType discountType) {
        TripList trips = new TripList();
        // TODO implement here
        return trips;
    }

    public static TripList timeTable() {
        TripList trips = new TripList();
        // TODO implement here
        return trips;
    }

    public static Order searchOrder(int orderId) {
        // TODO implement here
        return null;
    }

    public static Order searchOrder(String code) {
        // TODO implement here
        return null;
    }

    public static Boolean getPriceTable() {
        // TODO implement here read csv
        return true;
    }


}
