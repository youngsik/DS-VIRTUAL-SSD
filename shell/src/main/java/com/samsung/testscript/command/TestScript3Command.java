package com.samsung.testscript.command;

import com.samsung.testscript.ScriptManager;

public class TestScript3Command implements ScriptCommand {
    private final ScriptManager scriptManager;

    public TestScript3Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute() {
        scriptManager.testScript3();
    }
}
