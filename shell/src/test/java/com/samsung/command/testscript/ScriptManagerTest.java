package com.samsung.command.testscript;

import com.samsung.file.FileManager;
import com.samsung.file.JarExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ScriptManagerTest {
    private static final String EMPTY_VALUE = "0x00000000";

    @Mock
    private FileManager fileManager;

    @Mock
    private JarExecutor jarExecutor;

    @InjectMocks
    private ScriptManager scriptManager;

    private Map<Integer, String> lastWritten;

    @BeforeEach
    void setUp() {
        lastWritten = new HashMap<>();

        when(fileManager.getHashmap()).thenReturn(lastWritten);
        doNothing().when(fileManager).readFile(anyInt());

        stubWrite();
    }

    @Test
    @DisplayName("testScript1 - 기본값 0xAAAABBBB를 100개에 걸쳐 검증")
    void testScript1() {
        boolean result = scriptManager.testScript1();

        assertTrue(result);
        verify(jarExecutor, atLeast(100)).executeWrite(anyInt(), eq("0xAAAABBBB"));
        verify(fileManager, atLeast(100)).readFile(anyInt());
        verify(fileManager, atLeast(100)).getHashmap();
    }

    @Test
    @DisplayName("testScript2 - script2LbaOrder 값들 반복적으로 write 후 검증")
    void testScript2() {
        boolean result = scriptManager.testScript2();

        assertTrue(result);
        for (int i = 0; i <= 4; i++) {
            verify(jarExecutor, atLeastOnce()).executeWrite(eq(i), eq("0xAAAABBBB"));
            verify(fileManager, atLeastOnce()).readFile(eq(i));
        }
        verify(fileManager, atLeast(30 * 5)).getHashmap(); // 최소 150회 호출 예상
    }

    @Test
    @DisplayName("testScript3 - 랜덤 HEX를 LBA 0과 99에 반복 write 후 검증")
    void testScript3() {
        boolean result = scriptManager.testScript3();

        assertTrue(result);
        verify(jarExecutor, times(200)).executeWrite(eq(0), anyString());
        verify(jarExecutor, times(200)).executeWrite(eq(99), anyString());
        verify(fileManager, times(200)).readFile(0);
        verify(fileManager, times(200)).readFile(99);
        verify(fileManager, atLeast(400)).getHashmap();
    }


    @Test
    @DisplayName("testScript4 - write, overwrite 후 erase → EMPTY_VALUE 검증")
    void testScript4() {
        stubErase();

        boolean result = scriptManager.testScript4();

        assertTrue(result);
        verify(jarExecutor, times(1)).executeErase(eq(0), eq(3));
        verify(jarExecutor, times(31)).executeErase(anyInt(), eq(3));
        verify(jarExecutor, times(30 * 2)).executeWrite(anyInt(), anyString());
        verify(fileManager, times(30 * 3)).readFile(anyInt());
        verify(fileManager, atLeast(1)).getHashmap();
    }

    private void stubErase() {
        doAnswer(invocation -> {
            int startLba = invocation.getArgument(0);
            int length = invocation.getArgument(1);
            for (int i = startLba; i < startLba + length; i++) {
                lastWritten.put(i, EMPTY_VALUE);
            }
            return null;
        }).when(jarExecutor).executeErase(anyInt(), anyInt());
    }

    private void stubWrite() {
        doAnswer(invocation -> {
            int lba = invocation.getArgument(0);
            String value = invocation.getArgument(1);
            lastWritten.put(lba, value);
            return null;
        }).when(jarExecutor).executeWrite(anyInt(), anyString());
    }

}
