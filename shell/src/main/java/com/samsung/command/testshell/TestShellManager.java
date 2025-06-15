package com.samsung.command.testshell;

import com.samsung.file.FileManager;
import com.samsung.file.JarExecutor;
import lombok.extern.slf4j.Slf4j;

import static com.samsung.command.testshell.TestShellConstants.HELP_MESSAGE;

@Slf4j
public class TestShellManager {

    private final JarExecutor jarExecutor;
    private final FileManager fileManager;

    public TestShellManager(JarExecutor jarExecutor, FileManager fileManager) {
        this.jarExecutor = jarExecutor;
        this.fileManager = fileManager;
    }

    public void write(int index, String value) {
        String head = "[Write]";
        String pass = "Done";

        jarExecutor.executeWrite(index, value);

        String output = head + " " + pass;
        System.out.println(output);
    }

    public void read(int index) {
        String head = "[Read] LBA";
        String location = String.format("%02d", index);

        Long requestCommandTime = System.currentTimeMillis();
        jarExecutor.executeRead(index);
        String value = fileManager.getResultFromOutputFile();

        System.out.println(head + " " + location + " " + value);
    }

    public void exit() {
        System.exit(0);
    }

    public void help() {
        for(String s : HELP_MESSAGE){
            System.out.println(s);
        }
    }

    public void fullwrite(String value) {
        String head = "[Full Write]";
        String pass = "Done";

        for (int i = 0; i < 100; i++) {
            jarExecutor.executeWrite(i, value);
        }
        System.out.println(head + " " + pass);
    }

    public void fullread() {
        String head = "[Full Read] LBA";

        for(int i = 0; i < 100; i++) {
            Long requestCommandTime = System.currentTimeMillis();
            jarExecutor.executeRead(i);
            String value = fileManager.getResultFromOutputFile();

            System.out.println(head + " " + String.format("%02d", i) + " " + value);
        }
    }

    public void erase(int eraseLBA, int eraseSize) {
        if(eraseSize == 0) {
            return;
        }

        LBARange range = new LBARange(eraseLBA, getFinishLBA(eraseLBA, eraseSize));

        for(int i = range.start; i <= range.end; i += 10) {
            jarExecutor.executeErase(i, getEraseSize(i, range.end));
        }

        System.out.println("[ERASE] " + eraseLBA + " " + eraseSize + " DONE");
    }

    public void eraseRange(int startLBA, int finishLBA) {
        LBARange range = new LBARange(startLBA, finishLBA);

        for(int i = range.start; i <= range.end; i += 10) {
            jarExecutor.executeErase(i, getEraseSize(i, range.end));
        }

        System.out.println("[ERASE] " + startLBA + " " + finishLBA + " DONE");
    }

    public void flush() {
        jarExecutor.executeFlush();
        System.out.println("[FLUSH] DONE");
    }

    private int getFinishLBA(int startLBA, int eraseSize) {
        int res = startLBA + eraseSize;
        res += (eraseSize < 0 ? 1 : -1);

        return Math.min(99, Math.max(0, res));
    }

    private int getEraseSize(int currentLBA, int endLBA) {
        return Math.min(10, endLBA - currentLBA + 1);
    }

    private static class LBARange {
        public int start;
        public int end;

        public LBARange(int start, int end) {
            if(start > end) {
                this.start = end;
                this.end = start;
            }
            else{
                this.start = start;
                this.end = end;
            }
        }
    }
}
