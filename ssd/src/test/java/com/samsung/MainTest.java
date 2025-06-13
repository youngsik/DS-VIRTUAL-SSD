package com.samsung;

import com.samsung.file.FileManager;
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
        CmdData cmdData = main.cmdValidCheck(args);

        assertEquals("W", cmdData.getCommand());
        assertEquals(3, cmdData.getLba());
        assertEquals("0x00000001", cmdData.getValue());
    }

    @Test
    @DisplayName("parsing 함수 테스트2 - 읽기")
    void testParsing2() {
        String[] args = {"R", "3"};
        CmdData cmdData = Main.cmdValidCheck(args);

        assertEquals("R", cmdData.getCommand());
        assertEquals(3, cmdData.getLba());
        assertEquals(null, cmdData.getValue());
    }

    @Test
    @DisplayName("command가 W, R이 아닌 경우 command ERROR 할당 테스트")
    void testParsingWithInvalidCommand() {
        String[] args = {"X", "5", "0x99999999"};
        CmdData cmdData = main.cmdValidCheck(args);

        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    @DisplayName("lbaLocation이 int가 아니면 command ERROR 할당 테스트")
    void testParsingWithInvalidLbaLocation() {
        String[] args = {"W", "notInt", "0x12345678"};
        CmdData cmdData = main.cmdValidCheck(args);
        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    @DisplayName("Command에 잘못된 개수의 인수 쓰기")
    void testTwoParameters() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"W", "0"});
        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    @DisplayName("Command에 잘못된 개수의 인수 읽기")
    void testThreeParameters() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"R", "0", "0x00000000", "0"});
        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    @DisplayName("Command에 음수 인수 입력")
    void negativeParameter() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"W", "-1", "0x00000000"});
        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    @DisplayName("Command에 잘못된 value 쓰기")
    void valueErrorTest() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"W", "0", "0x0000000011"});
        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    @DisplayName("parsing 함수 테스트 - 쓰기")
    void testParsing16() {
        String[] args = {"W", "3", "0xG(GGGGG,"};
        CmdData cmdData = main.cmdValidCheck(args);

        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    void testW_0_0x00000000() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"W", "0", "0x00000000"});
        assertEquals("W", cmdData.getCommand());
        assertEquals(0, cmdData.getLba());
        assertEquals("0x00000000", cmdData.getValue());
    }

    @Test
    void testW_99_0xFFFFFFFF() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"W", "99", "0xFFFFFFFF"});
        assertEquals("W", cmdData.getCommand());
        assertEquals(99, cmdData.getLba());
        assertEquals("0xFFFFFFFF", cmdData.getValue());
    }

    @Test
    void testR_50_0x1234ABCD() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"R", "50", "0x1234ABCD"});
        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    void testR_1_0xA0B0C0D0() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"R", "1", "0xA0B0C0D0"});
        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    void testW_10_0x00FF00FF() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"W", "10", "0x00FF00FF"});
        assertEquals("W", cmdData.getCommand());
        assertEquals(10, cmdData.getLba());
        assertEquals("0x00FF00FF", cmdData.getValue());
    }

    @Test
    void testNoSpaceBetweenWorkAndLba() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"W10", "0x12345678"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test
    void testValueMissing() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"W", "10"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test
    void testWorkMissing() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"10", "0x12345678"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test
    void testLbaAndValueMissing() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"W"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test
    @DisplayName("Erase 성공")
    void testEraseInput() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "5", "9"});
        assertEquals("E", cmdData.getCommand());
    }

    @Test
    @DisplayName("Erase 실패 - value")
    void testEraseInput_fail_value1() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "5", "11"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test
    @DisplayName("Erase 실패 - value")
    void testEraseInput_fail_value2() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "5", "-1"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test@DisplayName("Erase 실패 - value")
    void testEraseInput_fail_value3() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "5", "aa"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test
    @DisplayName("Erase 실패 - value & lba")
    void testEraseInput_fail_lba3() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "100", "aa"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test
    @DisplayName("Erase 실패 - paramQty")
    void testEraseInput_fail_paramQty() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test
    @DisplayName("E 명령 - 최소값(0 위치부터 1칸 지우기)")
    void testEraseMinValue() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "0", "1"});
        assertEquals("E", cmdData.getCommand());
        assertEquals(0, cmdData.getLba());
        assertEquals("1", cmdData.getValue());
    }

    @Test
    @DisplayName("E 명령 - 50 위치부터 5칸 지우기")
    void testEraseMiddleValue() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "50", "5"});
        assertEquals("E", cmdData.getCommand());
        assertEquals(50, cmdData.getLba());
        assertEquals("5", cmdData.getValue());
    }

    @Test
    @DisplayName("E 명령 - 최대 lba(99 위치부터 1칸 지우기)")
    void testEraseMaxLba() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "99", "1"});
        assertEquals("E", cmdData.getCommand());
        assertEquals(99, cmdData.getLba());
        assertEquals("1", cmdData.getValue());
    }

    @Test
    @DisplayName("E 명령 - 10 위치부터 0칸 지우기(오류 아님)")
    void testEraseZeroCount() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "10", "0"});
        assertEquals("E", cmdData.getCommand());
        assertEquals(10, cmdData.getLba());
        assertEquals("0", cmdData.getValue());
    }

    @Test
    @DisplayName("E 명령 - 0 위치부터 10칸 지우기(최대 size)")
    void testEraseMaxCount() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "0", "10"});
        assertEquals("E", cmdData.getCommand());
        assertEquals(0, cmdData.getLba());
        assertEquals("10", cmdData.getValue());
    }

    @Test
    @DisplayName("E 명령 - 90 위치부터 9칸 지우기")
    void testEraseNearEnd() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "90", "9"});
        assertEquals("E", cmdData.getCommand());
        assertEquals(90, cmdData.getLba());
        assertEquals("9", cmdData.getValue());
    }

    @Test
    @DisplayName("E 명령 - 음수 LBA 입력 오류")
    void testEraseNegativeLba() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "-1", "5"});
        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    @DisplayName("E 명령 - LBA 99 초과 입력 오류")
    void testEraseLbaOver99() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "100", "1"});
        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    @DisplayName("E 명령 - LBA 소수점 입력 오류")
    void testEraseLbaWithDecimal() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "10.5", "2"});
        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    @DisplayName("E 명령 - LBA 문자 입력 오류")
    void testEraseLbaWithAlphabet() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "abc", "2"});
        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    @DisplayName("E 명령 - LBA 특수문자 입력 오류")
    void testEraseLbaWithSpecialChar() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "1-2", "2"});
        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    @DisplayName("E 명령 - value 음수 입력 오류")
    void testEraseNegativeValue() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "1", "-1"});
        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    @DisplayName("E 명령 - value 10 초과 입력 오류")
    void testEraseValueOver10() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "1", "11"});
        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    @DisplayName("E 명령 - value 소수점 입력 오류")
    void testEraseValueWithDecimal() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "1", "1.5"});
        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test
    @DisplayName("E 명령 - value 문자 입력 오류")
    void testEraseValueWithAlphabet() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "1", "abc"});
        assertEquals("ERROR", cmdData.getCommand());
        assertEquals("ERROR", cmdData.getValue());
    }

    @Test @DisplayName("E110 - 공백 없음")
    void testNoSpace() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E110"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E 1   10 - 공백이 두 칸 이상")
    void testMultipleSpaces() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "1", "10"});
        assertEquals("E", cmdData.getCommand());
        assertEquals(1, cmdData.getLba());
        assertEquals("10", cmdData.getValue());
    }

    @Test @DisplayName("E 1 - 값 부족")
    void testMissingValue() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "1"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E 1 10 5 - 너무 많은 값")
    void testTooManyValues() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "1", "10", "5"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E 95 5 - lba+size=100 오류")
    void testLbaPlusSizeEquals100() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "95", "5"});
        assertEquals("E", cmdData.getCommand());
    }

    @Test @DisplayName("E 99 2 - lba+size=101 오류")
    void testLbaPlusSizeOver99() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "99", "2"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E @ 1 - lba에 특수문자 포함")
    void testLbaWithSpecialChar() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "@", "1"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E 5 # - size에 특수문자 포함")
    void testSizeWithSpecialChar() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "5", "#"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E $ % - lba, size 모두 특수문자 포함")
    void testLbaAndSizeWithSpecialChar() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "$", "%"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E ! 10 - lba에 느낌표 포함")
    void testLbaWithExclamation() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "!", "10"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E 3 & - size에 앰퍼샌드 포함")
    void testSizeWithAmpersand() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "3", "&"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E * 1 - lba에 별표 포함")
    void testLbaWithAsterisk() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "*", "1"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E 1 10# - size 끝에 특수문자 포함")
    void testSizeEndsWithSpecialChar() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "1", "10#"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E 1@ 10 - lba 숫자와 특수문자 붙음")
    void testLbaNumberWithSpecialChar() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "1@", "10"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E 1 1\\n - size 뒤에 개행문자 포함")
    void testSizeWithNewline() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "1", "1\n"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E - command만 있고 lba, size 없음")
    void testOnlyCommand() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E 1 - command, lba만 있고 size 없음")
    void testCommandAndLbaOnly() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "1"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E 1 2 3 - 파라미터 4개")
    void testFourParameters() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "1", "2", "3"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("빈 문자열 - 아무 입력도 없는 경우")
    void testEmptyInput() {
        CmdData cmdData = main.cmdValidCheck(new String[]{});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E 1 2 3 4 - 파라미터 5개 이상")
    void testFiveParameters() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "1", "2", "3", "4"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName(" E 1 2 - 앞에 공백 포함")
    void testLeadingSpace() {
        CmdData cmdData = main.cmdValidCheck(new String[]{" E", "1", "2"});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("E 1 2 - 끝에 공백 포함")
    void testTrailingSpace() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"E", "1", "2 "});
        assertEquals("ERROR", cmdData.getCommand());
    }

    @Test @DisplayName("     E 1 2 - 앞에 여러 공백 포함")
    void testMultipleLeadingSpaces() {
        CmdData cmdData = main.cmdValidCheck(new String[]{"     E", "1", "2"});
        assertEquals("ERROR", cmdData.getCommand());
    }

}

