package com.samsung.validator;

import com.samsung.common.CmdData;

import static com.samsung.common.CommandType.FLUSH;

public class FlushCommandValidator implements CommandValidator {
    @Override
    public CmdData validate(String[] cmdParam) {
        ArgumentCountValidator.validateOneArgs(cmdParam);
        return new CmdData(FLUSH, -1, null);
    }
}