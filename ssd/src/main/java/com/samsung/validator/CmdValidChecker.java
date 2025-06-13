package com.samsung.validator;

import com.samsung.CmdData;
import com.samsung.CommandType;

public class CmdValidChecker {
    public CmdData cmdValidCheckAndParsing(String[] cmdParam) {
        if (cmdParam.length == 0) {
            throw new RuntimeException();
        }
        try {
            CmdValidatorInterface validator = CommandType.fromCode(cmdParam[0]).getCMDValidatorInterface();
            validator.validate(cmdParam);
            return validator.validate(cmdParam);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException();
        }
    }
}