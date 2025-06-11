package com.samsung.testscript.command;

import com.samsung.testscript.ScriptManager;
import com.samsung.testshell.Command;

public class TestScript1Command implements Command {
    private final ScriptManager scriptManager;
    
    public TestScript1Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        // 명령어 길이 체크
        if (cmdArgs.length != 1) {
            throw new RuntimeException("INVALID COMMAND");
        }

        scriptManager.testScript1();
    }
}
