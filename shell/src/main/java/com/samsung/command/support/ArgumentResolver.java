package com.samsung.command.support;

public class ArgumentResolver {

    public static Integer resolveLba(String lbaString) {
        Integer lba = toInt(lbaString);
        ArgumentValidator.validateLbaRange(lba);
        return lba;
    }

    public static String resolveValue(String value) {
        ArgumentValidator.validateNull(value);
        ArgumentValidator.validateValueFormat(value);
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
