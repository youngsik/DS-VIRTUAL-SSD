package com.samsung.testshell;

import com.samsung.SsdApplication;
import com.samsung.file.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;

@ExtendWith(MockitoExtension.class)
class TestShellManagerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public static final String DUMMY_VALUE = "0xFFFFFFFF";

    @Mock
    SsdApplication mockSsdApplication;

    @Mock
    FileManager fileManager;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    @DisplayName("testShell 읽기 실행")
    void testShellReadExecute() {

        int index = 3;
        String inputCommand ="R " + index;

        TestShellManager testShellManager= new TestShellManager(mockSsdApplication, fileManager);
        testShellManager.read(index);

        verify(mockSsdApplication, times(1)).execute(inputCommand);

    }

    @Test
    @DisplayName("testShell 읽기 출력")
    void testShellReadValue() {

        int index = 3;
        String inputCommand ="R " + index;
        String expected = "[Read] LBA 03 0xFFFFFFFF";

        TestShellManager testShellManager= new TestShellManager(mockSsdApplication, fileManager);

        Map<Integer, String> hashMap = new HashMap<>();
        doNothing().when(fileManager).readFile(index);
        hashMap.put(index, DUMMY_VALUE);

        when(fileManager.getHashmap()).thenReturn(hashMap);

        testShellManager.read(index);

        assertThat(outContent.toString().trim())
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("testShell 쓰기 실행")
    void testShellWriteExecute() {

        int index = 3;
        String value = "0xFFFFFFFF";

        String inputCommand ="W" + " " + index + " " + value;

        TestShellManager testShellManager= new TestShellManager(mockSsdApplication, fileManager);testShellManager.write(index,value);

        verify(mockSsdApplication, times(1)).execute(inputCommand);
    }

    @Test
    @DisplayName("testShell 쓰기 출력")
    void testShellWriteValue() {

        int index = 3;
        String value = "0xFFFFFFFF";
        String expected = "[Write] Done";

        String inputCommand ="W" + " " + index + " " + value;

        TestShellManager testShellManager= new TestShellManager(mockSsdApplication, fileManager);
        testShellManager.write(index,value);

        assertThat(outContent.toString().trim())
                .isEqualTo(expected);

    }
    @Test
    @DisplayName("testShell 전체쓰기 실행")
    void testShellFullWriteExecute() {
        String value = "0xFFFFFFFF";

        TestShellManager testShellManager= new TestShellManager(mockSsdApplication, fileManager);

        testShellManager.fullwrite(value);
        verify(mockSsdApplication, times(100)).execute(anyString());
    }

    @Test
    @DisplayName("testShell 전체쓰기 출력")
    void testShellFullWrite() {
        String expected = "[Full Write] Done";
        String value = "0xFFFFFFFF";

        TestShellManager testShellManager= new TestShellManager(mockSsdApplication, fileManager);

        testShellManager.fullwrite(value);
        assertThat(outContent.toString().trim())
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("testShell fullread")
    void testFullreadOutput() {

        Map<Integer, String> fakeData = new HashMap<>();
        fakeData.put(0, "0xFFFFFFFF");
        fakeData.put(1, "0xFFFFFFFE");
        fakeData.put(99, "0x12345678");

        when(fileManager.getHashmap()).thenReturn(fakeData);
        TestShellManager testShellManager= new TestShellManager(mockSsdApplication, fileManager);

        testShellManager.fullread();

        String[] outputLines = outContent.toString().trim().split("\n");

        assertThat(outputLines).hasSize(100);

        assertThat(outputLines[0].replace("\n", "").replace("\r", "").trim())
                .isEqualTo("[Full Read] LBA 00 0xFFFFFFFF");


        for (int i = 2; i < 99; i++) {
            String expected = String.format("[Full Read] LBA %02d 0x00000000", i);
            assertThat(outputLines[i].replace("\n", "").replace("\r", "").trim())
                    .isEqualTo(expected);
        }

        assertThat(outputLines[99].replace("\n", "").replace("\r", "").trim())
                .isEqualTo("[Full Read] LBA 99 0x12345678");

        for (int i = 0; i < 100; i++) {
            verify(fileManager).readFile(i);
        }
    }

    @Test
    @DisplayName("testShell help")
    void testShellFullHelp() {
        // given
        TestShellManager testShellManager = new TestShellManager(mockSsdApplication, fileManager);

        // when
        testShellManager.help();

        // then
        String actualOutput = outContent.toString().trim();

        String expectedOutput = String.join("\n",
                "DeviceSolution",
                "",
                "명령어:",
                "  write <index> <value>     지정된 index에 value를 기록합니다. 예: write 3 0xAAAABBBB",
                "  read <index>              지정된 index의 값을 읽어옵니다. 예: read 3",
                "  fullwrite <value>         전체 영역에 value를 기록합니다. 예: fullwrite 0xAAAABBBB",
                "  fullread                  전체 영역을 읽어옵니다.",
                "  help                      사용 가능한 명령어를 출력합니다.",
                "  exit                      프로그램을 종료합니다.",
                "",
                "개발자: 김영식, 박준경, 권희정, 권성민, 이상훈, 오시훈, 추준성"
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
    void testShellFullEXIT() throws Exception {
        TestShellManager testShellManager= new TestShellManager(mockSsdApplication, fileManager);

        int statusCode = catchSystemExit(() -> {
            testShellManager.exit(); // exit() 호출
        });

        assertThat(statusCode).isEqualTo(0);
    }




}