package com.samsung.testscript;

import main.SsdApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScriptManager {
    private static final String READ_PREFIX_COMMAND = "R ";
    private static final String WRITE_PREFIX_COMMAND = "W ";
    private static final String TEST_VALUE = "0xAAAABBBB";

    private static final int SCRIPT1_LOOP = 100;
    private static final int SCRIPT1_TERM = 4;

    private final List<Integer> script2LbaOrder = new ArrayList<>(List.of(4, 0, 3, 1, 2));

    private final SsdApplication ssdApplication;

    public ScriptManager(SsdApplication ssdApplication) {
        this.ssdApplication = ssdApplication;
    }
    public Random random = new Random();

    public boolean testScript1(){
        int indexHeader = 0;
        boolean isSuccess = false;
        while (indexHeader <= SCRIPT1_LOOP - SCRIPT1_TERM) {
            isSuccess = verifyEachFourTimes(indexHeader, TEST_VALUE);
            indexHeader += SCRIPT1_TERM;
        }
        return isSuccess;
    }

    private boolean verifyEachFourTimes(int indexHeader, String verifyValue) {
        boolean isSuccess = false;
        for (int i = indexHeader; i < indexHeader + SCRIPT1_TERM; i++){
            write(i, verifyValue);
            isSuccess = readAndCompare(i, verifyValue);
        }
        return isSuccess;
    }

    public boolean testScript2() {
        for (Integer lba : script2LbaOrder) {
            write(lba, TEST_VALUE);
        }

        boolean isSuccess = false;
        for (int i = 0; i <= 4; i++){
            isSuccess = readAndCompare(i, TEST_VALUE);
        }

        return isSuccess;
    }

    public boolean testScript3(){
        boolean isSuccess = false;
        for (int i = 0; i < 100; i++){
            int randInt = random.nextInt(1000);
            String randHex = String.format("0x%08X", randInt);

            write(0, randHex);
            write(99, randHex);

            boolean isSuccessLbaFirst = readAndCompare(0, randHex);
            boolean isSuccessLbaLast = readAndCompare(99, randHex);

            isSuccess = isSuccessLbaFirst && isSuccessLbaLast;
        }
        return isSuccess;
    }

    private String read(Integer lba){
        String command = getReadCommand(lba);
        return ssdApplication.execute(command);
    }

    private String getReadCommand(Integer lba) {
        return READ_PREFIX_COMMAND + lba;
    }

    private void write(Integer lba, String value){
        String command = getWriteCommand(lba, value);
        ssdApplication.execute(command);
    }

    private String getWriteCommand(Integer lba, String value) {
        return WRITE_PREFIX_COMMAND + lba + " " + value;
    }

    private boolean readAndCompare(Integer lba, String compareValue){
        String readValue = read(lba);
        return compareValue.equals(readValue);
    }
}
