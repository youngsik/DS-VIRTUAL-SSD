package com.samsung.file;

import java.io.IOException;

public class JarExecutor {
    public void executeWrite(Integer lba, String value){
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-jar",
                    getSsdJarPath(),
                    "W", lba.toString(), value);

            System.out.println(pb.command());
            pb.inheritIO();
            pb.start();
            Thread.sleep(1000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void executeErase(Integer startLba, Integer length){
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-jar",
                    getSsdJarPath(),
                    "E", startLba.toString(), length.toString());

            System.out.println(pb.command());
            pb.inheritIO();
            pb.start();
            Thread.sleep(1000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    private String getSsdJarPath() {
        String projectRoot = System.getProperty("user.dir");
        return projectRoot + "\\ssd.jar";
    }
}
