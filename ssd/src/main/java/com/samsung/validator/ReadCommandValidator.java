package com.samsung.validator;

import com.samsung.ssd.CmdData;

import static com.samsung.ssd.CommandType.ERROR;
import static com.samsung.ssd.CommandType.READ;
import static com.samsung.ssd.SSDConstant.ERROR_MESSAGE;
import static com.samsung.ssd.SSDConstant.MAX_LBA;

public class ReadCommandValidator implements CommandValidator {
    @Override
    public CmdData validate(String[] cmdParam) {
        if (cmdParam.length != 2) return error();
        int lba;
        try {
            lba = Integer.parseInt(cmdParam[1]);
        } catch (NumberFormatException e) {
            return error();
        }
        if (lba < 0 || lba > MAX_LBA) return error();
        return new CmdData(READ, lba, null);
    }

    private CmdData error() {
        return new CmdData(ERROR, -1, ERROR_MESSAGE);
    }
}