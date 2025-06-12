package com.samsung.command.testscript;

import com.samsung.command.Command;

public class TestScript3Command implements Command {
    private final ScriptManager scriptManager;

    public TestScript3Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        // 명령어 길이 체크
        if (cmdArgs.length != 1) {
            throw new RuntimeException("INVALID COMMAND");
        }

        scriptManager.testScript3();
    }
}
