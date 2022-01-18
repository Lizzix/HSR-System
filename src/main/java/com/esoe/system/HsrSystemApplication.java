package com.esoe.system;

import com.esoe.business.Search;
import com.esoe.business.Validate;
import com.esoe.enums.DiscountType;
import com.esoe.enums.SeatPosition;
import com.esoe.enums.StationName;
import com.esoe.util.Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

import static java.lang.System.exit;

@SpringBootApplication
public class HsrSystemApplication {



    public static void main(String[] args) {
        SpringApplication.run(HsrSystemApplication.class, args);
        Search search = new Search();
        Date date = new Date();
        search.searchTrips(StationName.Banciao.getCode(), StationName.Tainan.getCode(),date, DiscountType.UNIVERSITY);
        exit(0);
    }

}
