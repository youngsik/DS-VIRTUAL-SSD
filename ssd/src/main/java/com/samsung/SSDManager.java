package com.samsung;

class SSDManager {
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
        if (command.equals(SSDConstant.COMMAND_ERROR)) fileErrorOutput();
        else if (command.equals(SSDConstant.COMMAND_READ)) fileManager.readFile(lba);
        else if (command.equals(SSDConstant.COMMAND_WRITE)) fileManager.writeFile(lba, value);
        else if (command.equals(SSDConstant.COMMAND_ERASE)) fileErase(lba, Integer.parseInt(value));
    }

    private void fileErase(int startLba, int size) {
        int endLba = startLba + size;
        for (int currentLba = startLba; currentLba < endLba; currentLba++) {
            if(currentLba > SSDConstant.MAX_LBA) break;
            fileManager.writeFile(currentLba, SSDConstant.ERASE_VALUE);
        }
    }

    private void fileErrorOutput() {
        fileManager.writeOnOutputFile(value);
    }
}