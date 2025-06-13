package com.samsung.command;

import com.samsung.validator.CommandValidator;

public class ArgumentResolver {

    public static Integer resolveLba(String lbaString) {
        Integer lba = toInt(lbaString);
        CommandValidator.validateLbaRange(lba);
        return lba;
    }

    public static String resolveValue(String value) {
        CommandValidator.validateNull(value);
        CommandValidator.validateValueFormat(value);
        return value;
    }

    public static Integer toInt(String arg) {
        try{
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new RuntimeException("INVALID COMMAND");
        }
    }
}
