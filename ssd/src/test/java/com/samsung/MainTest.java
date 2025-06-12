package com.samsung;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MainTest {

    @Mock
    FileManager mockFileManager;

    Main main;

    @BeforeEach
    void setUp() {
        main = new Main();
    }

    @Test
    @DisplayName("parsing 함수 테스트 - 쓰기")
    void testParsing() {
        String[] args = {"W", "3", "0x00000001"};
        main.parsing(args);

        assertEquals("W", main.command);
        assertEquals(3, main.lba);
        assertEquals("0x00000001", main.value);
    }

    @Test
    @DisplayName("parsing 함수 테스트2 - 읽기")
    void testParsing2() {
        String[] args = {"R", "3"};
        main.parsing(args);

        assertEquals("R", main.command);
        assertEquals(3, main.lba);
        assertEquals(null, main.value);
    }

    @Test
    @DisplayName("command가 W, R이 아닌 경우 command ERROR 할당 테스트")
    void testParsingWithInvalidCommand() {
        String[] args = {"X", "5", "0x99999999"};
        main.parsing(args);

        assertEquals("ERROR", main.command);
        assertEquals("ERROR", main.value);
    }

    @Test
    @DisplayName("lbaLocation이 int가 아니면 command ERROR 할당 테스트")
    void testParsingWithInvalidLbaLocation() {
        String[] args = {"W", "notInt", "0x12345678"};
        main.parsing(args);
        assertEquals("ERROR", main.command);
        assertEquals("ERROR", main.value);
    }

    @Test
    @DisplayName("Command에 잘못된 개수의 인수 쓰기")
    void testTwoParameters() {
        main.parsing(new String[]{"W", "0"});
        assertEquals("ERROR", main.command);
        assertEquals("ERROR", main.value);
    }

    @Test
    @DisplayName("Command에 잘못된 개수의 인수 읽기")
    void testThreeParameters() {
        main.parsing(new String[]{"R", "0", "0x00000000", "0"});
        assertEquals("ERROR", main.command);
        assertEquals("ERROR", main.value);
    }

    @Test
    @DisplayName("Command에 음수 인수 입력")
    void negativeParameter() {
        main.parsing(new String[]{"W", "-1", "0x00000000"});
        assertEquals("ERROR", main.command);
        assertEquals("ERROR", main.value);
    }

    @Test
    @DisplayName("Command에 잘못된 value 쓰기")
    void valueErrorTest() {
        main.parsing(new String[]{"W", "0", "0x0000000011"});
        assertEquals("ERROR", main.command);
        assertEquals("ERROR", main.value);
    }

    @Test
    @DisplayName("parsing 함수 테스트 - 쓰기")
    void testParsing16() {
        String[] args = {"W", "3", "0xG(GGGGG,"};
        main.parsing(args);

        assertEquals("ERROR", main.command);
        assertEquals("ERROR", main.value);
    }

    @Test
    void testW_0_0x00000000() {
        Main.parsing(new String[]{"W", "0", "0x00000000"});
        assertEquals("W", Main.command);
        assertEquals(0, Main.lba);
        assertEquals("0x00000000", Main.value);
    }

    @Test
    void testW_99_0xFFFFFFFF() {
        Main.parsing(new String[]{"W", "99", "0xFFFFFFFF"});
        assertEquals("W", Main.command);
        assertEquals(99, Main.lba);
        assertEquals("0xFFFFFFFF", Main.value);
    }

    @Test
    void testR_50_0x1234ABCD() {
        Main.parsing(new String[]{"R", "50", "0x1234ABCD"});
        assertEquals("ERROR", Main.command);
        assertEquals("ERROR", Main.value);
    }

    @Test
    void testR_1_0xA0B0C0D0() {
        Main.parsing(new String[]{"R", "1", "0xA0B0C0D0"});
        assertEquals("ERROR", Main.command);
        assertEquals("ERROR", Main.value);
    }

    @Test
    void testW_10_0x00FF00FF() {
        Main.parsing(new String[]{"W", "10", "0x00FF00FF"});
        assertEquals("W", Main.command);
        assertEquals(10, Main.lba);
        assertEquals("0x00FF00FF", Main.value);
    }

    @Test
    void testNoSpaceBetweenWorkAndLba() {
        Main.parsing(new String[]{"W10", "0x12345678"});
        assertEquals("ERROR", Main.command);
    }

    @Test
    void testValueMissing() {
        Main.parsing(new String[]{"W", "10"});
        assertEquals("ERROR", Main.command);
    }

    @Test
    void testWorkMissing() {
        Main.parsing(new String[]{"10", "0x12345678"});
        assertEquals("ERROR", Main.command);
    }

    @Test
    void testLbaAndValueMissing() {
        Main.parsing(new String[]{"W"});
        assertEquals("ERROR", Main.command);
    }

    @Test
    @DisplayName("Erase 성공")
    void testEraseInput() {
        Main.parsing(new String[]{"E", "5", "9"});
        assertEquals("E", Main.command);
    }

    @Test
    @DisplayName("Erase 실패 - value")
    void testEraseInput_fail_value1() {
        Main.parsing(new String[]{"E", "5", "11"});
        assertEquals("ERROR", Main.command);
    }

    @Test
    @DisplayName("Erase 실패 - value")
    void testEraseInput_fail_value2() {
        Main.parsing(new String[]{"E", "5", "-1"});
        assertEquals("ERROR", Main.command);
    }

    @Test@DisplayName("Erase 실패 - value")
    void testEraseInput_fail_value3() {
        Main.parsing(new String[]{"E", "5", "aa"});
        assertEquals("ERROR", Main.command);
    }

    @Test
    @DisplayName("Erase 실패 - value & lba")
    void testEraseInput_fail_lba3() {
        Main.parsing(new String[]{"E", "100", "aa"});
        assertEquals("ERROR", Main.command);
    }

    @Test
    @DisplayName("Erase 실패 - paramQty")
    void testEraseInput_fail_paramQty() {
        Main.parsing(new String[]{"E"});
        assertEquals("ERROR", Main.command);
    }

    @Test
    @DisplayName("E 명령 - 최소값(0 위치부터 1칸 지우기)")
    void testEraseMinValue() {
        Main.parsing(new String[]{"E", "0", "1"});
        assertEquals("E", Main.command);
        assertEquals(0, Main.lba);
        assertEquals("1", Main.value);
    }

    @Test
    @DisplayName("E 명령 - 50 위치부터 5칸 지우기")
    void testEraseMiddleValue() {
        Main.parsing(new String[]{"E", "50", "5"});
        assertEquals("E", Main.command);
        assertEquals(50, Main.lba);
        assertEquals("5", Main.value);
    }

    @Test
    @DisplayName("E 명령 - 최대 lba(99 위치부터 1칸 지우기)")
    void testEraseMaxLba() {
        Main.parsing(new String[]{"E", "99", "1"});
        assertEquals("E", Main.command);
        assertEquals(99, Main.lba);
        assertEquals("1", Main.value);
    }

    @Test
    @DisplayName("E 명령 - 10 위치부터 0칸 지우기(오류 아님)")
    void testEraseZeroCount() {
        Main.parsing(new String[]{"E", "10", "0"});
        assertEquals("E", Main.command);
        assertEquals(10, Main.lba);
        assertEquals("0", Main.value);
    }

    @Test
    @DisplayName("E 명령 - 0 위치부터 10칸 지우기(최대 size)")
    void testEraseMaxCount() {
        Main.parsing(new String[]{"E", "0", "10"});
        assertEquals("E", Main.command);
        assertEquals(0, Main.lba);
        assertEquals("10", Main.value);
    }

    @Test
    @DisplayName("E 명령 - 90 위치부터 9칸 지우기")
    void testEraseNearEnd() {
        Main.parsing(new String[]{"E", "90", "9"});
        assertEquals("E", Main.command);
        assertEquals(90, Main.lba);
        assertEquals("9", Main.value);
    }

    @Test
    @DisplayName("E 명령 - 음수 LBA 입력 오류")
    void testEraseNegativeLba() {
        Main.parsing(new String[]{"E", "-1", "5"});
        assertEquals("ERROR", Main.command);
        assertEquals("ERROR", Main.value);
    }

    @Test
    @DisplayName("E 명령 - LBA 99 초과 입력 오류")
    void testEraseLbaOver99() {
        Main.parsing(new String[]{"E", "100", "1"});
        assertEquals("ERROR", Main.command);
        assertEquals("ERROR", Main.value);
    }

    @Test
    @DisplayName("E 명령 - LBA 소수점 입력 오류")
    void testEraseLbaWithDecimal() {
        Main.parsing(new String[]{"E", "10.5", "2"});
        assertEquals("ERROR", Main.command);
        assertEquals("ERROR", Main.value);
    }

    @Test
    @DisplayName("E 명령 - LBA 문자 입력 오류")
    void testEraseLbaWithAlphabet() {
        Main.parsing(new String[]{"E", "abc", "2"});
        assertEquals("ERROR", Main.command);
        assertEquals("ERROR", Main.value);
    }

    @Test
    @DisplayName("E 명령 - LBA 특수문자 입력 오류")
    void testEraseLbaWithSpecialChar() {
        Main.parsing(new String[]{"E", "1-2", "2"});
        assertEquals("ERROR", Main.command);
        assertEquals("ERROR", Main.value);
    }

    @Test
    @DisplayName("E 명령 - value 음수 입력 오류")
    void testEraseNegativeValue() {
        Main.parsing(new String[]{"E", "1", "-1"});
        assertEquals("ERROR", Main.command);
        assertEquals("ERROR", Main.value);
    }

    @Test
    @DisplayName("E 명령 - value 10 초과 입력 오류")
    void testEraseValueOver10() {
        Main.parsing(new String[]{"E", "1", "11"});
        assertEquals("ERROR", Main.command);
        assertEquals("ERROR", Main.value);
    }

    @Test
    @DisplayName("E 명령 - value 소수점 입력 오류")
    void testEraseValueWithDecimal() {
        Main.parsing(new String[]{"E", "1", "1.5"});
        assertEquals("ERROR", Main.command);
        assertEquals("ERROR", Main.value);
    }

    @Test
    @DisplayName("E 명령 - value 문자 입력 오류")
    void testEraseValueWithAlphabet() {
        Main.parsing(new String[]{"E", "1", "abc"});
        assertEquals("ERROR", Main.command);
        assertEquals("ERROR", Main.value);
    }

    @Test @DisplayName("E110 - 공백 없음")
    void testNoSpace() {
        Main.parsing(new String[]{"E110"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E 1   10 - 공백이 두 칸 이상")
    void testMultipleSpaces() {
        Main.parsing(new String[]{"E", "1", "10"});
        assertEquals("E", Main.command);
        assertEquals(1, Main.lba);
        assertEquals("10", Main.value);
    }

    @Test @DisplayName("E 1 - 값 부족")
    void testMissingValue() {
        Main.parsing(new String[]{"E", "1"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E 1 10 5 - 너무 많은 값")
    void testTooManyValues() {
        Main.parsing(new String[]{"E", "1", "10", "5"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E 95 5 - lba+size=100 오류")
    void testLbaPlusSizeEquals100() {
        Main.parsing(new String[]{"E", "95", "5"});
        assertEquals("E", Main.command);
    }

    @Test @DisplayName("E 99 2 - lba+size=101 오류")
    void testLbaPlusSizeOver99() {
        Main.parsing(new String[]{"E", "99", "2"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E @ 1 - lba에 특수문자 포함")
    void testLbaWithSpecialChar() {
        Main.parsing(new String[]{"E", "@", "1"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E 5 # - size에 특수문자 포함")
    void testSizeWithSpecialChar() {
        Main.parsing(new String[]{"E", "5", "#"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E $ % - lba, size 모두 특수문자 포함")
    void testLbaAndSizeWithSpecialChar() {
        Main.parsing(new String[]{"E", "$", "%"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E ! 10 - lba에 느낌표 포함")
    void testLbaWithExclamation() {
        Main.parsing(new String[]{"E", "!", "10"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E 3 & - size에 앰퍼샌드 포함")
    void testSizeWithAmpersand() {
        Main.parsing(new String[]{"E", "3", "&"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E * 1 - lba에 별표 포함")
    void testLbaWithAsterisk() {
        Main.parsing(new String[]{"E", "*", "1"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E 1 10# - size 끝에 특수문자 포함")
    void testSizeEndsWithSpecialChar() {
        Main.parsing(new String[]{"E", "1", "10#"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E 1@ 10 - lba 숫자와 특수문자 붙음")
    void testLbaNumberWithSpecialChar() {
        Main.parsing(new String[]{"E", "1@", "10"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E 1 1\\n - size 뒤에 개행문자 포함")
    void testSizeWithNewline() {
        Main.parsing(new String[]{"E", "1", "1\n"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E - command만 있고 lba, size 없음")
    void testOnlyCommand() {
        Main.parsing(new String[]{"E"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E 1 - command, lba만 있고 size 없음")
    void testCommandAndLbaOnly() {
        Main.parsing(new String[]{"E", "1"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E 1 2 3 - 파라미터 4개")
    void testFourParameters() {
        Main.parsing(new String[]{"E", "1", "2", "3"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("빈 문자열 - 아무 입력도 없는 경우")
    void testEmptyInput() {
        Main.parsing(new String[]{});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E 1 2 3 4 - 파라미터 5개 이상")
    void testFiveParameters() {
        Main.parsing(new String[]{"E", "1", "2", "3", "4"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName(" E 1 2 - 앞에 공백 포함")
    void testLeadingSpace() {
        Main.parsing(new String[]{" E", "1", "2"});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("E 1 2 - 끝에 공백 포함")
    void testTrailingSpace() {
        Main.parsing(new String[]{"E", "1", "2 "});
        assertEquals("ERROR", Main.command);
    }

    @Test @DisplayName("     E 1 2 - 앞에 여러 공백 포함")
    void testMultipleLeadingSpaces() {
        Main.parsing(new String[]{"     E", "1", "2"});
        assertEquals("ERROR", Main.command);
    }

}

