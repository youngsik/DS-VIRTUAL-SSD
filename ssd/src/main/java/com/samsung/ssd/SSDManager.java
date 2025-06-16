package com.samsung.ssd;

import com.samsung.buffer.BufferFolderManager;
import com.samsung.buffer.BufferProcessor;
import com.samsung.common.CmdData;
import com.samsung.common.CommandType;
import com.samsung.common.SSDConstants;
import com.samsung.file.FileManager;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.samsung.common.CommandType.*;
import static com.samsung.common.SSDConstants.EMPTY_VALUE;

@Slf4j
public class SSDManager {
    public static final int FULL_BUFFER = -1;
    private CmdData cmdData;
    private final FileManager fileManager;
    private final BufferProcessor bufferProcessor;
    private final BufferFolderManager bufferFolderManager;

    private final CmdData[] commandBuffer = new CmdData[SSDConstants.MAX_BUFFER_INDEX];

    public SSDManager(CmdData cmdData, FileManager fileManager, BufferProcessor bufferProcessor) {
        this.cmdData = cmdData;
        this.fileManager = fileManager;
        this.bufferProcessor = bufferProcessor;
        this.bufferFolderManager = new BufferFolderManager();

        applyBufferAlgorithm();
    }

    public void cmdExecuteFromBuffer() {
        List<CmdData> calculatedCmdList = bufferProcessor.getBuffer();
        if (calculatedCmdList.size() == 5 && !isReadOrFlush()) {
            flush();
            bufferProcessor.clear();
        }
        executeSingleCommand(cmdData);

        if (isReadOrFlush()) return;
        applyBufferAlgorithm();
    }

    private void executeSingleCommand(CmdData cmd) {
        CommandType command = cmd.getCommand();
        switch (command) {
            case READ -> handleRead(cmd);
            case WRITE, ERASE -> executeCommandInBuffer(cmd);
            case FLUSH -> flush();
        }
    }

    private void applyBufferAlgorithm() {
        List<CmdData> loadedCmdList = loadCommandsFromBuffer();
        for (CmdData command : loadedCmdList) {
            bufferProcessor.process(command);
        }

        List<CmdData> calculatedCmdList = bufferProcessor.getBuffer();

        bufferFolderManager.deleteBuffer();

        for (CmdData command : calculatedCmdList) {
            executeCommandInBuffer(command);
        }
        bufferProcessor.clear();
    }

    private void handleRead(CmdData cmd) {
        String result = bufferProcessor.process(cmd);
        if (EMPTY_VALUE.equals(result)) {
            fileManager.readFile(cmd.getLba());
        }
        fileManager.writeOnOutputFile(result);
    }

    private void executeCommandInBuffer(CmdData cmdData) {
        bufferFolderManager.updateFiles(cmdData, getAvailableIndex());
    }

    private void flush() {
        List<CmdData> cmdDataList = loadCommandsFromBuffer();

        for (CmdData cmd : cmdDataList) {
            CommandType command = cmd.getCommand();
            if (command.equals(WRITE)) fileManager.writeFile(cmd.getLba(), cmd.getValue());
            else if (command.equals(ERASE)) fileErase(cmd.getLba(), Integer.parseInt(cmd.getValue()));
        }

        bufferFolderManager.deleteBuffer();
    }

    private List<CmdData> loadCommandsFromBuffer() {
        Arrays.fill(commandBuffer, null);

        int index = 0;
        List<CmdData> list = bufferFolderManager.getCmdDataFromBuffer(index);
        for(CmdData cmdData : list){
            commandBuffer[index++] = cmdData;
        }

        return Arrays.stream(commandBuffer)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void fileErase(int startLba, int size) {
        int endLba = startLba + size;
        for (int currentLba = startLba; currentLba < endLba; currentLba++) {
            if (currentLba > SSDConstants.MAX_LBA) break;
            fileManager.writeFile(currentLba, EMPTY_VALUE);
        }
    }

    private int getAvailableIndex() {
        int availableIndex = bufferFolderManager.findAvailableBufferIndex();
        if (availableIndex == FULL_BUFFER) {
            flush();
            availableIndex = 1;
        }
        return availableIndex;
    }

    private boolean isReadOrFlush() {
        return cmdData.getCommand().equals(READ) || cmdData.getCommand().equals(FLUSH);
    }
}