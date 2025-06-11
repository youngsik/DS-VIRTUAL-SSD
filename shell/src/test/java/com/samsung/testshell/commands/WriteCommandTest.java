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

    public static final String INDEX = "1";
    public static final String VALUE = "0xFFFFFFFF";

    @Mock
    TestShellManager testShellManager;

    @InjectMocks
    WriteCommand writeCommand;

    @DisplayName("execute 실행 테스트")
    @Test
    void executeTest() {
        String[] cmdArgs = new String[3];
        cmdArgs[1] = INDEX;
        cmdArgs[2] = VALUE;
        writeCommand.execute(cmdArgs);
        verify(testShellManager, times(1)).write(Integer.parseInt(INDEX), VALUE);
    }
}