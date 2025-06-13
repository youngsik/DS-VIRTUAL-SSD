package com.samsung.ssd;

import com.samsung.buffer.BufferFolderManager;
import com.samsung.buffer.BufferProcessor;
import com.samsung.common.CmdData;
import com.samsung.common.CommandType;
import com.samsung.common.SSDConstant;
import com.samsung.file.FileManager;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.samsung.file.FileConstants.BLANK_DATA;
import static com.samsung.common.CommandType.*;

@Slf4j
public class SSDManager {
    public static final int FULL_BUFFER = -1;
    private CmdData cmdData;
    private final FileManager fileManager;
    private final BufferProcessor bufferProcessor;
    private final BufferFolderManager bufferFolderManager;

    private static final String BUFFER_DIR = "./buffer";
    private final CmdData[] commandBuffer = new CmdData[SSDConstant.MAX_BUFFER_INDEX];

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

    private boolean isReadOrFlush() {
        return cmdData.getCommand().equals(READ) || cmdData.getCommand().equals(FLUSH);
    }

    private void executeSingleCommand(CmdData cmd) {
        CommandType command = cmd.getCommand();
        switch (command) {
            case READ -> handleRead(cmd);
            case WRITE, ERASE -> executeCommandInBuffer(cmd);
            case FLUSH -> flush();
        }
    }

    private void handleRead(CmdData cmd) {
        String result = bufferProcessor.process(cmd);
        if (BLANK_DATA.equals(result)) {
            fileManager.readFile(cmd.getLba());
        }
        fileManager.writeOnOutputFile(result);
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
    }

    private void fileErase(int startLba, int size) {
        int endLba = startLba + size;
        for (int currentLba = startLba; currentLba < endLba; currentLba++) {
            if (currentLba > SSDConstant.MAX_LBA) break;
            fileManager.writeFile(currentLba, SSDConstant.ERASE_VALUE);
        }
    }

    public List<CmdData> loadCommandsFromBuffer() {
        File bufferDir = new File(BUFFER_DIR);
        File[] files = bufferDir.listFiles((dir, name) -> name.matches("\\d+_.+\\.txt"));

        Arrays.fill(commandBuffer, null);

        int index = 0;
        if (files != null) {
            for (File file : files) {
                if (index >= SSDConstant.MAX_BUFFER_INDEX) {
                    break;
                }

                String fileName = file.getName();
                String[] parts = fileName.split("_");

                if (parts.length == 4) {
                    CommandType command = CommandType.fromCode(parts[1]);
                    int lba = Integer.parseInt(parts[2]);
                    String value = parts[3].replace(".txt", "");

                    commandBuffer[index++] = new CmdData(command, lba, value);
                }
            }
        }
        return Arrays.stream(commandBuffer)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void executeCommandInBuffer(CmdData cmdData) {
        int availableIndex = findAvailableBufferIndex();
        if (availableIndex == FULL_BUFFER) {
            flush();
            availableIndex = 1;
        }

        String newFileName = String.format("%d_%s_%d_%s.txt",
                availableIndex, cmdData.getCommand().getCode(), cmdData.getLba(), cmdData.getValue());
        Path oldFilePath = Paths.get(BUFFER_DIR, availableIndex + "_empty.txt");
        Path newFilePath = Paths.get(BUFFER_DIR, newFileName);

        try {
            Files.move(oldFilePath, newFilePath);
        } catch (IOException e) {
            log.error("[processCommand] 파일명 변경 오류");
        }
    }

    private int findAvailableBufferIndex() {
        for (int i = SSDConstant.MIN_BUFFER_INDEX; i <= SSDConstant.MAX_BUFFER_INDEX; i++) {
            Path filePath = Paths.get(BUFFER_DIR, i + "_empty.txt");
            File file = filePath.toFile();

            if (file.exists() && file.getName().endsWith("empty.txt")) {
                return i;
            }
        }
        return FULL_BUFFER;
    }

    public void flush() {
        List<CmdData> cmdDataList = loadCommandsFromBuffer();

        for (CmdData cmd : cmdDataList) {
            CommandType command = cmd.getCommand();
            if (command.equals(WRITE)) fileManager.writeFile(cmd.getLba(), cmd.getValue());
            else if (command.equals(ERASE)) fileErase(cmd.getLba(), Integer.parseInt(cmd.getValue()));
        }

        bufferFolderManager.deleteBuffer();
    }
}