public class Main {

    public static String command;
    public static int lba;
    public static int LBA;
    public static String value;

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
        LBA = Integer.parseInt(cmdParam[1]);
        value = cmdParam.length > 2 ? cmdParam[2] : null;

        parsePostCondCheck();
    }

    private static boolean parsePreCondCheck(String[] cmdParam) {
        if(cmdParam == null || cmdParam.length == 0) {

        }
        try {
            LBA = Integer.parseInt(cmdParam[1]);
        } catch (NumberFormatException e) {
            command = "ERROR";
            value = "ERROR";
            return true;
        }

        if("R".equals(cmdParam[0])) {
            if(cmdParam.length != 2) {
                value = "ERROR";
                return true;
            }

            if(!isDigit(cmdParam[1])) {
                value = "ERROR";
                return true;
            }
        }
        else if("W".equals(cmdParam[0])) {
            if(cmdParam.length != 3) {
                value = "ERROR";
                return true;
            }

            if(!isDigit(cmdParam[1])) {
                value = "ERROR";
                return true;
            }

            if(!isValidValue(cmdParam[2])) {
                value = "ERROR";
                return true;
            }
        }

        return false;
    }

    private static void parsePostCondCheck() {
        if (!"W".equals(command) && !"R".equals(command)) {
            command = "ERROR";
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

    /*
    public static void main(String[] args) {
        SSDManager ssdManager = new SSDManager("W", 0, "0x00000002");
        ssdManager.cmdExecute();

        ssdManager = new SSDManager("W", 0, "0x00000001");
        ssdManager.cmdExecute();
    } */
}
