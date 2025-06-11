package com.samsung.testscript;

import main.SsdApplication;

public class ScriptManager {
    private static final String READ_PREFIX_COMMAND = "R ";
    private static final String WRITE_PREFIX_COMMAND = "W ";
    private final SsdApplication ssdApplication;

    public ScriptManager(SsdApplication ssdApplication) {
        this.ssdApplication = ssdApplication;
    }

    public void testScript1(){
        int indexHeader = 0;
        String verifyValue = "0xAAAABBBB";
        while (indexHeader <= 96) {
            verifyEachFourTimes(indexHeader, verifyValue);
            indexHeader += 4;
        }
    }

    private void verifyEachFourTimes(int indexHeader, String verifyValue) {
        for (int i = indexHeader; i < indexHeader + 4; i++){
            write(i, verifyValue);
            boolean compareResult = readAndCompare(i, verifyValue);
            if(!compareResult){
                System.out.println("불일치");
            }
        }
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
