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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScriptManagerTest {
    private static final String EMPTY_VALUE = "0x00000000";
    public static final String VALUE = "0xAAAABBBB";

    @Mock
    private FileManager fileManager;

    @Mock
    private JarExecutor jarExecutor;

    @Mock
    private RandomHex randomHex;

    @InjectMocks
    private ScriptManager scriptManager;

    @BeforeEach
    void setUp() {
        stubWriteJar();
    }

    @Test
    @DisplayName("testScript1 - 기본값 0xAAAABBBB를 100개에 걸쳐 검증")
    void testScript1() {
        doReturn(VALUE).when(fileManager).getValueFromFile(anyInt());
        boolean result = scriptManager.testScript1();

        assertTrue(result);
        verify(jarExecutor, atLeast(100)).executeWrite(anyInt(), eq(VALUE));
    }

    @Test
    @DisplayName("testScript2 - script2LbaOrder 값들 반복적으로 write 후 검증")
    void testScript2() {
        doReturn(VALUE).when(fileManager).getValueFromFile(anyInt());
        boolean result = scriptManager.testScript2();

        assertTrue(result);
        for (int i = 0; i <= 4; i++) {
            verify(jarExecutor, atLeastOnce()).executeWrite(eq(i), eq(VALUE));
        }
        verify(fileManager, atLeast(30 * 5)).getValueFromFile(anyInt()); // 최소 150회 호출 예상
    }

    @Test
    @DisplayName("testScript3 - 랜덤 HEX를 LBA 0과 99에 반복 write 후 검증")
    void testScript3() {
        when(randomHex.getRandomValue()).thenReturn(VALUE);

        doReturn(VALUE).when(fileManager).getValueFromFile(anyInt());

        boolean result = scriptManager.testScript3();

        assertTrue(result);
        verify(jarExecutor, times(200)).executeWrite(eq(0), anyString());
        verify(jarExecutor, times(200)).executeWrite(eq(99), anyString());
        verify(fileManager, times(200)).getValueFromFile(0);
        verify(fileManager, times(200)).getValueFromFile(99);
        verify(fileManager, atLeast(400)).getValueFromFile(anyInt());
    }


    @Test
    @DisplayName("testScript4 - write, overwrite 후 erase → EMPTY_VALUE 검증")
    void testScript4() {
        doReturn(EMPTY_VALUE).when(fileManager).getValueFromFile(anyInt());
        stubEraseJar();

        boolean result = scriptManager.testScript4();

        assertTrue(result);
        verify(jarExecutor, times(1)).executeErase(eq(0), eq(3));
        verify(jarExecutor, times(31)).executeErase(anyInt(), eq(3));
        verify(jarExecutor, times(30 * 2)).executeWrite(anyInt(), anyString());
        verify(fileManager, times(30 * 3)).getValueFromFile(anyInt());
    }

    private void stubEraseJar() {
        doNothing().when(jarExecutor).executeErase(anyInt(), anyInt());
    }

    private void stubWriteJar() {
        doNothing().when(jarExecutor).executeWrite(anyInt(), anyString());
    }
}
