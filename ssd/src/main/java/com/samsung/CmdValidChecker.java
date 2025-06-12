package com.samsung;

import java.util.ArrayList;
import java.util.Arrays;

import static com.samsung.SSDConstant.*;

public class CmdValidChecker {
    public String command;
    public int lba;
    public String value;
    private final ArrayList<String> commandList
            = new ArrayList<>(Arrays.asList(COMMAND_READ, COMMAND_WRITE, COMMAND_ERASE));

    public CmdData cmdValidCheckAndParsing(String[] cmdParam) {
        if (parsePreCondCheck(cmdParam)) return new CmdData("ERROR",-1,"ERROR");

        command = cmdParam[0];
        lba = Integer.parseInt(cmdParam[1]);
        value = cmdParam.length > 2 ? cmdParam[2] : null;
        return new CmdData(command, lba, value);
    }

    private boolean parsePreCondCheck(String[] cmdParam) {
        if (checkInputNull(cmdParam)) return true;
        if (parseParamCountCheckFail(cmdParam)) return true;
        if (parseNumFail(cmdParam)) return true;
        if (checkValueFormatFail(cmdParam)) return true;
        if (checkCmdFail(cmdParam)) return true;
        return false;
    }

    private boolean checkInputNull(String[] cmdParam) {
        if (cmdParam.length != 0) return false;
        setErrorCommand();
        return true;

    }

    private boolean parseNumFail(String[] cmdParam) {
        try {
            lba = Integer.parseInt(cmdParam[1]);
        } catch (NumberFormatException e) {
            setErrorCommand();
            return true;
        }
        if (checkLbaRangeFail()) {
            setErrorCommand();
            return true;
        }
        return false;
    }

    private boolean parseParamCountCheckFail(String[] cmdParam) {
        if (checkReadParamCount(cmdParam)) return false;
        if(checkWriteParamCount(cmdParam)) return false;
        if(checkEraseParamCount(cmdParam)) return false;
        setErrorCommand();
        return true;
    }

    private boolean checkWriteParamCount(String[] cmdParam) {
        if (isWriteCommand(cmdParam) && cmdParam.length == 3) return true;
        return false;
    }

    private boolean checkReadParamCount(String[] cmdParam) {
        if (isReadCommand(cmdParam) && cmdParam.length == 2) return true;
        return false;
    }

    private boolean checkEraseParamCount(String[] cmdParam) {
        if (isEraseCommand(cmdParam) && cmdParam.length == 3) return true;
        return false;
    }

    private boolean isWriteCommand(String[] cmdParam) {
        return cmdParam[0].equals(COMMAND_WRITE);
    }

    private boolean isReadCommand(String[] cmdParam) {
        return cmdParam[0].equals(COMMAND_READ);
    }

    private boolean isEraseCommand(String[] cmdParam) {
        return cmdParam[0].equals(COMMAND_ERASE);
    }

    private boolean checkCmdFail(String[] cmdParam) {
        if (commandList.contains(cmdParam[0])) return false;
        setErrorCommand();
        return true;
    }

    private boolean checkValueFormatFail(String[] cmdParam) {
        if (checkWriteValue(cmdParam)) return false;
        if (checkEraseValue(cmdParam)) return false;
        if (cmdParam.length == 2) return false;
        setErrorCommand();
        return true;
    }

    private boolean checkWriteValue(String[] cmdParam) {
        if (!isWriteCommand(cmdParam)) return false;
        return cmdParam[2].matches("^0x[0-9A-F]{8}$");
    }

    private boolean checkEraseValue(String[] cmdParam) {
        if (!isEraseCommand(cmdParam)) return false;
        int lba = 0;
        int eRange = 0;
        try {
            lba = Integer.parseInt(cmdParam[1]);
            eRange = Integer.parseInt(cmdParam[2]);
        } catch (NumberFormatException e) {
            return false;
        }
        if (eRange < 0 || eRange > 10 || lba + eRange > 100) {
            return false;
        }
        return true;
    }

    private void setErrorCommand() {
        command = "ERROR";
        value = "ERROR";
    }

    private boolean checkLbaRangeFail() {
        return lba < 0 || lba > 99;
    }
}
