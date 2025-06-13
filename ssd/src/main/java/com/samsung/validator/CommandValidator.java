package com.samsung.validator;

import com.samsung.common.CmdData;

public interface CommandValidator {
    CmdData validate(String[] cmdParam);
}