package com.samsung.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandInvokerTest {
    public static final String VALID_COMMAND_NAME = "help";
    public static final String INVALID_COMMAND_NAME = "abcde";
    public static final String[] VALID_CMD_ARGS = {VALID_COMMAND_NAME};
    public static final String[] INVALID_CMD_ARGS = {INVALID_COMMAND_NAME};

    @Mock
    private Command mockCommand;

    @Mock
    private CommandRegistry mockRegistry;

    @InjectMocks
    private CommandInvoker invoker;

    @DisplayName("ShellCommand exectue 메서드 실행 pass 테스트")
    @Test
    void execute_pass_test() {
        doReturn(mockCommand).when(mockRegistry).getCommand(VALID_COMMAND_NAME);

        invoker.execute(VALID_CMD_ARGS);

        verify(mockRegistry, times(1)).getCommand(VALID_COMMAND_NAME);
    }

    @DisplayName("ShellCommand exectue 메서드 실행 예외 테스트")
    @Test
    void execute_exception_test() {
        assertThrows(RuntimeException.class, () -> invoker.execute(INVALID_CMD_ARGS));
    }
}