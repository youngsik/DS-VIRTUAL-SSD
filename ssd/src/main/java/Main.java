class Main {

    public static String command;
    public static int lba;
    public static String value;

    public static void main(String[] args) {
        parsing(args);
        run(command, lba, value);
    }

    public static void run(String command, int lba, String value) {
        SSDManager ssdManager = new SSDManager(command, lba, value);
        ssdManager.cmdExecute();
    }

    public static void parsing(String[] args) {
        if (parsePreCondCheck(args)) return;

        command = args[0];
        lba = Integer.parseInt(args[1]);
        value = args.length > 2 ? args[2] : null;

        parsePostCondCheck();
    }

    private static boolean parsePreCondCheck(String[] args) {
        if(args == null || args.length == 0) {
            value = "ERROR";
            return true;
        }

        if("R".equals(args[0])) {
            if(args.length != 2) {
                value = "ERROR";
                return true;
            }

            if(!isDigit(args[1])) {
                value = "ERROR";
                return true;
            }
        }
        else if("W".equals(args[0])) {
            if(args.length != 3) {
                value = "ERROR";
                return true;
            }

            if(!isDigit(args[1])) {
                value = "ERROR";
                return true;
            }

            if(!isValidValue(args[2])) {
                value = "ERROR";
                return true;
            }
        }

        return false;
    }

    private static void parsePostCondCheck() {
        if (!"W".equals(command) && !"R".equals(command)) {
            value = "ERROR";
        }
    }

    private static boolean isDigit(String number) {
        for(char c : number.toCharArray()) {
            if(c < '0' || c > '9') {
                return false;
            }
        }

        return true;
    }

    private static boolean isValidValue(String paramValue) {
        if(paramValue == null) {
            return false;
        }

        if(paramValue.startsWith("0x") && paramValue.length() == 10) {
            String stripParam = paramValue.substring(2);

            return true;
        }
        else{
            return false;
        }
    }

    public static void main(String[] args) {
        SSDManager ssdManager = new SSDManager("W", 0, "0x00000002");
        ssdManager.cmdExecute();

        ssdManager = new SSDManager("W", 0, "0x00000001");
        ssdManager.cmdExecute();
    }
}
