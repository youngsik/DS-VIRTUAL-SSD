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
class ReadCommandTest {

    public static final String INDEX = "1";

    @Mock
    TestShellManager testShellManager;

    @InjectMocks
    ReadCommand readCommand;

    @DisplayName("execute 실행 테스트")
    @Test
    void executeTest() {
        String[] cmdArgs = new String[2];
        cmdArgs[1] = INDEX;
        readCommand.execute(cmdArgs);
        verify(testShellManager, times(1)).read(Integer.parseInt(INDEX));
    }
}