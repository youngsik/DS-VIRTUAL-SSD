class SSDManager {

    private String action;
    private int lbaLocation;
    private String value;

    public SSDManager(String action, int lbaLocation, String value) {
        this.action = action;
        this.lbaLocation = lbaLocation;
        this.value = value;
    }

    public void fileRead(int lbaLocation) {

    }

    public void fileWrite(int lbaLocation, String value) {

    }

    public void cmdExecute() {
        if ("read".equalsIgnoreCase(action)) {
            fileRead(lbaLocation);
        } else if ("write".equalsIgnoreCase(action)) {
            fileWrite(lbaLocation, value);
        } else {
            System.out.println("Invalid command");
        }
    }
}