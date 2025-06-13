package com.samsung.validator;

import com.samsung.CmdData;
import com.samsung.SSDConstant;

public class CmdValidChecker {
    public CmdData cmdValidCheckAndParsing(String[] cmdParam) {
        if (cmdParam.length == 0) {
            return new CmdData(SSDConstant.COMMAND_ERROR, -1, SSDConstant.COMMAND_ERROR);
        }
        CommandValidator validator;
        switch (cmdParam[0]) {
            case SSDConstant.COMMAND_READ: validator = new ReadCommandValidator(); break;
            case SSDConstant.COMMAND_WRITE: validator = new WriteCommandValidator(); break;
            case SSDConstant.COMMAND_ERASE: validator = new EraseCommandValidator(); break;
            case SSDConstant.COMMAND_FLUSH: validator = new FlushCommandValidator(); break;
            default: validator = null;
        }

        if (validator == null) {
            return new CmdData(SSDConstant.COMMAND_ERROR, -1, SSDConstant.COMMAND_ERROR);
        }
        return validator.validate(cmdParam);
    }
}