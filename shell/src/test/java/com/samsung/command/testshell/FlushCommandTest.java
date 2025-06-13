package com.samsung.command.testshell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlushCommandTest {

    private static final String[] ONE_CMD_ARGS = new String[1];
    private static final String[] TWO_CMD_ARGS = new String[2];

    @Mock
    TestShellManager testShellManager;

    @InjectMocks
    FlushCommand flushCommand;

    @DisplayName("인자 개수가 1개이면 flush 메서드 호출 PASS")
    @Test
    void execute_passTest() {
        flushCommand.execute(ONE_CMD_ARGS);
        verify(testShellManager, times(1)).flush();
    }

    @DisplayName("인자 개수가 1개가 아니면 FAIL")
    @Test
    void execute_failTest() {
        assertThrows(RuntimeException.class,
                () -> flushCommand.execute(TWO_CMD_ARGS));
    }
}