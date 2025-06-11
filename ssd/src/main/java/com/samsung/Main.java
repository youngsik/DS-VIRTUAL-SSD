package com.samsung;

class Main {

    public static String command;
    public static int LBA;
    public static String value;

    public static void main(String[] args) {
        parsing(args);
        run(command, LBA, value);
    }

    public static void run(String command, int LBA, String value) {
        SSDManager ssdManager = new SSDManager(command, LBA, value);
        ssdManager.cmdExecute();
    }

    public static void parsing(String[] cmdParam) {
        if (parsePreCondCheck(cmdParam)) return;

        command = cmdParam[0];
        LBA = Integer.parseInt(cmdParam[1]);
        value = cmdParam.length > 2 ? cmdParam[2] : null;

        parsePostCondCheck();
    }

    private static boolean parsePreCondCheck(String[] cmdParam) {
        if (parseNumFail(cmdParam)) return true;
        if (parseParamCountCheckFail(cmdParam)) return true;
        if (checkValueFormatFail(cmdParam)) return true;
        return false;
    }

    private static boolean parseNumFail(String[] cmdParam) {
        try {
            LBA = Integer.parseInt(cmdParam[1]);
        } catch (NumberFormatException e) {
            return setErrorCommand();
        }
        if (LBA < 0 || LBA > 99) {
            return setErrorCommand();
        }
        return false;
    }

    private static boolean setErrorCommand() {
        command = "ERROR";
        value = "ERROR";
        return true;
    }

    private static boolean parseParamCountCheckFail(String[] cmdParam) {
        if (cmdParam.length != 2 && cmdParam.length != 3) {
            return setErrorCommand();
        }
        if (cmdParam[0] == "W" && cmdParam.length != 3) {
            return setErrorCommand();
        }
        return false;
    }

    private static void parsePostCondCheck() {
        if (!"W".equals(command) && !"R".equals(command)) {
            command = "ERROR";
            value = "ERROR";
        }
    }

    private static boolean checkValueFormatFail(String[] cmdParam) {
        if (cmdParam.length > 2 && cmdParam[2].matches("0x[0-9A-Z]{8}")) return false;
        if (cmdParam.length == 2) return false;
        return setErrorCommand();
    }
}
