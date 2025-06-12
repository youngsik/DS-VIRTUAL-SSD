package com.samsung.command.testscript;

import java.util.Random;

import static com.samsung.command.testscript.TestScriptConstant.MAX_RAND_BOUND;

public class RandomHex {

    private static RandomHex instance;

    private RandomHex() {
    }
    public static RandomHex getInstance() {
        if (instance == null)
            instance = new RandomHex();
        return instance;
    }

    public static String getRandomValue() {
        int randomInt = new Random().nextInt(MAX_RAND_BOUND);
        return String.format("0x%08X", randomInt);
    }
}
