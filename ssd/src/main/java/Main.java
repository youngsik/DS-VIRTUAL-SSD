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
        try {
            LBA = Integer.parseInt(cmdParam[1]);
        } catch (NumberFormatException e) {
            value = "ERROR";
            return true;
        }
        return false;
    }

    private static void parsePostCondCheck() {
        if (!"W".equals(command) && !"R".equals(command)) {
            value = "ERROR";
        }
    }
}
