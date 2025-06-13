package com.samsung.validator;

import com.samsung.CmdData;
import com.samsung.support.ArgumentResolver;
import com.samsung.support.CommandValidator;

import static com.samsung.CommandType.ERASE;

public class EraseCMDValidatorInterface implements CMDValidatorInterface {
    @Override
    public CmdData validate(String[] cmdParam) {
        CommandValidator.validateThreeArgs(cmdParam);
        int lba = ArgumentResolver.resolveLba(cmdParam[1]);
        int size = ArgumentResolver.resolveSize(cmdParam[2]);
        if (lba + size > 100) throw new RuntimeException();
        return new CmdData(ERASE, lba, cmdParam[2]);
    }
}