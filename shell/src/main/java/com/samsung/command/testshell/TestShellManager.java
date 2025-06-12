package com.samsung.command.testshell;

import com.samsung.file.FileManager;
import com.samsung.file.JarExecutor;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestShellManager {

    public static final String writeCmd = "W";
    public static final String ReadCmd = "R";

    private final JarExecutor jarExecutor;
    private final FileManager fileManager;

    public static final String BLANK_DATA = "0x00000000";
    public static final String SSD_NAND_FILE_NAME = "ssd_nand.txt";

    public TestShellManager(JarExecutor jarExecutor, FileManager fileManager) {
        this.jarExecutor = jarExecutor;
        this.fileManager =fileManager;
    }

    public void write(int index, String value) {
        String head = "[Write]";
        String pass = "Done";

        jarExecutor.executeWriteJar(index, value);

        String output = head + " " + pass;
        System.out.println(output);
    }

    public void read(int index) {
        String head = "[Read] LBA";
        String location = String.format("%02d", index);
        String value;

        fileManager.readFile(index);
        value = fileManager.getHashmap().getOrDefault(index, BLANK_DATA);

        String output = head + " " + location + " " + value;
        System.out.println(output);
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
        System.out.println("  help                      사용 가능한 명령어를 출력합니다.");
        System.out.println("  exit                      프로그램을 종료합니다.");
        System.out.println("Copyright (c) 2025 DeviceSolution. All rights reserved.");
        System.out.println();


    }

    public void fullwrite(String value) {
        String head = "[Full Write]";
        String pass = "Done";

        for(int i=0; i<100; i++) {
            jarExecutor.executeWriteJar(i, value);
        }
        System.out.println(head + " " + pass);
    }

    public void fullread() {
        String head = "[Full Read] LBA";

        for (int index = 0; index < 100; index++) {
            fileManager.readFile(index);
            String value = fileManager.getHashmap().getOrDefault(index, BLANK_DATA);

            String location = String.format("%02d", index);
            String output = head + " " + location + " " + value;

            System.out.println(output);
        }
    }

    public void erase(int eraseLBA, int eraseLength) {
        if(eraseLength == 0) {
            return;
        }

        int startLBA = eraseLBA;
        int finishLBA = getFinishLBA(startLBA, eraseLength);

        if(startLBA > finishLBA) {
            int tempLBA = startLBA;
            startLBA = finishLBA;
            finishLBA = tempLBA;
        }

        for(int i = startLBA; i <= finishLBA; i += 10) {
            // jarExecutor.executeErase(i, getEraseLength(i, finishLBA));
            System.out.println("SSD : [ERASE] " + i + " " + getEraseLength(i, finishLBA));
        }

        System.out.println("[ERASE] " + eraseLBA + " " + eraseLength + " [DONE]");
    }

    public void eraseRange(int startLBA, int finishLBA) {
        for(int i = startLBA; i <= finishLBA; i += 10) {
            // jarExecutor.executeErase(i, getEraseLength(i, finishLBA));
            System.out.println("SSD : [ERASE] " + i + " " + getEraseLength(i, finishLBA));
        }

        System.out.println("[ERASE] " + startLBA + " " + finishLBA + " [DONE]");
    }

    private int getFinishLBA(int startLBA, int eraseLength) {
        int res = startLBA + eraseLength;
        res = res + (eraseLength < 0 ? 1 : -1);

        if(res < 0) {
            return 0;
        }
        else if(res >= 100) {
            return 99;
        }
        else{
            return res;
        }
    }

    private int getEraseLength(int currentLBA, int endLBA) {
        if(currentLBA + 10 > endLBA) {
            return endLBA - currentLBA + 1;
        }
        else{
            return 10;
        }
    }
}
