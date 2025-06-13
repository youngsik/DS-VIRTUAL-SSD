package com.samsung.validator;

import com.samsung.CmdData;
import com.samsung.support.ArgumentResolver;
import com.samsung.support.CommandValidator;

import static com.samsung.CommandType.READ;

public class ReadCommandValidator implements CmdValidatorInterface {
    @Override
    public CmdData validate(String[] cmdParam) {
        CommandValidator.validateTwoArgs(cmdParam);
        int lba = ArgumentResolver.resolveLba(cmdParam[1]);
        return new CmdData(READ, lba, null);
    }
}