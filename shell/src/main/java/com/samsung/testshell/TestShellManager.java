package com.samsung.testshell;

import com.samsung.FileManager;
import com.samsung.SsdApplication;

public class TestShellManager {

    SsdApplication ssdApplicatioin;
    FileManager fileManager;


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
        // fullwrite 실행
    }

    public void fullread() {
        // fullread 실행
    }
}
