class SSDManager {
    private String action = "";
    private int LBA = -1;
    private String value = "";
    private FileManager fileManager;

    public SSDManager(String action, int LBA, String value) {
        this.action = action;
        this.LBA = LBA;
        this.value = value;
        this.fileManager = new FileManager();
    }

    public void cmdExecute() {
        if ("ERROR".equals(value)) {
            fileManager.errorResult(value);
            return;
        }
        if ("R".equals(action)) {
            fileRead(LBA);
            return;
        }
        if ("W".equals(action)) {
            fileWrite(LBA, value);
        }
    }

    public void fileRead(int lbaLocation) {
        fileManager.readFile(lbaLocation);
    }

    public void fileWrite(int lbaLocation, String value) {
        fileManager.writeFile(lbaLocation, value);
    }
}