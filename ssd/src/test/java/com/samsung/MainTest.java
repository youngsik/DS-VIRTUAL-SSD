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
}
