package com.samsung.command.testshell;

import com.samsung.file.FileManager;
import com.samsung.file.JarExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;

@ExtendWith(MockitoExtension.class)
class TestShellManagerTest {

    public static final int INDEX = 3;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public static final String DUMMY_VALUE = "0xFFFFFFFF";
    TestShellManager testShellManager;

    @Mock
    private JarExecutor jarExecutor;

    @Mock
    FileManager fileManager;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        testShellManager = new TestShellManager(jarExecutor, fileManager);
    }

    @Test
    @DisplayName("testShell 읽기 실행")
    void testShellReadExecute() {
        testShellManager.read(INDEX);

        verify(fileManager, times(1)).getResultFromOutputFile();
    }

    @Test
    @DisplayName("testShell 읽기 출력")
    void testShellReadValue() {
        int index = INDEX;
        String value = "0xFFFFFFFF";
        String expected = "[Read] LBA 03 "+ value;
        when(fileManager.getResultFromOutputFile()).thenReturn(value);

        testShellManager.read(index);

        assertThat(outContent.toString().trim())
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("testShell 쓰기 실행")
    void testShellWriteExecute() {
        int index = INDEX;
        String value = "0xFFFFFFFF";

        testShellManager.write(index, value);

        verify(jarExecutor, times(1)).executeWrite(index, value);
    }

    @Test
    @DisplayName("testShell 쓰기 출력")
    void testShellWriteValue() {
        int index = INDEX;
        String value = "0xFFFFFFFF";
        String expected = "[Write] Done";

        testShellManager.write(index, value);

        assertThat(outContent.toString().trim())
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("testShell 전체쓰기 실행")
    void testShellFullWriteExecute() {
        String value = "0xFFFFFFFF";

        testShellManager.fullwrite(value);

        verify(jarExecutor, times(100)).executeWrite(anyInt(), anyString());
    }

    @Test
    @DisplayName("testShell 전체쓰기 출력")
    void testShellFullWrite() {
        String expected = "[Full Write] Done";
        String value = "0xFFFFFFFF";

        testShellManager.fullwrite(value);

        assertThat(outContent.toString().trim())
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("testShell fullread")
    void testFullreadOutput() {
        List<String> fakeData = new ArrayList<>();
        fakeData.add("0xFFFFFFFF");
        fakeData.add("0x12345678");
        for (int i = 0; i < 100; i++) {;
            fakeData.add("0x00000000");
        }

        when(fileManager.getResultFromOutputFile()).thenReturn(fakeData.get(0))
                .thenReturn(fakeData.get(1))
                .thenReturn(fakeData.get(2));

        testShellManager.fullread();

        verify(fileManager, times(100)).getResultFromOutputFile();

        String[] outputLines = outContent.toString().trim().split("\n");
        assertThat(outputLines).hasSize(100);
        assertThat(outputLines[0].replace("\n", "").replace("\r", "").trim())
                .isEqualTo("[Full Read] LBA 00 0xFFFFFFFF");
        assertThat(outputLines[1].replace("\n", "").replace("\r", "").trim())
                .isEqualTo("[Full Read] LBA 01 0x12345678");
        for (int i = 2; i < 100; i++) {
            String expected = String.format("[Full Read] LBA %02d 0x00000000", i);
            assertThat(outputLines[i].replace("\n", "").replace("\r", "").trim())
                    .isEqualTo(expected);
        }
    }

    @Test
    @DisplayName("testShell help")
    void testShellFullHelp() {
        testShellManager.help();

        String actualOutput = outContent.toString().trim();
        String expectedOutput = String.join("\n",
                "제작자",
                "팀명: DeviceSolution",
                "팀원: 김영식, 박준경, 권희정, 권성민, 이상훈, 오시훈, 추준성",
                "",
                "명령어",
                "  write [LBA] [Value]     지정된 index에 value를 기록합니다. 예: write 3 0xAAAABBBB",
                "  read [LBA]              지정된 index의 값을 읽어옵니다. 예: read 3",
                "  erase [LBA] [Length]    지정된 LBA 부터 Length 길이만큼을 SSD에서 삭제합니다. 예 : erase 0 10",
                "  erase_range [LBA1] [LBA2]    지정된 범위의 데이터를 SSD에서 삭제합니다. 예 : erase_range 10 20",
                "  fullwrite  [Value]         전체 영역에 value를 기록합니다. 예: fullwrite 0xAAAABBBB",
                "  fullread                  전체 영역을 읽어옵니다.",
                "  flush                     SSD 버퍼를 비웁니다. 예 : flush",
                "  help                      사용 가능한 명령어를 출력합니다.",
                "  exit                      프로그램을 종료합니다.",
                "Copyright (c) 2025 DeviceSolution. All rights reserved.",
                ""
        ).trim();

        // 공백문자/라인 간 공백 문제를 방지
        String normalizedExpected = Arrays.stream(expectedOutput.split("\n"))
                .map(String::trim)
                .collect(Collectors.joining("\n"));

        String normalizedActual = Arrays.stream(actualOutput.split("\n"))
                .map(String::trim)
                .collect(Collectors.joining("\n"));

        assertThat(normalizedActual).isEqualTo(normalizedExpected);
    }

    @Test
    @DisplayName("testShell EXIT")
    void testShellFullEXIT() {
        int statusCode=-1;
        try {
            statusCode = catchSystemExit(() -> {
                testShellManager.exit(); // exit() 호출
            });
        } catch (Exception e) {
        }
        assertThat(statusCode).isEqualTo(0);
    }

    @Test
    @DisplayName("testShell Erase SSD 실행 size 미변동 case")
    void testShellEraseSSDExecuteNoChangeSize() {

        int index = 1;
        int size = 5;

        testShellManager.erase(index,size);

        verify(jarExecutor, times(1)).executeErase(index, size);
    }

    @Test
    @DisplayName("testShell Erase SSD 실행 size 변동 case")
    void testShellEraseSSDExecuteChangeSize() {

        int index = 0;
        int size = 15;
        int expaectedIndex = 0;
        int expaectedSize = 10;

        testShellManager.erase(index,size);

        verify(jarExecutor, times(1)).executeErase(expaectedIndex, expaectedSize);
    }


    @Test
    @DisplayName("testShell Erase SSD 실행 Multi수행 case")
    void testShellEraseSSDExecuteMulti() {

        int index = 2;
        int size = 12;
        int expaectedIndex;
        int expaectedSize;

        testShellManager.erase(index,size);

        verify(jarExecutor, times(2)).executeErase(anyInt(), anyInt());

        expaectedIndex=2;
        expaectedSize=10;
        verify(jarExecutor, times(1)).executeErase(expaectedIndex, expaectedSize);

        expaectedIndex=12;
        expaectedSize=2;
        verify(jarExecutor, times(1)).executeErase(expaectedIndex, expaectedSize);
    }

    @Test
    @DisplayName("testShell Erase SSD 실행 -Size case")
    void testShellEraseSSDExecuteMinusSize() {

        int index = 99;
        int size = -30;
        int expaectedIndex ;
        int expaectedSize;

        testShellManager.erase(index,size);

        verify(jarExecutor, times(3)).executeErase(anyInt(), anyInt());

        expaectedIndex=70;
        expaectedSize=10;
        verify(jarExecutor, times(1)).executeErase(expaectedIndex, expaectedSize);

        expaectedIndex=80;
        expaectedSize=10;
        verify(jarExecutor, times(1)).executeErase(expaectedIndex, expaectedSize);

        expaectedIndex=90;
        expaectedSize=10;
        verify(jarExecutor, times(1)).executeErase(expaectedIndex, expaectedSize);
    }

    @Test
    @DisplayName("testShell EraseRange SSD 실행")
    void testShellEraseSSDExecuteRange() {

        int first = 0;
        int end = 9;
        int expaectedIndex ;
        int expaectedSize;

        testShellManager.eraseRange(first,end);

        verify(jarExecutor, times(1)).executeErase(anyInt(), anyInt());

        expaectedIndex=0;
        expaectedSize=10;
        verify(jarExecutor, times(1)).executeErase(expaectedIndex, expaectedSize);
    }

    @Test
    @DisplayName("testShell EraseRange Multi SSD 실행")
    void testShellEraseSSDExecuteRangeMulti() {

        int first = 0;
        int end = 20;
        int expaectedIndex ;
        int expaectedSize;

        testShellManager.eraseRange(first,end);

        verify(jarExecutor, times(3)).executeErase(anyInt(), anyInt());

        expaectedIndex=0;
        expaectedSize=10;
        verify(jarExecutor, times(1)).executeErase(expaectedIndex, expaectedSize);

        expaectedIndex=10;
        expaectedSize=10;
        verify(jarExecutor, times(1)).executeErase(expaectedIndex, expaectedSize);

        expaectedIndex=20;
        expaectedSize=1;
        verify(jarExecutor, times(1)).executeErase(expaectedIndex, expaectedSize);

    }
}