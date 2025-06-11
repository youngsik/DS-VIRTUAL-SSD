package com.samsung.file;

import java.io.IOException;

public class JarExecutor {
    public void executeWriteJar(Integer lba, String value){
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

    private String getSsdJarPath() {
        String projectRoot = System.getProperty("user.dir");
        return projectRoot + "\\ssd.jar";
    }
}
