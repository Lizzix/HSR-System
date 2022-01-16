package com.esoe.container;

import java.util.Map;

public class Price {
    static public int calculateTotal(Map<Integer, Integer> tickets) {
        int price = 0;
        // iterate through the map
        for (Map.Entry<Integer, Integer> entry : tickets.entrySet()) {
            price += calculate(entry.getKey(), entry.getValue());
        }
        return price;
    }
    // TODO
    private static int calculate(int discount, int quantity) {

        return 0;
    }
}
