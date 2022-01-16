package com.esoe.system;

import com.esoe.dao.StationDAO;
import com.esoe.model.Station;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

import static java.lang.System.exit;

@SpringBootApplication
public class HsrSystemApplication {

    private static StationDAO dao = new StationDAO();

    public static void main(String[] args) {
        SpringApplication.run(HsrSystemApplication.class, args);

        List<Station> stations = dao.list();
        stations.forEach(System.out::println);
        exit(0);

    }

}
