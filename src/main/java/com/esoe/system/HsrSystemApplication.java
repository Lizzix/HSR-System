package com.esoe.system;

import com.esoe.dao.*;
import com.esoe.enums.StationName;
import com.esoe.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import static java.lang.System.exit;

@SpringBootApplication
public class HsrSystemApplication {

//    private static StationDAO dao = new StationDAO();
//    private static TripScheduleDAO dao = new TripScheduleDAO();

    public static void main(String[] args) {
        SpringApplication.run(HsrSystemApplication.class, args);

//          List<Station> stations = dao.list();
//        tripSchedules.forEach(System.out::println);
//
        System.out.println("--------------------------");
        exit(0);
    }

}
