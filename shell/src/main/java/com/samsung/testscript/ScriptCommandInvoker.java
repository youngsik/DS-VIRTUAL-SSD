package com.samsung.testscript;

import com.samsung.testshell.ShellCommand;

import java.util.HashMap;
import java.util.Map;

public class ScriptCommandInvoker {
    private Map<String, ShellCommand> commandMap = new HashMap<>();

    public void register(String commandName, ShellCommand command) {
        registerCommand(commandName, command);
    }

    public void execute(String[] cmdArgs) {
        ShellCommand command = getCommand(cmdArgs[0]);
        command.execute(cmdArgs);
    }

    private void registerCommand(String commandName, ShellCommand command) {
        commandMap.put(commandName, command);
    }

    private ShellCommand getCommand(String commandName) {
        ShellCommand command = commandMap.get(commandName);

        // 명령어 존재 여부 체크
        if (command == null) {
            throw new RuntimeException("INVALID COMMAND");
        }

        return command;
    }
}
