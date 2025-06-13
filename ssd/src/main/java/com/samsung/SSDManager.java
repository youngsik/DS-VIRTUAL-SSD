package com.samsung;

import com.samsung.file.FileManager;

class SSDManager {
    private CmdData cmdData;
    private FileManager fileManager;

    public SSDManager(CmdData newCmdData) {
        this.cmdData = newCmdData;
        this.fileManager = FileManager.getInstance();
    }

    public void cmdExecute() {
        if (cmdData.getCommand().equals(SSDConstant.COMMAND_ERROR)) fileErrorOutput();
        else if (cmdData.getCommand().equals(SSDConstant.COMMAND_READ)) fileManager.readFile(cmdData.getLba());
        else if (cmdData.getCommand().equals(SSDConstant.COMMAND_WRITE)) fileManager.writeFile(cmdData.getLba(), cmdData.getValue());
        else if (cmdData.getCommand().equals(SSDConstant.COMMAND_ERASE)) fileErase(cmdData.getLba(), Integer.parseInt(cmdData.getValue()));
    }

    private void fileErase(int startLba, int size) {
        int endLba = startLba + size;
        for (int currentLba = startLba; currentLba < endLba; currentLba++) {
            if(currentLba > SSDConstant.MAX_LBA) break;
            fileManager.writeFile(currentLba, SSDConstant.ERASE_VALUE);
        }
    }

    private void fileErrorOutput() {
        fileManager.writeOnOutputFile(cmdData.getValue());
    }
}