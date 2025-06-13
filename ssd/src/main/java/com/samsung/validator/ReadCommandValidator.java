package com.samsung.validator;

import com.samsung.CmdData;

import static com.samsung.CommandType.ERROR;
import static com.samsung.CommandType.READ;
import static com.samsung.SSDConstant.COMMAND_ERROR;
import static com.samsung.SSDConstant.MAX_LBA;

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
        return new CmdData(ERROR, -1, COMMAND_ERROR);
    }
}