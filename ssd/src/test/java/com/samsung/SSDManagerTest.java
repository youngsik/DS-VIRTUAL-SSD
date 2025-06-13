package com.samsung;

import com.samsung.buffer.BufferProcessor;
import com.samsung.file.FileManager;
import com.samsung.common.CmdData;
import com.samsung.ssd.SSDManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.samsung.common.CommandType.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SSDManagerTest {
    public static final int LBA = 0;
    public static final String WRITE_VALUE = "0x12341234";
    public static final String ERROR_VALUE = "ERROR";

    @Mock
    FileManager fileManager;
    @Mock
    BufferProcessor bufferProcessor;
    SSDManager writeSsdManager;
    SSDManager readSsdManager;
    SSDManager invalidValueSsdManager;
    SSDManager eraseSsdManager;

    @BeforeEach
    void setUp() {
        writeSsdManager = new SSDManager(new CmdData(WRITE, LBA, WRITE_VALUE), fileManager, bufferProcessor);
        readSsdManager = new SSDManager(new CmdData(READ, LBA, WRITE_VALUE), fileManager, bufferProcessor);
        invalidValueSsdManager = new SSDManager(new CmdData(ERROR, -1, ERROR_VALUE), fileManager, bufferProcessor);
        eraseSsdManager = new SSDManager(new CmdData(ERASE, 0, "9"), fileManager, bufferProcessor);
    }


    @Test
    @DisplayName("읽기 테스트")
    public void readTest() {
        when(bufferProcessor.process(any(CmdData.class))).thenReturn(WRITE_VALUE);

        readSsdManager.cmdExecuteFromBuffer();

        verify(fileManager).writeOnOutputFile(WRITE_VALUE);
    }

    @Test
    @DisplayName("읽기 2번 테스트")
    public void readTwiceTest() {
        when(bufferProcessor.process(any(CmdData.class))).thenReturn(WRITE_VALUE);
        readSsdManager.cmdExecuteFromBuffer();
        readSsdManager.cmdExecuteFromBuffer();
        verify(fileManager,times(2)).writeOnOutputFile(WRITE_VALUE);
    }

    @Test
    @DisplayName("ERROR 처리 테스트")
    void valueErrorTest() {
        invalidValueSsdManager.cmdExecuteFromBuffer();
        verify(fileManager, times(1)).writeOnOutputFile(ERROR_VALUE);
    }

}
