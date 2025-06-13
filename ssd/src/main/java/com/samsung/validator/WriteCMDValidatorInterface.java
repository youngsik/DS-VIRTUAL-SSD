package com.samsung.validator;

import com.samsung.CmdData;
import com.samsung.support.ArgumentResolver;
import com.samsung.support.ArgumentValidator;
import com.samsung.support.CommandValidator;

import static com.samsung.CommandType.WRITE;

public class WriteCMDValidatorInterface implements CMDValidatorInterface {
    @Override
    public CmdData validate(String[] cmdParam) {
        CommandValidator.validateThreeArgs(cmdParam);
        int lba = ArgumentResolver.resolveLba(cmdParam[1]);
        ArgumentValidator.validateValueFormat(cmdParam[2]);
        return new CmdData(WRITE, lba, cmdParam[2]);
    }
}