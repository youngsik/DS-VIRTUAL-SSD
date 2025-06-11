package com.samsung.testshell.commands;

import com.samsung.testshell.TestShellManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WriteCommandTest {

    @Mock
    TestShellManager testShellManager;

    @InjectMocks
    WriteCommand writeCommand;

    @DisplayName("execute 실행 테스트")
    @Test
    void executeTest() {
        writeCommand.execute(1, "0XFFFFFF");
        verify(testShellManager, times(1)).write(1, "0XFFFFFF");
    }
}