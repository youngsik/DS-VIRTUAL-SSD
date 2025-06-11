package com.samsung.testscript;

import com.samsung.testscript.command.ScriptCommand;

import java.util.HashMap;
import java.util.Map;

public class ScriptCommandInvoker {
    private Map<String, ScriptCommand> commandMap = new HashMap<>();

    public void register(String commandName, ScriptCommand command) {
        registerCommand(commandName, command);
    }

    public void execute(String[] cmdArgs) {
        ScriptCommand command = getCommand(cmdArgs[0]);
        command.execute(cmdArgs);
    }

    private void registerCommand(String commandName, ScriptCommand command) {
        commandMap.put(commandName, command);
    }

    private ScriptCommand getCommand(String commandName) {
        ScriptCommand command = commandMap.get(commandName);

        // 명령어 존재 여부 체크
        if (command == null) {
            throw new RuntimeException("INVALID COMMAND");
        }

        return command;
    }
}
