package com.samsung.buffer;

import com.samsung.common.CmdData;
import com.samsung.common.SSDConstant;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class BufferFolderManager {
    private static final String BUFFER_DIR = "./buffer";
    public static final int FULL_BUFFER = -1;

    public BufferFolderManager() {
        createBufferDirectory();
        createEmptyFiles();
    }

    public void createBufferDirectory() {
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


    public void deleteBuffer() {
        File targetDir = new File(BUFFER_DIR);

        if (targetDir.exists() && targetDir.isDirectory()) {
            deleteRecursively(targetDir);
        }

        // 2. 폴더 다시 생성
        targetDir.mkdirs();

        // 3. 빈 파일 5개 생성
        for (int i = 1; i <= 5; i++) {
            File emptyFile = new File(targetDir, i + "_empty.txt");
            try {
                emptyFile.createNewFile();  // 이미 존재하지 않는다는 전제
            } catch (IOException e) {
                System.err.println("파일 생성 실패: " + emptyFile.getName());
                e.printStackTrace();
            }
        }
    }

    public void updateFiles(CmdData cmdData, int index) {
        String newFileName = String.format("%d_%s_%d_%s.txt",
                index, cmdData.getCommand().getCode(), cmdData.getLba(), cmdData.getValue());
        Path oldFilePath = Paths.get(BUFFER_DIR, index + "_empty.txt");
        Path newFilePath = Paths.get(BUFFER_DIR, newFileName);

        try {
            Files.move(oldFilePath, newFilePath);
        } catch (IOException e) {
            log.error("[processCommand] 파일명 변경 오류");
        }
    }

    public int findAvailableBufferIndex() {
        for (int i = SSDConstant.MIN_BUFFER_INDEX; i <= SSDConstant.MAX_BUFFER_INDEX; i++) {
            Path filePath = Paths.get(BUFFER_DIR, i + "_empty.txt");
            File file = filePath.toFile();

            if (file.exists() && file.getName().endsWith("empty.txt")) {
                return i;
            }
        }
        return FULL_BUFFER;
    }

    private void deleteRecursively(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (f.isDirectory()) {
                    deleteRecursively(f);
                }
                f.delete();
            }
        }
    }
}
