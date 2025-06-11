package com.samsung.testscript;

import com.samsung.file.FileManager;
import com.samsung.file.JarExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScriptManager {
    private static final String TEST_VALUE = "0xAAAABBBB";

    private static final int TEST_LOOP_30 = 30;
    private static final int TEST_LOOP_100 = 100;
    private static final int VERIFY_TERM = 4;

    private static final int MAX_RAND_BOUND = 1000;
    private static final int LBA_FIRST = 0;
    private static final int LBA_LAST = 99;

    private final List<Integer> script2LbaOrder = new ArrayList<>(List.of(4, 0, 3, 1, 2));

    private final FileManager fileManager;
    private final JarExecutor jarExecutor;

    public ScriptManager(FileManager fileManager, JarExecutor jarExecutor) {
        this.fileManager = fileManager;
        this.jarExecutor = jarExecutor;
    }
    public Random random = new Random();

    public boolean testScript1(){
        int indexHeader = LBA_FIRST;
        boolean isSuccess = false;
        while (indexHeader <= TEST_LOOP_100 - VERIFY_TERM) {
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
        boolean isSuccess = false;
        for (int i = 0; i < TEST_LOOP_30; i++) {
            for (Integer lba : script2LbaOrder) {
                write(lba, TEST_VALUE);
            }

            for (int j = 0; j <= VERIFY_TERM; j++) {
                isSuccess = readAndCompare(j, TEST_VALUE);
            }
        }

        return isSuccess;
    }

    public boolean testScript3(){
        boolean isSuccess = false;
        for (int i = 0; i < TEST_LOOP_100 * 2; i++){
            String randHexForFirst = getRandomHex();
            String randHexForLast = getRandomHex();

            boolean isSuccessLbaFirst = writeAndVerify(LBA_FIRST, randHexForFirst);
            boolean isSuccessLbaLast = writeAndVerify(LBA_LAST, randHexForLast);

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
        fileManager.readFile(lba);
        return fileManager.getHashmap().get(lba);
    }

    private void write(Integer lba, String value){
        jarExecutor.executeWriteJar(lba, value);
    }

    private boolean readAndCompare(Integer lba, String compareValue){
        String readValue = read(lba);
        return compareValue.equals(readValue);
    }
}
