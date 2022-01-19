package com.esoe.controller;

import com.esoe.dao.*;
import com.esoe.enums.DiscountType;
import com.esoe.enums.StationName;
import com.esoe.model.Discount;
import com.esoe.model.Trip;
import com.esoe.model.TripSchedule;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.esoe.util.Util.strToStationName;

@RestController
@RequestMapping("/api")
public class timeTableController {

    private static final TripDAO tripDAO = new TripDAO();
    private static final TripScheduleDAO tripScheduleDAO = new TripScheduleDAO();
    private static final DiscountDAO discountDAO = new DiscountDAO();

    @PostMapping("/index")
    public ResponseBody index(@PathVariable("date") String date,
                              @PathVariable("time") String time,
                              @PathVariable("startStation") String start,
                              @PathVariable("endStation") String dest){

        ResponseBody timetable = new ResponseBody();

        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        Map<String, String> trip = new HashMap<>();

        StationName startStation = strToStationName(start);
        StationName destStation = strToStationName(dest);

        List<Trip> trips = tripDAO.list(startStation, destStation, date, time);
        if (trips.isEmpty()) {
            timetable.setStatus(0);
            timetable.setMessage("no available trip");
            timetable.setData(null);
        }
        List<Integer> tripIDs = new ArrayList<>();
        List<Integer> trainIDs = new ArrayList<>();
        for (Trip t : trips) {
            tripIDs.add(t.getId());
            trainIDs.add(t.getTrainID());
        }
        List<Optional> tripSchedules = new ArrayList<>();
        for (Integer id : tripIDs) {
            tripSchedules.add(tripScheduleDAO.get(id));
        }
        Set<List<Discount>> earlyDiscounts = new HashSet<>();
        Set<List<Discount>> uniDiscounts = new HashSet<>();
        for (Integer id : trainIDs) {
            earlyDiscounts.add(discountDAO.list(DiscountType.EARLY, id));
            uniDiscounts.add(discountDAO.list(DiscountType.UNIVERSITY, id));
        }

        int count = 0;
        for (Integer id : trainIDs) {
            Map<String, String> info = new HashMap<>();
            Object obj = tripSchedules.get(count);
            TripSchedule ts = null;
            if (obj != null) {
                ts = (TripSchedule) obj;
            }
            assert ts != null;
            info.put("trainNO", String.valueOf(id));
            info.put("startStation", start);
            info.put("endStation", dest);
            info.put("departureTime", ts.toString());
            info.put("arrivalTime", ts.toString());
            info.put("earlyDiscount", String.valueOf(earlyDiscounts.size()));
            info.put("universityDiscount", String.valueOf(uniDiscounts.size()));
            dataList.add(info);
        }
        timetable.setStatus(1);
        timetable.setMessage("success");
        return timetable;
    }

}
