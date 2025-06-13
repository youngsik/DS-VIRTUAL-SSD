package com.samsung.command.testshell;

import com.samsung.file.FileManager;
import com.samsung.file.JarExecutor;
import lombok.extern.slf4j.Slf4j;

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
        String value = fileManager.getResultFromOutputFile(requestCommandTime);

        System.out.println(head + " " + location + " " + value);
    }

    public void exit() {
        System.exit(0);
    }

    public void help() {
        System.out.println("제작자");
        System.out.println("팀명: DeviceSolution");
        System.out.println("팀원: 김영식, 박준경, 권희정, 권성민, 이상훈, 오시훈, 추준성");
        System.out.println();
        System.out.println("명령어");
        System.out.println("  write [LBA] [Value]     지정된 index에 value를 기록합니다. 예: write 3 0xAAAABBBB");
        System.out.println("  read [LBA]              지정된 index의 값을 읽어옵니다. 예: read 3");
        System.out.println("  erase [LBA] [Length]    지정된 LBA 부터 Length 길이만큼을 SSD에서 삭제합니다. 예 : erase 0 10");
        System.out.println("  erase_range [LBA1] [LBA2]    지정된 범위의 데이터를 SSD에서 삭제합니다. 예 : erase_range 10 20");
        System.out.println("  fullwrite  [Value]         전체 영역에 value를 기록합니다. 예: fullwrite 0xAAAABBBB");
        System.out.println("  fullread                  전체 영역을 읽어옵니다.");
        System.out.println("  flush                     SSD 버퍼를 비웁니다. 예 : flush");
        System.out.println("  help                      사용 가능한 명령어를 출력합니다.");
        System.out.println("  exit                      프로그램을 종료합니다.");
        System.out.println("Copyright (c) 2025 DeviceSolution. All rights reserved.");
        System.out.println();
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
            String value = fileManager.getResultFromOutputFile(requestCommandTime);

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
