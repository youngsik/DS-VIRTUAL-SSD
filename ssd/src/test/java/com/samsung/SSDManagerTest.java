package com.samsung;

import com.samsung.file.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.samsung.CommandType.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SSDManagerTest {
    public static final int LBA = 0;
    public static final String WRITE_VALUE = "0x00000000";
    public static final String ERROR_VALUE = "ERROR";

    @Mock
    FileManager fileManager;
    SSDManager writeSsdManager;
    SSDManager readSsdManager;
    SSDManager invalidValueSsdManager;
    SSDManager eraseSsdManager;

    @BeforeEach
    void setUp() {
        writeSsdManager = new SSDManager(new CmdData(WRITE, LBA, WRITE_VALUE), fileManager);
        readSsdManager = new SSDManager(new CmdData(READ, LBA, WRITE_VALUE), fileManager);
        invalidValueSsdManager = new SSDManager(new CmdData(ERROR, -1, ERROR_VALUE), fileManager);
        eraseSsdManager = new SSDManager(new CmdData(ERASE, 0, "9"), fileManager);
    }


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
        verify(fileManager).writeFile(LBA, WRITE_VALUE);
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
