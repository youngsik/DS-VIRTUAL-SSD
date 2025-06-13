package com.samsung.validator;

import com.samsung.ssd.CmdData;

import static com.samsung.ssd.CommandType.ERROR;
import static com.samsung.ssd.CommandType.WRITE;
import static com.samsung.ssd.SSDConstant.ERROR_MESSAGE;
import static com.samsung.ssd.SSDConstant.MAX_LBA;

public class WriteCommandValidator implements CommandValidator {
    @Override
    public CmdData validate(String[] cmdParam) {
        if (cmdParam.length != 3) return error();
        int lba;
        if (!cmdParam[2].matches("^0x[0-9A-F]{8}$")) return error();
        try {
            lba = Integer.parseInt(cmdParam[1]);
        } catch (NumberFormatException e) {
            return error();
        }
        if (lba < 0 || lba > MAX_LBA) return error();
        return new CmdData(WRITE, lba, cmdParam[2]);
    }

    private CmdData error() {
        return new CmdData(ERROR, -1, ERROR_MESSAGE);
    }
}