package com.samsung;

class SSDManager {
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
        if (command.equals("ERROR")) fileThrowExcpetion();
        else if (command.equals("R")) fileRead(LBA);
        else if (command.equals("W")) fileWrite(LBA, value);
    }

    private void fileThrowExcpetion() {
        fileManager.throwException(value);
    }

    public void fileRead(int lbaLocation) {
        fileManager.readFile(lbaLocation);
    }

    public void fileWrite(int lbaLocation, String value) {
        fileManager.writeFile(lbaLocation, value);
    }
}