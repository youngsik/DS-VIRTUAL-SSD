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
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestShellManagerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    SsdApplication mockSsdApplication;

    @Mock
    FileManager fileManager;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    @DisplayName("testSheel 읽기 실행")
    void testShellReadExecute() {

        int index = 3;
        String inputCommand ="R " + index;

        TestShellManager testShellManager= new TestShellManager(mockSsdApplication, fileManager);
        testShellManager.read(index);

        verify(mockSsdApplication, times(1)).execute(inputCommand);
        verify(fileManager, times(1)).getValue(index);
    }

    @Test
    @DisplayName("testSheel 읽기 출력")
    void testShellReadValue() {

        int index = 3;
        String inputCommand ="R " + index;
        String expected = "[Read] LBA 03 0xFFFFFFFF";

        TestShellManager testShellManager= new TestShellManager(mockSsdApplication, fileManager);
        when(fileManager.getValue(index)).thenReturn("0xFFFFFFFF");
        testShellManager.read(index);

        assertThat(outContent.toString().trim())
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("testSheel 쓰기 실행")
    void testShellWriteExecute() {

        int index = 3;
        String value = "0xFFFFFFFF";

        String inputCommand ="W" + " " + index + " " + value;

        TestShellManager testShellManager= new TestShellManager(mockSsdApplication, fileManager);testShellManager.write(index,value);

        verify(mockSsdApplication, times(1)).execute(inputCommand);
    }

    @Test
    @DisplayName("testSheel 쓰기 출력")
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
    @DisplayName("testSheel 전체쓰기 실행")
    void testShellFullWriteExecute() {
        String value = "0xFFFFFFFF";

        TestShellManager testShellManager= new TestShellManager(mockSsdApplication, fileManager);

        testShellManager.fullwrite(value);
        verify(mockSsdApplication, times(100)).execute(anyString());
    }

    @Test
    @DisplayName("testSheel 전체쓰기 출력")
    void testShellFullWrite() {
        String expected = "[Full Write] Done";
        String value = "0xFFFFFFFF";

        TestShellManager testShellManager= new TestShellManager(mockSsdApplication, fileManager);

        testShellManager.fullwrite(value);
        assertThat(outContent.toString().trim())
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("testSheel 전체읽기 실행")
    void testShellFullReadExecute() {
        String value = "0xFFFFFFFF";
        List<String> listvalues = Arrays.asList("1 0xFFFFFFFF", "2 0xFFFFFFFE");

        TestShellManager testShellManager= new TestShellManager(mockSsdApplication, fileManager);

        File file = fileManager.getOrCreateFile(anyString());
        when(fileManager.getDataFromNandFile(file)).thenReturn(listvalues);

        testShellManager.fullread();
        String expectedOutput = String.join("\n", listvalues).trim();
        String actualOutput = outContent.toString().trim();

        String normalizedExpected = Arrays.stream(expectedOutput.split("\n"))
                .map(String::trim)
                .collect(Collectors.joining("\n"));

        String normalizedActual = Arrays.stream(actualOutput.split("\n"))
                .map(String::trim)
                .collect(Collectors.joining("\n"));

        assertThat(normalizedActual).isEqualTo(normalizedExpected);
    }

    @Test
    @DisplayName("testSheel help")
    void testShellFullHelp() {

        List<String> listvalues = Arrays.asList("DeviceSolution", "김영식, 박준경, 권희정, 권성민, 이상훈, 오시훈, 추준성","사용법","write 3 0xAAAABBBB" );

        TestShellManager testShellManager= new TestShellManager(mockSsdApplication, fileManager);

        testShellManager.help();

        String actualOutput = outContent.toString().trim();

        String expectedOutput = String.join("\n", listvalues).trim();
        String normalizedExpected = Arrays.stream(expectedOutput.split("\n"))
                .map(String::trim)
                .collect(Collectors.joining("\n"));

        String normalizedActual = Arrays.stream(actualOutput.split("\n"))
                .map(String::trim)
                .collect(Collectors.joining("\n"));

        assertThat(normalizedActual).isEqualTo(normalizedExpected);

    }



}