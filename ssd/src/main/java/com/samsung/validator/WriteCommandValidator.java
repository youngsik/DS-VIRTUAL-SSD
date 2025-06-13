package com.samsung.validator;

import com.samsung.common.CmdData;
import com.samsung.resolver.ArgumentResolver;

import static com.samsung.common.CommandType.WRITE;

public class WriteCommandValidator implements CommandValidator {
    @Override
    public CmdData validate(String[] cmdParam) {
        ArgumentCountValidator.validateThreeArgs(cmdParam);
        int lba = ArgumentResolver.resolveLba(cmdParam[1]);
        ArgumentValidator.validateValueFormat(cmdParam[2]);
        return new CmdData(WRITE, lba, cmdParam[2]);
    }
}