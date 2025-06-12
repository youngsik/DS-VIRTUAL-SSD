package com.samsung.validator;

import com.samsung.CmdData;

public interface CommandValidator {
    CmdData validate(String[] cmdParam);
}