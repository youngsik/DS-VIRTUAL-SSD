package com.samsung.command.testscript;

import java.util.Random;

import static com.samsung.command.testscript.TestScriptConstant.MAX_RAND_BOUND;

public class RandomHex {
    private static final Random RANDOM = new Random();

    private static final RandomHex INSTANCE = new RandomHex();

    private RandomHex() {}

    public static RandomHex getInstance() {
        return INSTANCE;
    }

    public String getRandomValue() {
        int randomInt = RANDOM.nextInt(MAX_RAND_BOUND);
        return String.format("0x%08X", randomInt);
    }
}
