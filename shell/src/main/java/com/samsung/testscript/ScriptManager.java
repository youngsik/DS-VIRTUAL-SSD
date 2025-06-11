package com.samsung.testscript;

import main.SsdApplication;

public class ScriptManager {
    private static final String READ_PREFIX_COMMAND = "R ";
    private static final String WRITE_PREFIX_COMMAND = "W ";

    public static final int SCRIPT1_LOOP = 100;
    public static final int SCRIPT1_TERM = 4;

    private final SsdApplication ssdApplication;

    public ScriptManager(SsdApplication ssdApplication) {
        this.ssdApplication = ssdApplication;
    }

    public boolean testScript1(){
        int indexHeader = 0;
        boolean isSuccess = false;
        String verifyValue = "0xAAAABBBB";
        while (indexHeader <= SCRIPT1_LOOP - SCRIPT1_TERM) {
            isSuccess = verifyEachFourTimes(indexHeader, verifyValue);
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

    private String read(Integer lba){
        String command = READ_PREFIX_COMMAND + lba;
        return ssdApplication.execute(command);
    }

    private void write(Integer lba, String value){
        String command = WRITE_PREFIX_COMMAND + lba + " " + value;
        ssdApplication.execute(command);
    }

    private boolean readAndCompare(Integer lba, String compareValue){
        String readValue = read(lba);
        return compareValue.equals(readValue);
    }

}
