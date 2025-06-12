package com.samsung;

import static com.samsung.SSDConstant.*;

public class WriteCommandValidator implements CommandValidator {
    @Override
    public CmdData validate(String[] cmdParam) {
        if (cmdParam.length != 3) return error();
        if (!cmdParam[0].equals(COMMAND_WRITE)) return error();
        int lba;
        if (!cmdParam[2].matches("^0x[0-9A-F]{8}$")) return error();
        try {
            lba = Integer.parseInt(cmdParam[1]);
        } catch (NumberFormatException e) {
            return error();
        }
        if (lba < 0 || lba > 99) return error();
        return new CmdData(COMMAND_WRITE, lba, cmdParam[2]);
    }

    private CmdData error() {
        return new CmdData(COMMAND_ERROR, -1, COMMAND_ERROR);
    }
}