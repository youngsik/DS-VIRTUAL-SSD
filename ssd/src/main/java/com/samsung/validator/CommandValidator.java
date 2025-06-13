package com.samsung.validator;

import com.samsung.ssd.CmdData;

public interface CommandValidator {
    CmdData validate(String[] cmdParam);
}