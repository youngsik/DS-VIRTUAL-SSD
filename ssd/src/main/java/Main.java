class Main {

    public static String command;
    public static int lbaLocation;
    public static String value;

    public static void main(String[] args) {
        parsing(args);
        run(command, lbaLocation, value);
    }

    public static void run(String command, int lbaLocation, String value) {
        SSDManager ssdManager = new SSDManager(command, lbaLocation, value);
        ssdManager.cmdExecute();
    }

    public static void parsing(String[] args) {
        if (parsePreCondCheck(args)) return;

        command = args[0];
        lbaLocation = Integer.parseInt(args[1]);
        value = args[2];

        parsePostCondCheck();
    }

    private static boolean parsePreCondCheck(String[] args) {
        try {
            lbaLocation = Integer.parseInt(args[1]);
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
