package com.samsung.command.testscript;

import com.samsung.file.FileManager;
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
        boolean result = false;
        for (int index = LBA_FIRST; index <= LOOP_100 - VERIFY_TERM; index += VERIFY_TERM) {
            result = verifyBlock(index, TEST_VALUE);
        }
        return result;
    }

    public boolean testScript2() {
        boolean result = false;
        for (int i = 0; i < LOOP_30; i++) {
            script2LbaOrder.forEach(lba -> write(lba, TEST_VALUE));
            for (int j = 0; j <= VERIFY_TERM; j++) {
                result = verifyValue(j, TEST_VALUE);
            }
        }
        return result;
    }

    public boolean testScript3() {
        boolean result = false;
        for (int i = 0; i < LOOP_100 * 2; i++) {
            String hex1 = getRandomHex();
            String hex2 = getRandomHex();
            boolean firstOk = writeAndVerify(LBA_FIRST, hex1);
            boolean lastOk = writeAndVerify(LBA_LAST, hex2);
            result = firstOk && lastOk;
        }
        return result;
    }

    public boolean testScript4() {
        boolean result = false;
        jarExecutor.executeErase(0, ERASE_BLOCK_LENGTH);

        int currentLba = 2;
        for (int i = 0; i < LOOP_30; i++) {
            testScript4Logic(currentLba);

            for (int offset = 0; offset < ERASE_BLOCK_LENGTH; offset++) {
                result = verifyValue(currentLba++, EMPTY_VALUE);
            }
        }
        return result;
    }

    private void testScript4Logic(int currentLba) {
        jarExecutor.executeWrite(currentLba, TEST_VALUE);
        jarExecutor.executeWrite(currentLba, TEST_VALUE_OVERWRITE);
        jarExecutor.executeErase(currentLba, ERASE_BLOCK_LENGTH);
    }

    private boolean verifyBlock(int startLba, String value) {
        boolean success = false;
        for (int i = 0; i < VERIFY_TERM; i++) {
            int lba = startLba + i;
            write(lba, value);
            success = verifyValue(lba, value);
        }
        return success;
    }

    private boolean writeAndVerify(int lba, String value) {
        write(lba, value);
        return verifyValue(lba, value);
    }

    private boolean verifyValue(int lba, String expected) {
        fileManager.readFile(lba);
        String actual = fileManager.getHashmap().get(lba);
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
