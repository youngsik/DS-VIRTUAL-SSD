package com.samsung.file;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JarExecutor {
    private static final int DEFAULT_WAIT_MILLIS = 500;

    public void executeRead(Integer lba) {
        executeCommand("R", String.valueOf(lba));
    }

    public void executeWrite(Integer lba, String value) {
        executeCommand("W", lba.toString(), value);
    }

    public void executeErase(Integer startLba, Integer length) {
        executeCommand("E", startLba.toString(), length.toString());
    }

    public void executeFlush() {
        executeCommand("F");
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

            pb.start().waitFor(1000L, TimeUnit.MILLISECONDS);

            // Thread.sleep(DEFAULT_WAIT_MILLIS);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getSsdJarPath() {
        String projectRoot = System.getProperty("user.dir");
        return projectRoot + "\\ssd.jar";
    }
}
