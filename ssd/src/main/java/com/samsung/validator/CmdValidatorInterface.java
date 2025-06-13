package com.samsung.validator;

import com.samsung.CmdData;

public interface CmdValidatorInterface {
    CmdData validate(String[] cmdParam);
}