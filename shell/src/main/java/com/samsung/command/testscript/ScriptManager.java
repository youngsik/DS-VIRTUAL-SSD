package com.samsung.command.testscript;

import com.samsung.file.FileManager;
import com.samsung.file.JarExecutor;
import lombok.extern.slf4j.Slf4j;

import static com.samsung.command.testscript.TestScriptConstant.*;

@Slf4j
public class ScriptManager {
    private final FileManager fileManager;
    private final JarExecutor jarExecutor;
    private final RandomHex randomHex;

    public ScriptManager(FileManager fileManager, JarExecutor jarExecutor, RandomHex randomHex) {
        this.fileManager = fileManager;
        this.jarExecutor = jarExecutor;
        this.randomHex = randomHex;
    }

    public boolean testScript1() {
        log.info("[Script1] VERIFY_TERM 단위로 순차 쓰기/검증 시작");
        int lastVerifyBlock = LOOP_100 - VERIFY_TERM;
        for (int index = LBA_FIRST; index <= lastVerifyBlock; index += VERIFY_TERM) {
            if (!isVerifyBlock(index)) return false;
        }
        return true;
    }

    public boolean testScript2() {
        log.info("[Script2] LBA 순서대로 쓰기/검증 반복 시작");
        for (int i = 0; i < LOOP_30; i++) {
            script2LbaOrder.forEach(lba -> jarExecutor.executeWrite(lba, TEST_VALUE));
            boolean isVerified = verifyRange(0, VERIFY_TERM + 1, TEST_VALUE);
            if (!isVerified) return false;
        }
        return true;
    }

    public boolean testScript3() {
        log.info("[Script3] LBA_FIRST & LBA_LAST 무작위 쓰기/검증 시작");
        for (int i = 0; i < LOOP_100 * 2; i++) {
            System.out.println(i);
            boolean firstOk = writeAndVerify(LBA_FIRST, randomHex.getRandomValue());
            boolean lastOk = writeAndVerify(LBA_LAST, randomHex.getRandomValue());
            if (!(firstOk && lastOk)) return false;
        }
        return true;
    }

    public boolean testScript4() {
        log.info("[Script4] Overwrite 후 Erase → 검증 테스트 시작");
        jarExecutor.executeErase(0, ERASE_BLOCK_LENGTH);

        int currentLba = 2;
        for (int i = 0; i < LOOP_30; i++) {
            overwriteThenErase(currentLba);
            boolean isVerified = verifyRange(currentLba, ERASE_BLOCK_LENGTH, EMPTY_VALUE);
            if (!isVerified) return false;
            currentLba += ERASE_BLOCK_LENGTH;
        }
        return true;
    }

    private void overwriteThenErase(int lba) {
        jarExecutor.executeWrite(lba, TEST_VALUE);
        jarExecutor.executeWrite(lba, TEST_VALUE_OVERWRITE);
        jarExecutor.executeErase(lba, ERASE_BLOCK_LENGTH);
    }

    private boolean isVerifyBlock(int startLba) {
        for (int offset = 0; offset < VERIFY_TERM; offset++) {
            int lba = startLba + offset;
            if (!writeAndVerify(lba, TestScriptConstant.TEST_VALUE)) return false;
        }
        return true;
    }

    private boolean writeAndVerify(int lba, String value) {
        jarExecutor.executeWrite(lba, value);
        return isVerifyValue(lba, value);
    }

    private boolean isVerifyValue(int lba, String expected) {
        jarExecutor.executeRead(lba);
        String actual = fileManager.getResultFromOutputFile();
        return expected.equals(actual);
    }

    private boolean verifyRange(int startLba, int length, String expected) {
        for (int offset = 0; offset < length; offset++) {
            int lba = startLba + offset;
            jarExecutor.executeRead(lba);
            String actual = fileManager.getResultFromOutputFile();
            if (!expected.equals(actual)) {
                log.warn("Verification failed at LBA {}: expected={}, actual={}", lba, expected, actual);
                return false;
            }
        }
        return true;
    }
}
