package com.samsung.command.testscript;

import com.samsung.command.Command;

public class TestScript4Command implements Command {
    private final ScriptManager scriptManager;

    public TestScript4Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        if (cmdArgs.length != 1) {
            throw new RuntimeException("INVALID COMMAND");
        }

        scriptManager.testScript4();
    }
}
