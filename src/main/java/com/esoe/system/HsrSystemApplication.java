package com.esoe.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static java.lang.System.exit;

@SpringBootApplication
public class HsrSystemApplication {



    public static void main(String[] args) {
        SpringApplication.run(HsrSystemApplication.class, args);


        exit(0);
    }

}
