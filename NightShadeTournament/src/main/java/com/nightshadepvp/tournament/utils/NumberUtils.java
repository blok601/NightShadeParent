package com.nightshadepvp.tournament.utils;

/**
 * Created by Blok on 7/26/2018.
 */
public class NumberUtils {

    public static String formatSecs(int s) {
        int mins = s / 60;

        if (mins == 0) {
            return s + " seconds";
        }

        s %= 60;
        return mins + " minutes and " + s + " seconds";
    }

    public static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isBase2Number(double a) {
        double d = logb(a, 2);
        if(Math.floor(d) == d){
            return true;
        }

        return false;
    }

    private static double logb(double a, double b) {
        return Math.log(a) / Math.log(b);
    }

}
