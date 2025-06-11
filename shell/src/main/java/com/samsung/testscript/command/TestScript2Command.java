package com.samsung.testscript.command;

import com.samsung.testscript.ScriptManager;

public class TestScript2Command implements ScriptCommand {
    private final ScriptManager scriptManager;

    public TestScript2Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute() {
        scriptManager.testScript2();
    }
}
