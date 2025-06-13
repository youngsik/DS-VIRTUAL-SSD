package com.samsung.command.support;

import com.samsung.validator.ArgumentCountValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentCountValidatorTest {

    private static final String[] ONE_CMD_ARG = new String[1];
    private static final String[] TWO_CMD_ARG = new String[2];
    private static final String[] THREE_CMD_ARG = new String[3];

    @DisplayName("명령어 인자 개수 1개 검증 PASS 테스트")
    @Test
    void validateOneArgs_PassTest() {
        assertDoesNotThrow(() -> ArgumentCountValidator.validateOneArgs(ONE_CMD_ARG));
    }

    @DisplayName("명령어 인자 개수 1개 검증 FAIL 테스트")
    @Test
    void validateOneArgs_FailTest() {
        assertThrows(RuntimeException.class, () -> ArgumentCountValidator.validateOneArgs(TWO_CMD_ARG));
    }

    @DisplayName("명령어 인자 개수 2개 검증 PASS 테스트")
    @Test
    void validateTwoArgs_PassTest() {
        assertDoesNotThrow(() -> ArgumentCountValidator.validateTwoArgs(TWO_CMD_ARG));
    }

    @DisplayName("명령어 인자 개수 2개 검증 FAIL 테스트")
    @Test
    void validateTwoArgs_FailTest() {
        assertThrows(RuntimeException.class, () -> ArgumentCountValidator.validateTwoArgs(ONE_CMD_ARG));
    }

    @DisplayName("명령어 인자 개수 3개 검증 PASS 테스트")
    @Test
    void validateThreeArgs_PassTest() {
        assertDoesNotThrow(() -> ArgumentCountValidator.validateThreeArgs(THREE_CMD_ARG));
    }

    @DisplayName("명령어 인자 개수 3개 검증 FAIL 테스트")
    @Test
    void validateThreeArgs_FailTest() {
        assertThrows(RuntimeException.class, () -> ArgumentCountValidator.validateThreeArgs(ONE_CMD_ARG));
    }
}