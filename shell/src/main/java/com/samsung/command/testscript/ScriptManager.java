package com.samsung.command.testscript;

import com.samsung.FileManager;
import com.samsung.file.JarExecutor;

import java.util.Random;

import static com.samsung.command.testscript.TestScriptConstant.*;

public class ScriptManager {
    private final FileManager fileManager;
    private final JarExecutor jarExecutor;
    private final Random random = new Random();

    public ScriptManager(FileManager fileManager, JarExecutor jarExecutor) {
        this.fileManager = fileManager;
        this.jarExecutor = jarExecutor;
    }

    public boolean testScript1() {
        int lastVerifyBlock = LOOP_100 - VERIFY_TERM;
        for (int index = LBA_FIRST; index <= lastVerifyBlock; index += VERIFY_TERM) {
            if(!isVerifyBlock(index, TEST_VALUE)) return false;
        }
        return true;
    }

    public boolean testScript2() {
        for (int i = 0; i < LOOP_30; i++) {
            script2LbaOrder.forEach(lba -> write(lba, TEST_VALUE));
            for (int j = 0; j <= VERIFY_TERM; j++) {
                if(!isVerifyValue(j, TEST_VALUE)) return false;
            }
        }
        return true;
    }

    public boolean testScript3() {
        for (int i = 0; i < LOOP_100 * 2; i++) {
            boolean firstValue = writeAndVerify(LBA_FIRST, getRandomHex());
            boolean lastValue = writeAndVerify(LBA_LAST, getRandomHex());
            if (!isValidFirstLastValue(firstValue, lastValue)) return false;
        }
        return true;
    }

    private boolean isValidFirstLastValue(boolean firstOk, boolean lastOk) {
        return firstOk && lastOk;
    }

    public boolean testScript4() {
        jarExecutor.executeErase(0, ERASE_BLOCK_LENGTH);

        int currentLba = 2;
        for (int i = 0; i < LOOP_30; i++) {
            testScript4Logic(currentLba);

            for (int offset = 0; offset < ERASE_BLOCK_LENGTH; offset++) {
                if (!isVerifyValue(currentLba++, EMPTY_VALUE)) return false;
            }
        }
        return true;
    }

    private void testScript4Logic(int currentLba) {
        jarExecutor.executeWrite(currentLba, TEST_VALUE);
        jarExecutor.executeWrite(currentLba, TEST_VALUE_OVERWRITE);
        jarExecutor.executeErase(currentLba, ERASE_BLOCK_LENGTH);
    }

    private boolean isVerifyBlock(int startLba, String value) {
        boolean success = false;
        for (int i = 0; i < VERIFY_TERM; i++) {
            int lba = startLba + i;
            write(lba, value);
            success = isVerifyValue(lba, value);
        }
        return success;
    }

    private boolean writeAndVerify(int lba, String value) {
        write(lba, value);
        return isVerifyValue(lba, value);
    }

    private boolean isVerifyValue(int lba, String expected) {
        String actual = fileManager.getValueFromFile(lba);
        return expected.equals(actual);
    }

    private void write(int lba, String value) {
        jarExecutor.executeWrite(lba, value);
    }

    private String getRandomHex() {
        int randomInt = random.nextInt(MAX_RAND_BOUND);
        return String.format("0x%08X", randomInt);
    }
}
