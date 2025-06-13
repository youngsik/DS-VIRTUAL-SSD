package com.samsung.validator;

import com.samsung.CmdData;

import static com.samsung.CommandType.ERROR;
import static com.samsung.CommandType.FLUSH;
import static com.samsung.SSDConstant.ERROR_MESSAGE;

public class FlushCommandValidator implements CommandValidator {
    @Override
    public CmdData validate(String[] cmdParam) {
        if (cmdParam.length != 1) return error();
        return new CmdData(FLUSH, -1, null);
    }

    private CmdData error() {
        return new CmdData(ERROR, -1, ERROR_MESSAGE);
    }
}