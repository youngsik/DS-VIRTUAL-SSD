package com.samsung.testshell;

import com.samsung.SsdApplication;
import com.samsung.file.FileManager;

import java.io.File;
import java.util.List;

public class TestShellManager {

    SsdApplication ssdApplicatioin;
    FileManager fileManager;

    public static final String BLANK_DATA = "0x00000000";
    public static final String SSD_NAND_FILE_NAME = "ssd_nand.txt";

    public TestShellManager(SsdApplication ssdApplicatioin, FileManager fileManager) {
        this.ssdApplicatioin = ssdApplicatioin;
        this.fileManager =fileManager;
    }

    public void write(int index, String value) {
        String head = "[Write]";
        String pass = "Done";
        ssdApplicatioin.execute("W" + " " + index + " " + value );

        String output = head + " " + pass;
        System.out.println(output);
    }

    public void read(int index) {
        String head = "[Read] LBA";
        String location = String.format("%02d", index);
        String value;
        ssdApplicatioin.execute("R" + " " + index);
        value = fileManager.getValue(index);

        String output = head + " " + location + " " + value;
        System.out.println(output);
    }


    public void exit() {
        // exit 실행
    }

    public void help() {
        // help 실행
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
        String head = "[Full Read] LBA ";

        File file = fileManager.getOrCreateFile(SSD_NAND_FILE_NAME);
        List<String> listvalues = fileManager.getDataFromNandFile(file);

        for (String value : listvalues) {
            System.out.println(value);
        }

    }
}
