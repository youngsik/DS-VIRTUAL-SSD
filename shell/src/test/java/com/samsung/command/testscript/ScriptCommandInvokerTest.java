package com.samsung.command.testscript;

import com.samsung.command.Command;
import com.samsung.command.CommandInvoker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScriptCommandInvokerTest {

    public static final String VALID_COMMAND_NAME = "write";
    public static final String INVALID_COMMAND_NAME = "abcde";
    public static final Command EMPTY_COMMAND = null;
    public static final String[] cmdArgs = new String[1];

    @Mock
    private Map<String, Command> mockCommandMap;

    @Mock
    private Command mockScriptCommand;

    @InjectMocks
    private CommandInvoker invoker;

    @DisplayName("ScriptCommand 객체 등록 실행 테스트")
    @Test
    void registerTest() {
        invoker.register(VALID_COMMAND_NAME, mockScriptCommand);
        verify(mockCommandMap, times(1)).put(VALID_COMMAND_NAME, mockScriptCommand);
    }

    @DisplayName("ScriptCommand exectue 메서드 실행 pass 테스트")
    @Test
    void execute_pass_test() {
        doReturn(mockScriptCommand).when(mockCommandMap).get(VALID_COMMAND_NAME);
        cmdArgs[0] = VALID_COMMAND_NAME;

        invoker.execute(cmdArgs);

        verify(mockScriptCommand, times(1)).execute(cmdArgs);
    }

    @DisplayName("ScriptCommand exectue 메서드 실행 예외 테스트")
    @Test
    void execute_exception_test() {
        doReturn(EMPTY_COMMAND).when(mockCommandMap).get(INVALID_COMMAND_NAME);
        cmdArgs[0] = INVALID_COMMAND_NAME;

        assertThrows(RuntimeException.class, () -> invoker.execute(cmdArgs));
    }
}