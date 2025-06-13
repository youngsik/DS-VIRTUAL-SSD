package com.samsung.validator;

import com.samsung.CmdData;

public interface CMDValidatorInterface {
    CmdData validate(String[] cmdParam);
}