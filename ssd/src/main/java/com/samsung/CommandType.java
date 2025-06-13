package com.samsung;

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
    private final CmdValidatorInterface CMDValidatorInterface;

    CommandType(String code, CmdValidatorInterface CMDValidatorInterface) {
        this.code = code;
        this.CMDValidatorInterface = CMDValidatorInterface;
    }

    public static CommandType fromCode(String code) {
        return Arrays.stream(values())
                .filter(c -> c.code.equals(code))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}