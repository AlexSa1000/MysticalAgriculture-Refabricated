package com.alex.mysticalagriculture.zucchini.util;

import net.minecraft.util.math.random.Random;

public final class Utils {
    public static final Random RANDOM = Random.createThreadSafe();

    public Utils() {
    }

    public static int randInt(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
