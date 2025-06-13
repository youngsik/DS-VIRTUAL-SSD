package com.samsung.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandValidatorTest {

    private static final String[] ONE_CMD_ARG = new String[1];
    private static final String[] TWO_CMD_ARG = new String[2];
    private static final String[] THREE_CMD_ARG = new String[3];

    @DisplayName("명령어 인자 개수 1개 검증 PASS 테스트")
    @Test
    void validateOneArgs_PassTest() {
        assertDoesNotThrow(() -> CommandValidator.validateOneArgs(ONE_CMD_ARG));
    }

    @DisplayName("명령어 인자 개수 1개 검증 FAIL 테스트")
    @Test
    void validateOneArgs_FailTest() {
        assertThrows(RuntimeException.class, () -> CommandValidator.validateOneArgs(TWO_CMD_ARG));
    }

    @DisplayName("명령어 인자 개수 2개 검증 PASS 테스트")
    @Test
    void validateTwoArgs_PassTest() {
        assertDoesNotThrow(() -> CommandValidator.validateTwoArgs(TWO_CMD_ARG));
    }

    @DisplayName("명령어 인자 개수 2개 검증 FAIL 테스트")
    @Test
    void validateTwoArgs_FailTest() {
        assertThrows(RuntimeException.class, () -> CommandValidator.validateTwoArgs(ONE_CMD_ARG));
    }

    @DisplayName("명령어 인자 개수 3개 검증 PASS 테스트")
    @Test
    void validateThreeArgs_PassTest() {
        assertDoesNotThrow(() -> CommandValidator.validateThreeArgs(THREE_CMD_ARG));
    }

    @DisplayName("명령어 인자 개수 3개 검증 FAIL 테스트")
    @Test
    void validateThreeArgs_FailTest() {
        assertThrows(RuntimeException.class, () -> CommandValidator.validateThreeArgs(ONE_CMD_ARG));
    }
}