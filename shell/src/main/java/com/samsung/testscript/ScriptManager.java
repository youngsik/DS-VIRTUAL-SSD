package com.samsung.testscript;

import main.SsdApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScriptManager {
    private static final String READ_PREFIX_COMMAND = "R ";
    private static final String WRITE_PREFIX_COMMAND = "W ";
    private static final String TEST_VALUE = "0xAAAABBBB";

    private static final int TEST_LOOP = 100;
    private static final int VERIFY_TERM = 4;

    public static final int MAX_RAND_BOUND = 1000;
    public static final int LBA_FIRST = 0;
    public static final int LBA_LAST = 99;

    private final List<Integer> script2LbaOrder = new ArrayList<>(List.of(4, LBA_FIRST, 3, 1, 2));

    private final SsdApplication ssdApplication;

    public ScriptManager(SsdApplication ssdApplication) {
        this.ssdApplication = ssdApplication;
    }
    public Random random = new Random();

    public boolean testScript1(){
        int indexHeader = LBA_FIRST;
        boolean isSuccess = false;
        while (indexHeader <= TEST_LOOP - VERIFY_TERM) {
            isSuccess = verifyEachFourTimes(indexHeader, TEST_VALUE);
            indexHeader += VERIFY_TERM;
        }
        return isSuccess;
    }

    private boolean verifyEachFourTimes(int indexHeader, String verifyValue) {
        boolean isSuccess = false;
        for (int i = indexHeader; i < indexHeader + VERIFY_TERM; i++){
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
        for (int i = 0; i <= VERIFY_TERM; i++){
            isSuccess = readAndCompare(i, TEST_VALUE);
        }

        return isSuccess;
    }

    public boolean testScript3(){
        boolean isSuccess = false;
        for (int i = 0; i < TEST_LOOP; i++){
            String randHex = getRandomHex();

            boolean isSuccessLbaFirst = writeAndVerify(LBA_FIRST, randHex);
            boolean isSuccessLbaLast = writeAndVerify(LBA_LAST, randHex);

            isSuccess = isSuccessLbaFirst && isSuccessLbaLast;
        }
        return isSuccess;
    }

    private boolean writeAndVerify(Integer lba, String randHex) {
        write(lba, randHex);
        return readAndCompare(lba, randHex);
    }

    private String getRandomHex() {
        int randInt = random.nextInt(MAX_RAND_BOUND);
        return String.format("0x%08X", randInt);
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
