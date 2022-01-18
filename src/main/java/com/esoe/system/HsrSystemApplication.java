package com.esoe.system;

import com.esoe.enums.DiscountType;
import com.esoe.model.Trip;
import com.esoe.service.Search;
import com.esoe.enums.StationName;
import com.esoe.util.Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.Optional;

import static java.lang.System.exit;

@SpringBootApplication
public class HsrSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HsrSystemApplication.class, args);
        Search search = new Search();
        String date = Util.getToday();
        String time = Util.getCurrentTimeStr();
        Map<Trip, Optional> result = search.searchTrips(StationName.Taipei,StationName.Chiayi, date, time, DiscountType.UNIVERSITY);
        System.out.println(result.size());


        exit(0);
    }

}
