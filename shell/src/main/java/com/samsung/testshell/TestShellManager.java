package com.samsung.testshell;

import com.samsung.SsdApplication;
import com.samsung.file.FileManager;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestShellManager {

    public static final String writeCmd = "W";
    public static final String ReadCmd = "R";
    private final SsdApplication ssdApplicatioin;
    private final FileManager fileManager;

    public static final String BLANK_DATA = "0x00000000";
    public static final String SSD_NAND_FILE_NAME = "ssd_nand.txt";

    public TestShellManager(SsdApplication ssdApplicatioin, FileManager fileManager) {
        this.ssdApplicatioin = ssdApplicatioin;
        this.fileManager =fileManager;
    }

    public void write(int index, String value) {
        String head = "[Write]";
        String pass = "Done";

        StringBuilder sb = new StringBuilder();
        sb.append(writeCmd)
                .append(" ")
                .append(index)
                .append(" ")
                .append(value);

        ssdApplicatioin.execute(sb.toString() );

        String output = head + " " + pass;
        System.out.println(output);
    }

    public void read(int index) {
        String head = "[Read] LBA";
        String location = String.format("%02d", index);
        String value;

        StringBuilder sb = new StringBuilder();
        sb.append(ReadCmd)
                .append(" ")
                .append(index);

        ssdApplicatioin.execute(sb.toString() );

        fileManager.readFile(index);
        value = fileManager.getHashmap().get(index);

        String output = head + " " + location + " " + value;
        System.out.println(output);
    }

    public void exit() {
        System.exit(0);
    }

    public void help() {
        System.out.println("DeviceSolution");
        System.out.println();
        System.out.println("명령어:");
        System.out.println("  write <index> <value>     지정된 index에 value를 기록합니다. 예: write 3 0xAAAABBBB");
        System.out.println("  read <index>              지정된 index의 값을 읽어옵니다. 예: read 3");
        System.out.println("  fullwrite <value>         전체 영역에 value를 기록합니다. 예: fullwrite 0xAAAABBBB");
        System.out.println("  fullread                  전체 영역을 읽어옵니다.");
        System.out.println("  help                      사용 가능한 명령어를 출력합니다.");
        System.out.println("  exit                      프로그램을 종료합니다.");
        System.out.println();
        System.out.println("개발자: 김영식, 박준경, 권희정, 권성민, 이상훈, 오시훈, 추준성");

    }

    public void fullwrite(String value) {
        String head = "[Full Write]";
        String pass = "Done";

        for(int i=0; i<100; i++) {
            ssdApplicatioin.execute("W" + " " + i + " " + value);
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
}
