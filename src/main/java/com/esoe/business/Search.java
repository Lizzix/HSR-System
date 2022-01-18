package com.esoe.business;

import com.esoe.dao.DiscountDAO;
import com.esoe.dao.TripDAO;
import com.esoe.enums.DiscountType;
import com.esoe.model.Discount;
import com.esoe.model.Trip;

import java.util.*;

public class Search {

    private static TripDAO tripDAO = new TripDAO();
    private static DiscountDAO discountDAO = new DiscountDAO();

    // TODO: drop stastation's depart_time is before now

    public List<Trip> searchTrips(Date date) {
        return tripDAO.list(date);
    }

    public List<Trip> searchTrips(short startID, short destID, Date date) {
        return tripDAO.list(startID, destID, date);
    }

    public List<Trip> searchTrips(short train_id, Date date) {
        return tripDAO.list(train_id, date);
    }

    public List<Trip> searchTrips(short startID, short destID, Date date, DiscountType discountType) {
        List<Discount> discounts = discountDAO.list(discountType, date);
        Set<Short> discountIDs = new HashSet<>();
        for (Discount discount : discounts) {
            discountIDs.add(discount.getTrainID());
        }
        String trainIDs = discountIDs.toString()
            .replace("[", "(")
            .replace("]", ")");

        return tripDAO.list(trainIDs,startID, destID, date);
    }


    // TODO: implement show price table


}
