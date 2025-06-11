class SSDManager {
    private String action;
    private int LBA;
    private String value;
    private FileManager fileManager = new FileManager();

    public SSDManager(String action, int LBA, String value) {
        this.action = action;
        this.LBA = LBA;
        this.value = value;
    }

    public void fileRead(int lbaLocation) {
        fileManager.readFile(lbaLocation);
    }

    public void fileWrite(int lbaLocation, String value) {
        fileManager.writeFile(lbaLocation, value);

    }
    public void cmdExecute() {
        if ("R".equals(action)) {
            fileRead(LBA);
            return;
        }
        if ("W".equals(action)) {
            fileWrite(LBA, value);
            return;
        }
    }
}