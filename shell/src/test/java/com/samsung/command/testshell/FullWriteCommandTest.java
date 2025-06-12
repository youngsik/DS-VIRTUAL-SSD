package com.samsung.command.testshell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FullWriteCommandTest {

    public static final String VALUE = "0xFFFFFFFF";
    public static final String[] cmdArgs = new String[2];

    @Mock
    TestShellManager testShellManager;

    @InjectMocks
    FullWriteCommand fullWriteCommand;

    @DisplayName("execute 실행 테스트")
    @Test
    void executeTest() {
        cmdArgs[1] = VALUE;
        fullWriteCommand.execute(cmdArgs);
        verify(testShellManager, times(1)).fullwrite(VALUE);
    }
}