package com.samsung.buffer;

import com.samsung.CmdData;
import com.samsung.SSDManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommandBufferManager {

    private static final String BUFFER_DIR = "./ssd/buffer";  // buffer 경로

    // 임시로 저장할 5개의 명령어 배열 (CmdData 객체 배열)
    private CmdData[] commandBuffer = new CmdData[5];

    // CommandBufferManager의 초기화 함수
    public CommandBufferManager() {
        // buffer 폴더가 없다면 생성
        createBufferDirectory();

        // buffer 폴더 안에 빈 텍스트 파일 생성
        createEmptyFiles();
    }

    // buffer 폴더 생성 함수
    private void createBufferDirectory() {
        Path bufferPath = Paths.get(BUFFER_DIR);

        // 폴더가 없다면 생성
        if (!Files.exists(bufferPath)) {
            try {
                // 상위 디렉토리(ssd)도 생성해야 할 수 있으므로, 먼저 상위 디렉토리를 확인하고 생성
                Files.createDirectories(bufferPath);
                System.out.println("Buffer directory created.");
            } catch (IOException e) {
                System.err.println("Error creating buffer directory: " + e.getMessage());
            }
        } else {
            System.out.println("Buffer directory already exists.");
        }
    }

    // buffer 폴더에 빈 텍스트 파일 생성 함수
    private void createEmptyFiles() {
        // 이미 buffer 디렉토리 안에 .txt 파일이 5개 이상 존재하는지 확인
        File bufferDir = new File(BUFFER_DIR);
        File[] files = bufferDir.listFiles((dir, name) -> name.endsWith(".txt"));

        // _empty.txt 파일이 5개 이상 존재한다면 더 이상 생성하지 않음
        if (files != null && files.length >= 5) {
            System.out.println("Already 5 '_empty.txt' files exist. No need to create new files.");
            return;  // 이미 5개 이상의 _empty.txt 파일이 존재하면 함수를 종료
        }

        // 파일이 5개 미만이라면 새로 생성
        for (int i = 1; i <= 5; i++) {
            Path filePath = Paths.get(BUFFER_DIR, i + "_empty.txt");
            File file = filePath.toFile();

            // 파일이 없다면 새로 생성
            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        System.out.println(filePath + " created.");
                    }
                } catch (IOException e) {
                    System.err.println("Error creating file " + filePath + ": " + e.getMessage());
                }
            }
        }
    }

    // buffer에 저장된 명령어들을 읽어오는 함수
    public void loadCommandsFromBuffer() {
        File bufferDir = new File(BUFFER_DIR);
        File[] files = bufferDir.listFiles((dir, name) -> name.matches("\\d+_.+\\.txt"));

        // 명령어 배열 초기화
        for (int i = 0; i < commandBuffer.length; i++) {
            commandBuffer[i] = null;  // 명령어 배열을 비웁니다.
        }

        int index = 0;
        if (files != null) {
            for (File file : files) {
                if (index >= 5) {
                    break;  // 최대 5개의 명령어만 읽어옵니다.
                }

                // 파일 이름에서 명령어, LBA, value를 추출
                String fileName = file.getName();
                String[] parts = fileName.split("_");

                // 파일명이 index_command_lba_value.txt 형식이라면
                if (parts.length == 4) {
                    String command = parts[1];
                    int lba = Integer.parseInt(parts[2]);
                    String value = parts[3].replace(".txt", "");

                    // CmdData 객체를 배열에 저장
                    commandBuffer[index++] = new CmdData(command, lba, value);
                }
            }
        }

        // 읽어온 명령어 출력 (디버깅용)
        for (CmdData cmd : commandBuffer) {
            if (cmd != null) {
                System.out.println(cmd);  // CmdData 클래스의 toString()을 사용하여 출력
            }
        }
    }

    // Command를 처리하고, 해당 파일명 변경하는 함수
    public void processCommand(String command, int lba, String value) {
        // 명령어를 저장할 파일 찾기
        int availableIndex = findAvailableFileIndex();

        if (availableIndex == -1) {
            System.out.println("Command buffer is full, cannot accept more commands.");
            return;
        }

        // 파일명을 새 명령어로 변경
        String newFileName = String.format("%d_%s_%d_%s.txt", availableIndex, command, lba, value);

        // 파일 경로
        Path oldFilePath = Paths.get(BUFFER_DIR, availableIndex + "_empty.txt");
        Path newFilePath = Paths.get(BUFFER_DIR, newFileName);

        // 파일명 변경
        try {
            Files.move(oldFilePath, newFilePath);
            System.out.println("File renamed to: " + newFileName);
        } catch (IOException e) {
            System.err.println("Error renaming file: " + e.getMessage());
        }
    }

    // 사용 가능한 파일 인덱스 찾기 (empty라는 이름을 가진 파일을 찾는 함수)
    private int findAvailableFileIndex() {
        for (int i = 1; i <= 5; i++) {
            Path filePath = Paths.get(BUFFER_DIR, i + "_empty.txt");
            File file = filePath.toFile();

            // 파일명이 "empty"인 파일을 찾으면 해당 인덱스를 반환
            if (file.exists() && file.getName().endsWith("empty.txt")) {
                return i;  // "empty.txt" 파일 인덱스 반환
            }
        }

        // 빈 파일이 없다면 버퍼가 꽉 찼다고 판단
        return -1;
    }

    // buffer에 저장된 명령어들을 정리하는 함수
    public void ignoreCommand() {
        // 1) 현재 buffer에 저장된 명령어들을 가져온다.
        loadCommandsFromBuffer();

        // 2) buffer 명령어를 정리한다.
        // 이 부분은 나중에 불필요한 명령어를 정리하는 로직을 추가할 예정
        // 예를 들어, 오래된 명령어 또는 특정 조건을 만족하는 명령어를 삭제할 수 있음.
        // 임시로 빈 함수로 두겠습니다.

        // 3) 정리된 명령어를 새로 저장한다.
        // flush()로 buffer를 비운 후, 정리된 명령어들을 processCommand로 다시 저장
        flush();

        // 정리된 명령어 배열에서 다시 저장
        for (CmdData cmd : commandBuffer) {
            if (cmd != null) {
                processCommand(cmd.getCommand(), cmd.getLba(), cmd.getValue());
            }
        }
    }

    // Buffer의 내용을 비우는 함수
    public void flush() {
        // 모든 명령어 파일을 지우고 _empty.txt 파일을 다시 생성
        File bufferDir = new File(BUFFER_DIR);
        File[] files = bufferDir.listFiles((dir, name) -> name.matches("\\d+_.+\\.txt"));

        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }

        createEmptyFiles();  // 빈 파일 다시 생성
    }

    private void flushToFile(){
        File bufferDir = new File(BUFFER_DIR);
        File[] files = bufferDir.listFiles((dir, name) -> name.matches("\\d+_.+\\.txt"));
        SSDManager ssdManager;
        for(File file : files){
            String[] commandList = file.getName().split("_");
            String command = commandList[1];
            int lba = Integer.parseInt(commandList[2]);
            String value = commandList[3];

            ssdManager = new SSDManager(command, lba, value);
            ssdManager.cmdExecute();
        }
    }
}

