package com.esoe.service;

import com.esoe.dao.DiscountDAO;
import com.esoe.dao.TripDAO;
import com.esoe.dao.TripScheduleDAO;
import com.esoe.enums.DiscountType;
import com.esoe.enums.StationName;
import com.esoe.model.Discount;
import com.esoe.model.Trip;
import com.esoe.model.TripSchedule;

import java.util.*;

public class Search {

    private static TripDAO tripDAO = new TripDAO();
    private static DiscountDAO discountDAO = new DiscountDAO();
    private static TripScheduleDAO tripScheduleDAO = new TripScheduleDAO();

    private HashMap<Trip, Optional> getMap(List<Trip> trips) {
        Optional<TripSchedule> tripSchedule;
        HashMap<Trip, Optional> result = new HashMap<>();
        for (Trip trip : trips) {
            tripSchedule = tripScheduleDAO.getByTripID(trip.getId());
            result.put(trip, tripSchedule);
        }
        return result;
    }

    public Map<Trip, Optional> searchTrips(String date) {
        List<Trip> trips = tripDAO.list(date);
        return getMap(trips);
    }

    public HashMap<Trip, Optional> searchTrips(StationName start, StationName dest, String date, String time) {
        List<Trip> trips = tripDAO.list(start, dest, date, time);
        return getMap(trips);
    }

    public Map<Trip, Optional> searchTrips(StationName start, StationName dest, String date, short train_id) {
        List<Trip> trips = tripDAO.list(start, dest, date, train_id);
        return getMap(trips);
    }

    public Map<Trip, Optional> searchTrips(StationName start, StationName dest, String date, short train_id, DiscountType discountType) {
        List<Trip> trips = tripDAO.list(start, dest, date, train_id);
        HashMap<Trip, Optional> result = new HashMap<>();
        Optional<TripSchedule> tripSchedule;
        for (Trip trip : trips) {
            List<Discount> discounts = discountDAO.list(discountType, trip.getTrainID());
            for (Discount ignored : discounts) {
                tripSchedule = tripScheduleDAO.getByTripID(trip.getId());
                result.put(trip, tripSchedule);
            }
        }
        return result;
    }

    public Map<Trip, Optional> searchTrips(StationName start, StationName dest, String date, String time, DiscountType discountType) {
        List<Trip> trips = tripDAO.list(start, dest, date, time);
        HashMap<Trip, Optional> result = new HashMap<>();
        Optional<TripSchedule> tripSchedule;
        for (Trip trip : trips) {
            List<Discount> discounts = discountDAO.list(discountType, trip.getTrainID());
            for (Discount ignored : discounts) {
                tripSchedule = tripScheduleDAO.getByTripID(trip.getId());
                result.put(trip, tripSchedule);
            }
        }
        return result;
    }

}
