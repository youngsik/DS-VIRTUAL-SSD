package com.samsung.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandInvokerTest {

    public static final String VALID_COMMAND_NAME = "write";
    public static final String INVALID_COMMAND_NAME = "abcde";
    public static final Command EMPTY_COMMAND = null;
    public static final String[] cmdArgs = new String[1];

    @Mock
    private Map<String, Command> mockCommandMap;

    @Mock
    private Command mockCommand;

    @InjectMocks
    private CommandInvoker invoker;

    @DisplayName("ShellCommand 객체 등록 실행 테스트")
    @Test
    void registerTest() {
        invoker.register(VALID_COMMAND_NAME, mockCommand);
        verify(mockCommandMap, times(1)).put(VALID_COMMAND_NAME, mockCommand);
    }

    @DisplayName("ShellCommand exectue 메서드 실행 pass 테스트")
    @Test
    void execute_pass_test() {
        doReturn(mockCommand).when(mockCommandMap).get(VALID_COMMAND_NAME);
        cmdArgs[0] = VALID_COMMAND_NAME;

        invoker.execute(cmdArgs);

        verify(mockCommand, times(1)).execute(cmdArgs);
    }

    @DisplayName("ShellCommand exectue 메서드 실행 예외 테스트")
    @Test
    void execute_exception_test() {
        doReturn(EMPTY_COMMAND).when(mockCommandMap).get(INVALID_COMMAND_NAME);
        cmdArgs[0] = INVALID_COMMAND_NAME;

        assertThrows(RuntimeException.class, () -> invoker.execute(cmdArgs));
    }
}