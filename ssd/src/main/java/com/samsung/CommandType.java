package com.samsung;

import com.samsung.validator.*;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CommandType {
    WRITE("W", new WriteCMDValidatorInterface()),
    ERASE("E", new EraseCMDValidatorInterface()),
    READ("R", new ReadCMDValidatorInterface()),
    FLUSH("F", new FlushCMDValidatorInterface());

    private final String code;
    private final CMDValidatorInterface CMDValidatorInterface;

    CommandType(String code, CMDValidatorInterface CMDValidatorInterface) {
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