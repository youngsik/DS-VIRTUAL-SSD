package com.samsung.command.testscript;

import com.samsung.command.Command;
import com.samsung.validator.CommandValidator;

public class TestScript4Command implements Command {
    private final ScriptManager scriptManager;

    public TestScript4Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        CommandValidator.validateOneArgs(cmdArgs);
        boolean isTestPassed = scriptManager.testScript4();
        validatePostcondition(isTestPassed);
    }

    private void validatePostcondition(boolean isTestPassed) {
        if (!isTestPassed) {
            throw new RuntimeException("TestScript4 is Failed!");
        }
    }
}
