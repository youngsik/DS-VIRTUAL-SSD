package com.samsung.command.testscript;

import com.samsung.command.Command;
import com.samsung.validator.ArgumentsValidator;

public class TestScript1Command implements Command {
    private final ScriptManager scriptManager;
    
    public TestScript1Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentsValidator.validateOneArgs(cmdArgs);
        scriptManager.testScript1();
    }

}
