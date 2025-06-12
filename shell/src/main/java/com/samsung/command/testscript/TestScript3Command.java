package com.samsung.command.testscript;

import com.samsung.command.Command;
import com.samsung.validator.ArgumentsValidator;

public class TestScript3Command implements Command {
    private final ScriptManager scriptManager;

    public TestScript3Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentsValidator.validateOneArgs(cmdArgs);
        scriptManager.testScript3();
    }

}
