package com.samsung.command;

import com.samsung.command.testscript.TestScript1Command;
import com.samsung.command.testscript.TestScript2Command;
import com.samsung.command.testscript.TestScript3Command;
import com.samsung.command.testscript.TestScript4Command;
import com.samsung.command.testshell.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommandInvokerTest {
    public static final String VALID_COMMAND_NAME = "help";

    public static final String INVALID_COMMAND_NAME = "abcde";
    public static final String[] cmdArgs = new String[1];

    @Mock
    private Command mockCommand;

    private Map<String, Command> commandMap;
    private CommandInvoker invoker;

    @BeforeEach
    void setUp() {
        commandMap = new HashMap<>();
        invoker = new CommandInvoker(commandMap);
    }

    @DisplayName("initAllCommands 메서드 테스트")
    @Test
    void initTest() {
        invoker.initAllCommands();

        assertShellComands();
        assertScriptCommands();
    }

    @DisplayName("ShellCommand exectue 메서드 실행 pass 테스트")
    @Test
    void execute_pass_test() {
        commandMap.put(VALID_COMMAND_NAME, mockCommand);
        cmdArgs[0] = VALID_COMMAND_NAME;

        invoker.execute(cmdArgs);

        verify(mockCommand, times(1)).execute(cmdArgs);
    }

    @DisplayName("ShellCommand exectue 메서드 실행 예외 테스트")
    @Test
    void execute_exception_test() {
        cmdArgs[0] = INVALID_COMMAND_NAME;

        assertThrows(RuntimeException.class, () -> invoker.execute(cmdArgs));
    }

    private void assertShellComands() {
        assertThat(commandMap.get("write")).isInstanceOf(WriteCommand.class);
        assertThat(commandMap.get("read")).isInstanceOf(ReadCommand.class);
        assertThat(commandMap.get("exit")).isInstanceOf(ExitCommand.class);
        assertThat(commandMap.get("help")).isInstanceOf(HelpCommand.class);
        assertThat(commandMap.get("fullwrite")).isInstanceOf(FullWriteCommand.class);
        assertThat(commandMap.get("fullread")).isInstanceOf(FullReadCommand.class);
    }

    private void assertScriptCommands() {
        assertTestScript1Commands();
        assertTestScript2Commands();
        assertTestScript3Commands();
        assertTestScript4Commands();
    }

    private void assertTestScript1Commands() {
        assertThat(commandMap.get("1_FullWriteAndReadCompare")).isInstanceOf(TestScript1Command.class);
        assertThat(commandMap.get("1_")).isInstanceOf(TestScript1Command.class);
    }

    private void assertTestScript2Commands() {
        assertThat(commandMap.get("2_PartialLBAWrite")).isInstanceOf(TestScript2Command.class);
        assertThat(commandMap.get("2_")).isInstanceOf(TestScript2Command.class);
    }

    private void assertTestScript3Commands() {
        assertThat(commandMap.get("3_WriteReadAging")).isInstanceOf(TestScript3Command.class);
        assertThat(commandMap.get("3_")).isInstanceOf(TestScript3Command.class);
    }

    private void assertTestScript4Commands() {
        assertThat(commandMap.get("4_EraseAndWriteAging")).isInstanceOf(TestScript4Command.class);
        assertThat(commandMap.get("4_")).isInstanceOf(TestScript4Command.class);
    }
}