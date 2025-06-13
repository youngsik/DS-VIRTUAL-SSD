package com.samsung;

import com.samsung.file.FileManager;

import static com.samsung.CommandType.*;

class SSDManager {
    private final CmdData cmdData;
    private final FileManager fileManager;

    public SSDManager(CmdData cmdData, FileManager fileManager) {
        this.cmdData = cmdData;
        this.fileManager = fileManager;
    }

    public void cmdExecute() {
        if (cmdData.getCommand().equals(ERROR)) fileErrorOutput();
        else if (cmdData.getCommand().equals(READ)) fileManager.readFile(cmdData.getLba());
        else if (cmdData.getCommand().equals(WRITE)) fileManager.writeFile(cmdData.getLba(), cmdData.getValue());
        else if (cmdData.getCommand().equals(ERASE)) fileErase(cmdData.getLba(), Integer.parseInt(cmdData.getValue()));
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