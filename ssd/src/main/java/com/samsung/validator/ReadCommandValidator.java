package com.samsung.validator;

import com.samsung.common.CmdData;
import com.samsung.resolver.ArgumentResolver;

import static com.samsung.common.CommandType.READ;

public class ReadCommandValidator implements CommandValidator {
    @Override
    public CmdData validate(String[] cmdParam) {
        ArgumentCountValidator.validateTwoArgs(cmdParam);
        int lba = ArgumentResolver.resolveLba(cmdParam[1]);
        return new CmdData(READ, lba, null);
    }
}