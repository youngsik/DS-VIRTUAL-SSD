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

public class CommandBufferManager {

    private static final String BUFFER_DIR = "./ssd/buffer";
    private CmdData[] commandBuffer = new CmdData[SSDConstant.MAX_BUFFER_NUMBER];

    public CommandBufferManager() {
        createBufferDirectory();
        createEmptyFiles();
    }

    // buffer 폴더 생성 함수
    private void createBufferDirectory() {
        Path bufferPath = Paths.get(BUFFER_DIR);

        if (!Files.exists(bufferPath)) {
            try {
                Files.createDirectories(bufferPath);
            } catch (IOException e) {
                //ERROR
            }
        } else {
            //ERROR
        }
    }

    private void createEmptyFiles() {
        File bufferDir = new File(BUFFER_DIR);
        File[] files = bufferDir.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files != null && files.length >= SSDConstant.MAX_BUFFER_NUMBER) {
            return;
        }

        for (int i = SSDConstant.MIN_BUFFER_NUMBER; i <= SSDConstant.MAX_BUFFER_NUMBER; i++) {
            Path filePath = Paths.get(BUFFER_DIR, i + "_empty.txt");
            File file = filePath.toFile();

            if (!file.exists()) {
                try {
                    if (!file.createNewFile()) {
                        // ERROR
                    }
                } catch (IOException e) {
                    // ERROR
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
                if (index >= SSDConstant.MAX_BUFFER_NUMBER) {
                    break;
                }

                String fileName = file.getName();
                String[] parts = fileName.split("_");

                if (parts.length == 4) {
                    String command = parts[SSDConstant.MIN_BUFFER_NUMBER];
                    int lba = Integer.parseInt(parts[2]);
                    String value = parts[3].replace(".txt", "");

                    commandBuffer[index++] = new CmdData(command, lba, value);
                }
            }
        }
        for (CmdData cmd : commandBuffer) {
            if (cmd != null) {
                System.out.println(cmd);
            }
        }
    }

    public void processCommand(String command, int lba, String value) {
        int availableIndex = findAvailableFileIndex();
        if (availableIndex == -SSDConstant.MIN_BUFFER_NUMBER) {
            return;
        }

        String newFileName = String.format("%d_%s_%d_%s.txt", availableIndex, command, lba, value);

        Path oldFilePath = Paths.get(BUFFER_DIR, availableIndex + "_empty.txt");
        Path newFilePath = Paths.get(BUFFER_DIR, newFileName);

        try {
            Files.move(oldFilePath, newFilePath);
        } catch (IOException e) {
            //ERROR
        }
    }

    private int findAvailableFileIndex() {
        for (int i = SSDConstant.MIN_BUFFER_NUMBER; i <= SSDConstant.MAX_BUFFER_NUMBER; i++) {
            Path filePath = Paths.get(BUFFER_DIR, i + "_empty.txt");
            File file = filePath.toFile();

            if (file.exists() && file.getName().endsWith("empty.txt")) {
                return i;
            }
        }
        flushToFile();
        return SSDConstant.MIN_BUFFER_NUMBER;
    }

    public void flushToFile(){
        File bufferDir = new File(BUFFER_DIR);
        File[] files = bufferDir.listFiles((dir, name) -> name.matches("\\d+_.+\\.txt"));
        SSDManager ssdManager;

        for(File file : files){
            String[] commandList = file.getName().split("_");
            String command = commandList[SSDConstant.MIN_BUFFER_NUMBER];
            int lba = Integer.parseInt(commandList[2]);
            String value = commandList[3];

            ssdManager = new SSDManager(command, lba, value);
            ssdManager.cmdExecute();
        }
        deleteAndInitBuffer(files);
    }

    public void deleteAndInitBuffer(File[] files) {
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        createEmptyFiles();  // 빈 파일 다시 생성
    }

    // buffer에 저장된 명령어들을 정리하는 함수
    public void organizeCommand() {
        // 1) 현재 buffer에 저장된 명령어들을 가져온다.
        loadCommandsFromBuffer();

        // 2) buffer 명령어를 정리한다.
        // 이 부분은 나중에 불필요한 명령어를 정리하는 로직을 추가할 예정
        // 예를 들어, 오래된 명령어 또는 특정 조건을 만족하는 명령어를 삭제할 수 있음.
        // 임시로 빈 함수로 두겠습니다.

        // 3) 정리된 명령어를 새로 저장한다.
        // flush()로 buffer를 비운 후, 정리된 명령어들을 processCommand로 다시 저장
        flushToFile();

        // 정리된 명령어 배열에서 다시 저장
        for (CmdData cmd : commandBuffer) {
            if (cmd != null) {
                processCommand(cmd.getCommand(), cmd.getLba(), cmd.getValue());
            }
        }
    }

    private void ignoreCommand() {

    }

    private void mergeCommand() {

    }

    public String fastRead(int lba) {
        return "";
    }


}

