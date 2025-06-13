package com.samsung.validator;

import com.samsung.CmdData;
import com.samsung.support.CommandValidator;

import static com.samsung.CommandType.FLUSH;

public class FlushCommandValidator implements CmdValidatorInterface {
    @Override
    public CmdData validate(String[] cmdParam) {
        CommandValidator.validateOneArgs(cmdParam);
        return new CmdData(FLUSH, -1, null);
    }
}