package com.samsung.testscript.command;

import com.samsung.testscript.ScriptManager;

public class TestScript1Command implements ScriptCommand {
    private final ScriptManager scriptManager;
    
    public TestScript1Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute() {
        scriptManager.testScript1();
    }
}
