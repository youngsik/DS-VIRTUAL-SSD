package com.samsung.resolver;

import com.samsung.common.CmdData;
import com.samsung.common.CommandType;
import com.samsung.validator.CommandValidator;

public class CommandResolver {
    public CmdData cmdValidCheckAndParsing(String[] cmdParam) {
        if (cmdParam.length == 0) {
            throw new RuntimeException();
        }
        try {
            CommandValidator validator = CommandType.fromCode(cmdParam[0]).getCommandValidator();
            return validator.validate(cmdParam);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException();
        }
    }
}