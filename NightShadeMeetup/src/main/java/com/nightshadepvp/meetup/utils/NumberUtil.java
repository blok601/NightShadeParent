package com.nightshadepvp.meetup.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Blok on 10/15/2018.
 */
public class NumberUtil {

    public static boolean isBetween(int max, int min, int val) {
        return min < val && val < max;
    }

    public static int randomBetween(int int1, int int2) {
        return ThreadLocalRandom.current().nextInt(int1, int2 + 1);
    }

}
