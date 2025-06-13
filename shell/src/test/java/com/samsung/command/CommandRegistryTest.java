package com.samsung.command;

import com.samsung.command.testscript.TestScript1Command;
import com.samsung.command.testscript.TestScript2Command;
import com.samsung.command.testscript.TestScript3Command;
import com.samsung.command.testscript.TestScript4Command;
import com.samsung.command.testshell.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CommandRegistryTest {

    private Map<String, Command> commandMap;
    private CommandRegistry registry;

    @BeforeEach
    void setUp() {
        commandMap = new HashMap<>();
        registry = new CommandRegistry(commandMap);
    }

    @DisplayName("명령어 초기화 검증 테스트 - @BeforeEach에서 생성자 호출 시 명령어 Map 초기화 됨")
    @Test
    void initTest() {
        assertShellComands();
        assertScriptCommands();
    }

    @DisplayName("getCommand 메서드 실행 테스트  - @BeforeEach에서 생성자 호출 시 명령어 Map 초기화 됨")
    @Test
    void getCommandTest() {
        assertThat(registry.getCommand("write")).isInstanceOf(WriteCommand.class);
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