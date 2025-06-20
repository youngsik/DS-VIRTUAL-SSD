package com.samsung.command.support;

import com.samsung.validator.ArgumentValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentValidatorTest {

    public static final String[] NOT_NULL_OBJECT = {};
    public static final Object NULL_OBJECT = null;

    @DisplayName("NULL 체크 PASS 테스트")
    @Test
    void validateNull_PassTest() {
        assertDoesNotThrow(() -> ArgumentValidator.validateNull(NOT_NULL_OBJECT));
    }

    @DisplayName("NULL 체크 FAIL 테스트")
    @Test
    void validateNull_FailTest() {
        assertThrows(RuntimeException.class, () -> ArgumentValidator.validateNull(NULL_OBJECT));
    }

    @DisplayName("Value 포맷 체크 PASS 테스트")
    @ParameterizedTest
    @ValueSource(strings = {
            "0x12345678",
            "0xABCDEF12",
            "0x00000000",
            "0xFFFFFFFF"
    })
    void validateValueFormat_PassTest(String value) {
        assertDoesNotThrow(() -> ArgumentValidator.validateValueFormat(value));
    }

    @DisplayName("Value 포맷 체크 FAIL 테스트")
    @ParameterizedTest(name = "Invalid value format: {0}")
    @ValueSource(strings = {
            "1A2B3C4D",
            "0x1a2b3c4d",
            "0x1A2B3C",
            "0x1A2B3C4D5E",
            "0x1A2B3C4G",
            "0x1234567Z",
            "0x1234",
            "0x!@#$%^&*",
            "0X1A2B3C4D",
            "",
            "   ",
            "0x1234567"
    })
    void validateValueFormat_FailTest(String value) {
        assertThrows(RuntimeException.class, () -> ArgumentValidator.validateValueFormat(value));
    }

    @DisplayName("LBA 범위 체크 PASS 테스트")
    @ParameterizedTest(name = "Valid LBA: {0}")
    @ValueSource(ints = {0, 1, 50, 98, 99})
    void validateLbaRange_PassCases(int lba) {
        assertDoesNotThrow(() -> ArgumentValidator.validateLbaRange(lba));
    }

    @DisplayName("LBA 범위 체크 FAIL 테스트")
    @ParameterizedTest(name = "Invalid LBA: {0}")
    @ValueSource(ints = {-100, -1, 100, 101, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void validateLbaRange_FailCases(int lba) {
        assertThrows(RuntimeException.class, () -> ArgumentValidator.validateLbaRange(lba));
    }
}