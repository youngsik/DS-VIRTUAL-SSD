package com.samsung.buffer;

import com.samsung.CmdData;
import com.samsung.SSDConstant;
import com.samsung.SSDManager;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import com.samsung.file.FileManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandBufferManager {

    private static final String BUFFER_DIR = "./ssd/buffer";
    private CmdData[] commandBuffer = new CmdData[SSDConstant.MAX_BUFFER_INDEX];

    public CommandBufferManager() {
        createBufferDirectory();
        createEmptyFiles();
    }
    private void createBufferDirectory() {
        Path bufferPath = Paths.get(BUFFER_DIR);
        if (Files.exists(bufferPath)) return;

        try {
            Files.createDirectories(bufferPath);
        } catch (IOException e) {
            log.info("[createBufferDirectory] 파일 생성 오류");
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
                    log.info("[createEmptyFiles] 파일 생성 오류");
                }
            }
        }
    }

    public void loadCommandsFromBuffer() {
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
                    String command = parts[SSDConstant.MIN_BUFFER_INDEX];
                    int lba = Integer.parseInt(parts[2]);
                    String value = parts[3].replace(".txt", "");

                    commandBuffer[index++] = new CmdData(command, lba, value);
                }
            }
        }
    }

    public void processCommand(String command, int lba, String value) {
        int availableIndex = findAvailableFileIndex();
        if (availableIndex == -SSDConstant.MIN_BUFFER_INDEX) {
            return;
        }

        String newFileName = String.format("%d_%s_%d_%s.txt", availableIndex, command, lba, value);
        Path oldFilePath = Paths.get(BUFFER_DIR, availableIndex + "_empty.txt");
        Path newFilePath = Paths.get(BUFFER_DIR, newFileName);

        try {
            Files.move(oldFilePath, newFilePath);
        } catch (IOException e) {
            log.info("[processCommand] 파일명 변경 오류");
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
        SSDManager ssdManager;

        for (CmdData cmd : commandBuffer) {
            if(cmd == null) break;
            ssdManager = new SSDManager(cmd, FileManager.getInstance(), new CommandBufferManager());
            ssdManager.cmdExecuteFromBuffer();
        }
        deleteAndInitBuffer();
    }

    public void deleteAndInitBuffer() {
        File bufferDir = new File(BUFFER_DIR);
        File[] files = bufferDir.listFiles((dir, name) -> name.matches("\\d+_.+\\.txt"));

        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        createEmptyFiles();  // 빈 파일 다시 생성
    }

    // buffer에 저장된 명령어들을 정리하는 함수
    public void organizeCommand() {
    }

    private void ignoreCommand() {

    }

    private void mergeCommand() {

    }

    public String fastRead(int lba) {
        return "";
    }


}

