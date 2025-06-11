package com.samsung.testshell;

import java.util.HashMap;
import java.util.Map;

public class ShellCommandInvoker {
    private Map<String, ShellCommand> commandMap = new HashMap<>();

    public void register(String commandName, ShellCommand command) {
        commandMap.put(commandName, command);
    }

    public void execute(String commandName, Integer index, String value) {
        ShellCommand command = getCommand(commandName);
        command.execute(index, value);
    }

    private ShellCommand getCommand(String commandName) {
        ShellCommand command = commandMap.get(commandName);

        if (command == null) {
            throw new RuntimeException("유효하지 않은 명령어입니다. 입력한 명령어: " + commandName);

        }
        return command;
    }
}
