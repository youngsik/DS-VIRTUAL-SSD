package com.samsung;

import com.samsung.buffer.BufferProcessor;
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

import static com.samsung.CommandType.*;

@Slf4j
class SSDManager {
    private CmdData cmdData;
    private final FileManager fileManager;
    private final BufferProcessor bufferProcessor;

    private static final String BUFFER_DIR = "./ssd/buffer";
    private final CmdData[] commandBuffer = new CmdData[SSDConstant.MAX_BUFFER_INDEX];

    public SSDManager(CmdData cmdData, FileManager fileManager, BufferProcessor bufferProcessor) {
        this.cmdData = cmdData;
        this.fileManager = fileManager;
        this.bufferProcessor =  bufferProcessor;

        createBufferDirectory();
        createEmptyFiles();
    }

    public void cmdExecuteFromBuffer() {
        CommandType command = cmdData.getCommand();
        if (command.equals(ERROR)) fileErrorOutput();
        else if (command.equals(READ)) fileManager.readFile(cmdData.getLba());
        else if (command.equals(WRITE)) fileManager.writeFile(cmdData.getLba(), cmdData.getValue());
        else if (command.equals(ERASE)) fileErase(cmdData.getLba(), Integer.parseInt(cmdData.getValue()));
        else if (command.equals(FLUSH)) flush();

        applyBufferAlgorithm();
    }

    private void applyBufferAlgorithm() {
        List<CmdData> loadedCmdList = loadCommandsFromBuffer();

        for (CmdData command : loadedCmdList) {
            bufferProcessor.process(command);
        }
        List<CmdData> calculatedCmcList = bufferProcessor.getBuffer();

        deleteFiles();
        createEmptyFiles();
        for (CmdData command : calculatedCmcList) {
            processCommand(command);
        }
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
        log.info("[fileErrorOutput] Error output 생성");
    }

    private void createBufferDirectory() {
        Path bufferPath = Paths.get(BUFFER_DIR);
        if (Files.exists(bufferPath)) return;

        try {
            Files.createDirectories(bufferPath);
        } catch (IOException e) {
            log.error("[createBufferDirectory] 파일 생성 오류");
        }
    }

    private void createEmptyFiles() {
        File bufferDir = new File(BUFFER_DIR);
        File[] files = bufferDir.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files != null && files.length >= SSDConstant.MAX_BUFFER_INDEX) {
            return;
        }

        for (int i = SSDConstant.MIN_BUFFER_INDEX; i <= SSDConstant.MAX_BUFFER_INDEX; i++) {
            Path filePath = Paths.get(BUFFER_DIR, i + "_empty.txt");
            File file = filePath.toFile();

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    log.error("[createEmptyFiles] 파일 생성 오류");
                }
            }
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
        return new ArrayList<>(List.of(commandBuffer));
    }

    public void processCommand(CmdData cmdData) {
        int availableIndex = findAvailableFileIndex();
        if (availableIndex == -SSDConstant.MIN_BUFFER_INDEX) {
            return;
        }

        String newFileName = String.format("%d_%s_%d_%s.txt",
                availableIndex, cmdData.getCommand(), cmdData.getLba(), cmdData.getValue());
        Path oldFilePath = Paths.get(BUFFER_DIR, availableIndex + "_empty.txt");
        Path newFilePath = Paths.get(BUFFER_DIR, newFileName);

        try {
            Files.move(oldFilePath, newFilePath);
        } catch (IOException e) {
            log.error("[processCommand] 파일명 변경 오류");
        }
    }

    private int findAvailableFileIndex() {
        for (int i = SSDConstant.MIN_BUFFER_INDEX; i <= SSDConstant.MAX_BUFFER_INDEX; i++) {
            Path filePath = Paths.get(BUFFER_DIR, i + "_empty.txt");
            File file = filePath.toFile();

            if (file.exists() && file.getName().endsWith("empty.txt")) {
                return i;
            }
        }
        flush();
        return SSDConstant.MIN_BUFFER_INDEX;
    }

    public void flush(){
        loadCommandsFromBuffer();

        for (CmdData cmd : commandBuffer) {
            if(cmd == null) break;
            this.cmdData = cmd;

            cmdExecuteFromBuffer();
        }
        deleteFiles();
        createEmptyFiles();  // 빈 파일 다시 생성
    }

    public void deleteFiles() {
        File bufferDir = new File(BUFFER_DIR);
        File[] files = bufferDir.listFiles((dir, name) -> name.matches("\\d+_.+\\.txt"));

        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }
}