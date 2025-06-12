package com.samsung;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SSDManagerTest {
    public static final int LBA = 0;
    public static final String WRITE_VALUE = "0x00000000";
    public static final String ERROR_VALUE = "ERROR";

    @Mock
    FileManager fileManager;

    @InjectMocks
    SSDManager writeSsdManager = new SSDManager("W", LBA, WRITE_VALUE);
    @InjectMocks
    SSDManager readSsdManager = new SSDManager("R", LBA, WRITE_VALUE);
    @InjectMocks
    SSDManager invalidValueSsdManager = new SSDManager(ERROR_VALUE, -1, ERROR_VALUE);
    @InjectMocks
    SSDManager eraseSsdManager = new SSDManager("E", 0, "9");

    @Test
    @DisplayName("읽기 테스트")
    public void readTest() {
        readSsdManager.cmdExecute();
        verify(fileManager, times(1)).readFile(LBA);
    }

    @Test
    @DisplayName("쓰기 테스트")
    public void writeTest() {
        writeSsdManager.cmdExecute();
        verify(fileManager, times(1)).writeFile(LBA, WRITE_VALUE);
    }

    @Test
    @DisplayName("읽기 2번 테스트")
    public void readTwiceTest() {
        readSsdManager.cmdExecute();
        readSsdManager.cmdExecute();
        verify(fileManager, times(2)).readFile(LBA);
    }

    @Test
    @DisplayName("ERROR 처리 테스트")
    void valueErrorTest() {
        invalidValueSsdManager.cmdExecute();
        verify(fileManager, times(1)).writeOnOutputFile(ERROR_VALUE);
    }

    @Test
    @DisplayName("지우기 명령어 테스트(성공)")
    void cmdExecuteErasePass() {
        eraseSsdManager.cmdExecute();
        for(int i=0; i<9; i++) {
            verify(fileManager, times(1)).writeFile(i, WRITE_VALUE);
        }
    }
}
