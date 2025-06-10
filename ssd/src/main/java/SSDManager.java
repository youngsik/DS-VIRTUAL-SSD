class SSDManager {
    private String action;
    private int lbaLocation;
    private String value;
    private FileManager fileManager;

    public SSDManager(String action, int lbaLocation, String value) {
        this.action = action;
        this.lbaLocation = lbaLocation;
        this.value = value;
    }

    public void fileRead(int lbaLocation) {
        fileManager.readFile(lbaLocation);
    }

    public void fileWrite(int lbaLocation, String value) {
        fileManager.writeFile(lbaLocation, value);

    }
}