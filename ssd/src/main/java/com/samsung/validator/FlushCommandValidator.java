package com.samsung.validator;

import com.samsung.ssd.CmdData;

import static com.samsung.ssd.CommandType.ERROR;
import static com.samsung.ssd.CommandType.FLUSH;
import static com.samsung.ssd.SSDConstant.ERROR_MESSAGE;

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