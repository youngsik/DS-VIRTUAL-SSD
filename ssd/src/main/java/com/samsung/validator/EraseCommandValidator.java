package com.samsung.validator;

import com.samsung.common.CmdData;
import com.samsung.resolver.ArgumentResolver;

import static com.samsung.common.CommandType.ERASE;

public class EraseCommandValidator implements CommandValidator {
    @Override
    public CmdData validate(String[] cmdParam) {
        ArgumentCountValidator.validateThreeArgs(cmdParam);
        int lba = ArgumentResolver.resolveLba(cmdParam[1]);
        int size = ArgumentResolver.resolveSize(cmdParam[2]);
        if (lba + size > 100) throw new RuntimeException();
        return new CmdData(ERASE, lba, cmdParam[2]);
    }
}