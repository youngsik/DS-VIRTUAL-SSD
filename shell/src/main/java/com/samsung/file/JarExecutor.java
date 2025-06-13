package com.samsung.file;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JarExecutor {
    private static final int DEFAULT_WAIT_MILLIS = 1000;

    public void executeWrite(Integer lba, String value) {
        executeCommand("W", lba.toString(), value);
    }

    public void executeErase(Integer startLba, Integer length) {
        executeCommand("E", startLba.toString(), length.toString());
    }

    private void executeCommand(String... args) {
        try {
            List<String> command = new ArrayList<>();
            command.add("java");
            command.add("-jar");
            command.add(getSsdJarPath());
            command.addAll(List.of(args));

            ProcessBuilder pb = new ProcessBuilder(command);
            log.info("Executing command: {}", String.join(" ", command));
            System.out.println("Executing command: " + String.join(" ", command));

            pb.inheritIO();
            pb.start();

            Thread.sleep(DEFAULT_WAIT_MILLIS);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getSsdJarPath() {
        String projectRoot = System.getProperty("user.dir");
        return projectRoot + "\\ssd-all.jar";
    }
}
