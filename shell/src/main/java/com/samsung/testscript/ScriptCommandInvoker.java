package com.samsung.testscript;

import com.samsung.testshell.Command;

import java.util.HashMap;
import java.util.Map;

public class ScriptCommandInvoker {
    private Map<String, Command> commandMap = new HashMap<>();

    public void register(String commandName, Command command) {
        registerCommand(commandName, command);
    }

    public void execute(String[] cmdArgs) {
        Command command = getCommand(cmdArgs[0]);
        command.execute(cmdArgs);
    }

    private void registerCommand(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    private Command getCommand(String commandName) {
        Command command = commandMap.get(commandName);

        // 명령어 존재 여부 체크
        if (command == null) {
            throw new RuntimeException("INVALID COMMAND");
        }

        return command;
    }
}
