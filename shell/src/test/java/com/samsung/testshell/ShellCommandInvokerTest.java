package com.samsung.testshell;

import com.samsung.testshell.commands.WriteCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShellCommandInvokerTest {

    @Mock
    private Map<String, ShellCommand> mockCommandMap;

    @Mock
    private ShellCommand shellCommand;

    @InjectMocks
    private ShellCommandInvoker invoker;

    @DisplayName("ShellCommand 객체 등록 실행 테스트")
    @Test
    void registerTest() {
        String commandName = "write";

        invoker.register(commandName, shellCommand);

        verify(mockCommandMap, times(1)).put(commandName, shellCommand);
    }

    @DisplayName("ShellCommand exectue 메서드 실행 pass 테스트")
    @Test
    void execute_pass_test() {
        String commandName = "write";
        Integer index = 1;
        String value = "0XFFFFFF";

        doReturn(shellCommand).when(mockCommandMap).get(commandName);

        invoker.execute(commandName, index, value);

        verify(shellCommand, times(1)).execute(index, value);
    }

    @DisplayName("ShellCommand exectue 메서드 실행 예외 테스트")
    @Test
    void execute_exception_test() {
        String invalidCommandName = "abcde";
        Integer index = 1;
        String value = "0XFFFFFF";

        doReturn(null).when(mockCommandMap).get(invalidCommandName);

        assertThrows(RuntimeException.class, () -> invoker.execute(invalidCommandName, index, value));
    }
}