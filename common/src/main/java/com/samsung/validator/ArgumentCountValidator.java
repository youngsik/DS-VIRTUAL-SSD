package com.samsung.validator;

public class ArgumentCountValidator {

    public static void validateOneArgs(String[] cmdArgs) {
        if (cmdArgs.length != 1) {
            throw new RuntimeException("INVALID COMMAND");
        }
    }

    public static void validateTwoArgs(String[] cmdArgs) {
        if (cmdArgs.length != 2) {
            throw new RuntimeException("INVALID COMMAND");
        }
    }

    public static void validateThreeArgs(String[] cmdArgs) {
        if (cmdArgs.length != 3) {
            throw new RuntimeException("INVALID COMMAND");
        }
    }

}
