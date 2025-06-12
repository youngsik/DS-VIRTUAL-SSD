package com.samsung;

import com.samsung.CommandValidator;

import static com.samsung.SSDConstant.*;

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
        return new CmdData(COMMAND_READ, lba, null);
    }

    private CmdData error() {
        return new CmdData(COMMAND_ERROR, -1, COMMAND_ERROR);
    }
}