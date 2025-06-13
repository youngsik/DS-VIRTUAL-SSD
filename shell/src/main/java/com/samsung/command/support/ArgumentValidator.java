package com.samsung.command.support;

public class ArgumentValidator {

    public static void validateNull(Object object) {
        if (object == null) {
            throw new RuntimeException("INVALID COMMAND");
        }
    }

    public static void validateValueFormat(String value) {
        if (!value.matches("^0x[0-9A-F]{8}$")) {
            throw new RuntimeException("INVALID COMMAND");
        }
    }

    public static void validateLbaRange(Integer lba) {
        if (lba < 0 || lba > 99) {
            throw new RuntimeException("INVALID COMMAND");
        }
    }

}
