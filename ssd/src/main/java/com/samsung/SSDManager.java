package com.samsung;

class SSDManager {
    public static final String ERASE_VALUE = "0x00000000";
    public static final int MAX_LBA = 99;
    private String command = "";
    private int lba = -1;
    private String value = "";
    private FileManager fileManager;

    public SSDManager(String command, int lba, String value) {
        this.command = command;
        this.lba = lba;
        this.value = value;
        this.fileManager = new FileManager();
    }

    public void cmdExecute() {
        if (command.equals("ERROR")) fileErrorOutput();
        else if (command.equals("R")) fileRead(lba);
        else if (command.equals("W")) fileWrite(lba, value);
        else if (command.equals("E")) fileErase(lba, Integer.parseInt(value));
    }

    private void fileErase(int startLba, int size) {
        int endLba = startLba + size;
        for (int currentLba = startLba; currentLba < endLba; currentLba++) {
            if(currentLba > MAX_LBA) break;
            fileManager.writeFile(currentLba, ERASE_VALUE);
        }
    }

    private void fileErrorOutput() {
        fileManager.writeOnOutputFile(value);
    }

    public void fileRead(int lba) {
        fileManager.readFile(lba);
    }

    public void fileWrite(int lba, String value) {
        fileManager.writeFile(lba, value);
    }
}