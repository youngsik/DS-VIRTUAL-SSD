package com.samsung;

import java.util.ArrayList;
import java.util.Arrays;

class Main {

    public static String command;
    public static int lba;
    public static String value;
    private static final ArrayList<String> commandList = new ArrayList<>(Arrays.asList("R", "W", "E"));

    public static void main(String[] args) {
        parsing(args);
        run(command, lba, value);
    }

    public static void run(String command, int LBA, String value) {
        SSDManager ssdManager = new SSDManager(command, LBA, value);
        ssdManager.cmdExecute();
    }

    public static void parsing(String[] cmdParam) {
        if (parsePreCondCheck(cmdParam)) return;
        command = cmdParam[0];
        lba = Integer.parseInt(cmdParam[1]);
        value = cmdParam.length > 2 ? cmdParam[2] : null;
    }

    private static boolean parsePreCondCheck(String[] cmdParam) {
        if (checkInputNull(cmdParam)) return true;
        if (parseParamCountCheckFail(cmdParam)) return true;
        if (parseNumFail(cmdParam)) return true;
        if (checkValueFormatFail(cmdParam)) return true;
        if (checkCmdFail(cmdParam)) return true;
        return false;
    }

    private static boolean checkInputNull(String[] cmdParam) {
        if (cmdParam.length != 0) return false;
        setErrorCommand();
        return true;

    }

    private static boolean parseNumFail(String[] cmdParam) {
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

    private static boolean parseParamCountCheckFail(String[] cmdParam) {
        if (checkReadParamCount(cmdParam)) return false;
        if(checkWriteParamCount(cmdParam)) return false;
        if(checkEraseParamCount(cmdParam)) return false;
        setErrorCommand();
        return true;
    }

    private static boolean checkWriteParamCount(String[] cmdParam) {
        if (cmdParam[0].equals("W") && cmdParam.length == 3) return true;
        return false;
    }

    private static boolean checkReadParamCount(String[] cmdParam) {
        if (cmdParam[0].equals("R") && cmdParam.length == 2) return true;
        return false;
    }

    private static boolean checkEraseParamCount(String[] cmdParam) {
        if (cmdParam[0].equals("E") && cmdParam.length == 3) return true;
        return false;
    }

    private static boolean checkCmdFail(String[] cmdParam) {
        if (commandList.contains(cmdParam[0])) return false;
        setErrorCommand();
        return true;
    }

    private static boolean checkValueFormatFail(String[] cmdParam) {
        if (checkWriteValue(cmdParam)) return false;
        if (checkEraseValue(cmdParam)) return false;
        if (cmdParam.length == 2) return false;
        setErrorCommand();
        return true;
    }

    private static boolean checkWriteValue(String[] cmdParam) {
        if (!cmdParam[0].equals("W")) return false;
        return cmdParam[2].matches("^0x[0-9A-F]{8}$");
    }

    private static boolean checkEraseValue(String[] cmdParam) {
        if (!cmdParam[0].equals("E")) return false;
        int eRange = 0;
        try {
            eRange = Integer.parseInt(cmdParam[2]);
        } catch (NumberFormatException e) {
            return false;
        }
        if (eRange < 0 || eRange > 10) {
            return false;
        }
        return true;
    }

    private static void setErrorCommand() {
        command = "ERROR";
        value = "ERROR";
    }

    private static boolean checkLbaRangeFail() {
        return lba < 0 || lba > 99;
    }
}
