package com.samsung;

class SSDManager {
    public static final String ERASE_VALUE = "0x00000000";
    private String command = "";
    private int LBA = -1;
    private String value = "";
    private FileManager fileManager;

    public SSDManager(String command, int LBA, String value) {
        this.command = command;
        this.LBA = LBA;
        this.value = value;
        this.fileManager = new FileManager();
    }

    public void cmdExecute() {
        if (command.equals("ERROR")) fileErrorOutput();
        else if (command.equals("R")) fileRead(LBA);
        else if (command.equals("W")) fileWrite(LBA, value);
        else if (command.equals("E")) fileErase(LBA, Integer.parseInt(value));
    }

    private void fileErase(int lba, int size) {
        for (int i=lba; i<lba+size; i++) {
            if(i > 99) break;
            fileManager.writeFile(i, ERASE_VALUE);
        }
    }

    private void fileErrorOutput() {
        fileManager.writeOnOutputFile(value);
    }

    public void fileRead(int lbaLocation) {
        fileManager.readFile(lbaLocation);
    }

    public void fileWrite(int lbaLocation, String value) {
        fileManager.writeFile(lbaLocation, value);
    }
}