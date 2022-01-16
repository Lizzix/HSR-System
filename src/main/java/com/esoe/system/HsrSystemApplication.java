package com.esoe.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static com.esoe.util.JsonUtils.parseOriginalData;
import static java.lang.System.exit;

@SpringBootApplication
public class HsrSystemApplication {

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        SpringApplication.run(HsrSystemApplication.class, args);
        // TODO: if the database exists
        // check if database exists by querying the database later
//        try {
//            String schemaPath = System.getProperty("user.dir") + "/src/main/resources/schema.sql";
//            Database database = new Database();
//            database.importSQLfile(schemaPath);
//            parseOriginalData();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
