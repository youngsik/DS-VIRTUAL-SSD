package com.samsung.validator;

import com.samsung.CmdData;

import static com.samsung.SSDConstant.*;

public class FlushCommandValidator implements CommandValidator {
    @Override
    public CmdData validate(String[] cmdParam) {
        if (cmdParam.length != 1) return error();
        return new CmdData(COMMAN_FLUSH, -1, null);
    }

    private CmdData error() {
        return new CmdData(COMMAND_ERROR, -1, COMMAND_ERROR);
    }
}