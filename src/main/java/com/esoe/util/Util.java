package com.esoe.util;

import java.util.Random;

public class Util {
    public static int getRandomCode() {
        /**
         * @return a random 8 bit digits [10000000, 99999999]
         */
        Random random = new Random();
        int lastBits = random.nextInt(10000000);
        int firstBit = random.nextInt(9) + 1;
        String code = String.format("%d%07d", firstBit, lastBits);
        return Integer.parseInt(code);
    }
}
