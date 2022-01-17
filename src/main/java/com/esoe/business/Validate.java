package com.esoe.business;

import com.esoe.enums.SeatPosition;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Slf4j
public class Validate {

    private static String dataDir = System.getProperty("user.dir") + "/assets/seat.json";
    private static final Gson gson = new Gson();
    private static JsonObject obj;

    private Validate() {}

    public static boolean isSeat(int carNo, int row, SeatPosition column) {
        if (carNo < 1 || carNo > 12) { return false; }
        if (row < 1 || row > 20) { return false; }
        try {
            obj = gson.fromJson(new FileReader(dataDir), JsonObject.class);
            JsonArray cars = obj.getAsJsonArray("cars");
            JsonObject car = cars.get(carNo - 1).getAsJsonObject();
            JsonObject rows = car.getAsJsonObject("seats");
            String rowString = rows.getAsJsonArray(String.valueOf(row)).toString();
            if (rowString.contains(column.getCodeStr())) { return true; }
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // TODO: implement user input validation
    public static boolean isValidInput() {
        return true;
    }
}
