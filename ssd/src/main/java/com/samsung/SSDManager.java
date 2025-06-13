package com.samsung;

import com.samsung.buffer.CommandBufferManager;
import com.samsung.file.FileManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SSDManager {
    private String command = "";
    private int lba = -1;
    private String value = "";
    private final FileManager fileManager;

    public SSDManager(CmdData cmdData) {
        this.command = cmdData.getCommand();
        this.lba = cmdData.getLba();
        this.value = cmdData.getValue();
        this.fileManager = FileManager.getInstance();
    }

    public void cmdExecute() {
        if (command.equals(SSDConstant.COMMAND_ERROR)) fileErrorOutput();
        else if (command.equals(SSDConstant.COMMAND_READ)) fileManager.readFile(lba);
        else if (command.equals(SSDConstant.COMMAND_WRITE) || command.equals(SSDConstant.COMMAND_ERASE)) {
            CommandBufferManager commandBufferManager = new CommandBufferManager();
            commandBufferManager.processCommand(command, lba, value);
        }
        else if(command.equals(SSDConstant.COMMAND_FLUSH)) {
            CommandBufferManager commandBufferManager = new CommandBufferManager();
            commandBufferManager.flushToFile();;
        }
    }

    public void cmdExecuteFromBuffer() {
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
        log.info("[fileErrorOutput] Error output 생성");
    }
}