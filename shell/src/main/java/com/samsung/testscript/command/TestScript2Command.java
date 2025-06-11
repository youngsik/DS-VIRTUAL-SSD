package com.samsung.testscript.command;

import com.samsung.testscript.ScriptManager;
import com.samsung.testshell.ShellCommand;

public class TestScript2Command implements ShellCommand {
    private final ScriptManager scriptManager;

    public TestScript2Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        // 명령어 길이 체크
        if (cmdArgs.length != 1) {
            throw new RuntimeException("INVALID COMMAND");
        }

        scriptManager.testScript2();
    }
}
