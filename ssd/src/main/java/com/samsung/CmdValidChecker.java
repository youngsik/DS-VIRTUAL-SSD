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
        if (!isCmdValid(cmdParam)) {
            setErrorCommand();
            return new CmdData(command, lba, value);
        }

        command = cmdParam[0];
        lba = Integer.parseInt(cmdParam[1]);
        value = cmdParam.length > 2 ? cmdParam[2] : null;
        return new CmdData(command, lba, value);
    }

    private boolean isCmdValid(String[] cmdParam) {
        if (!isInputNotNull(cmdParam)) return false;
        if (!isParamCountCorrect(cmdParam)) return false;
        if (!isLbaNumber(cmdParam)) return false;
        if (!isValueFormatCorrect(cmdParam)) return false;
        if (!isCorrectCommand(cmdParam)) return false;
        return true;
    }

    private boolean isInputNotNull(String[] cmdParam) {
        if (cmdParam.length != 0) return true;
        return false;
    }

    private boolean isParamCountCorrect(String[] cmdParam) {
        if (checkReadParamCount(cmdParam)) return true;
        if (checkWriteParamCount(cmdParam)) return true;
        if (checkEraseParamCount(cmdParam)) return true;
        return false;
    }

    private boolean isLbaNumber(String[] cmdParam) {
        try {
            lba = Integer.parseInt(cmdParam[1]);
        } catch (NumberFormatException e) {
            return false;
        }
        if (checkLbaRangeFail()) {
            return false;
        }
        return true;
    }

    private boolean checkLbaRangeFail() {
        return lba < 0 || lba > 99;
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

    private boolean isValueFormatCorrect(String[] cmdParam) {
        if (checkWriteValue(cmdParam)) return true;
        if (checkEraseValue(cmdParam)) return true;
        if (cmdParam.length == 2) return true;
        return false;
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

    private boolean isCorrectCommand(String[] cmdParam) {
        if (commandList.contains(cmdParam[0])) return true;
        return false;
    }

    private void setErrorCommand() {
        command = COMMAND_ERROR;
        lba = -1;
        value = COMMAND_ERROR;
    }
}
