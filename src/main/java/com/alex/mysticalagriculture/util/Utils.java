package com.alex.mysticalagriculture.util;

import java.text.NumberFormat;
import java.util.Random;

public class Utils {
    public static final Random RANDOM = new Random();

    public static String format(Object obj) {
        return NumberFormat.getInstance().format(obj);
    }

    public static int randInt(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
