package com.samsung.validator;

import com.samsung.ssd.CmdData;
import com.samsung.ssd.CommandType;

import static com.samsung.ssd.CommandType.ERROR;

public class CmdValidChecker {
    public CmdData cmdValidCheckAndParsing(String[] cmdParam) {
        if (cmdParam.length == 0) {
            return new CmdData(ERROR, -1, ERROR.name());
        }
        try {
            CommandValidator validator = CommandType.fromCode(cmdParam[0]).getCommandValidator();
            validator.validate(cmdParam);
            return validator.validate(cmdParam);
        } catch (IllegalArgumentException e) {
            return new CmdData(ERROR, -1, ERROR.name());
        }
    }
}