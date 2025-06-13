package com.samsung.ssd;

import com.samsung.validator.*;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CommandType {
    WRITE("W", new WriteCommandValidator()),
    ERASE("E", new EraseCommandValidator()),
    READ("R", new ReadCommandValidator()),
    FLUSH("F", new FlushCommandValidator()),
    ERROR("ERROR", null);

    private final String code;
    private final CommandValidator commandValidator;

    CommandType(String code, CommandValidator commandValidator) {
        this.code = code;
        this.commandValidator = commandValidator;
    }

    public static CommandType fromCode(String code) {
        return Arrays.stream(values())
                .filter(c -> c.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown command: " + code));
    }
}