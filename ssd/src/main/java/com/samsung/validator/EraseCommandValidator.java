package com.samsung.validator;

import com.samsung.CmdData;

import static com.samsung.CommandType.ERASE;
import static com.samsung.CommandType.ERROR;
import static com.samsung.SSDConstant.*;

public class EraseCommandValidator implements CommandValidator {
    @Override
    public CmdData validate(String[] cmdParam) {
        if (cmdParam.length != 3) return error();
        int lba, size;
        try {
            lba = Integer.parseInt(cmdParam[1]);
            size = Integer.parseInt(cmdParam[2]);
        } catch (NumberFormatException e) {
            return error();
        }
        if (lba < 0 || lba > MAX_LBA) return error();
        if (size < 0 || size > MAX_ERASE_SIZE) return error();
        if (lba + size > 100) return error();
        return new CmdData(ERASE, lba, cmdParam[2]);
    }

    private CmdData error() {
        return new CmdData(ERROR, -1, ERROR_MESSAGE);
    }
}