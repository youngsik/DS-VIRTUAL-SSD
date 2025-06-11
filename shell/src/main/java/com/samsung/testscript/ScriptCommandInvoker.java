package com.samsung.testscript;

import com.samsung.testscript.command.ScriptCommand;

import java.util.HashMap;
import java.util.Map;

public class ScriptCommandInvoker {
    private Map<String, ScriptCommand> commandMap = new HashMap<>();

    public void register(String commandName, ScriptCommand command) {
        registerCommand(commandName, command);
    }

    public void execute(String commandName) {
        ScriptCommand command = getCommand(commandName);
        command.execute();
    }

    private void registerCommand(String commandName, ScriptCommand command) {
        commandMap.put(commandName, command);
    }

    private ScriptCommand getCommand(String commandName) {
        ScriptCommand command = commandMap.get(commandName);

        if (command == null) {
            throw new RuntimeException("유효하지 않은 명령어입니다. 입력한 명령어: " + commandName);
        }
        return command;
    }
}
