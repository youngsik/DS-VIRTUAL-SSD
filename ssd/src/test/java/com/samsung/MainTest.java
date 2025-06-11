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
    void testEmptyInput() {
        Main.parsing(new String[]{});
        assertEquals("ERROR", Main.command);
    }
}
