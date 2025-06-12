package com.samsung;

import static com.samsung.SSDConstant.*;

public class CmdValidChecker {
    public CmdData cmdValidCheckAndParsing(String[] cmdParam) {
        if (cmdParam.length == 0) {
            return new CmdData(SSDConstant.COMMAND_ERROR, -1, SSDConstant.COMMAND_ERROR);
        }
        CommandValidator validator;
        switch (cmdParam[0]) {
            case COMMAND_READ: validator = new ReadCommandValidator(); break;
            case COMMAND_WRITE: validator = new WriteCommandValidator(); break;
            case COMMAND_ERASE: validator = new EraseCommandValidator(); break;
            default: validator = null;
        }

        if (validator == null) {
            return new CmdData(SSDConstant.COMMAND_ERROR, -1, SSDConstant.COMMAND_ERROR);
        }
        return validator.validate(cmdParam);
    }
}