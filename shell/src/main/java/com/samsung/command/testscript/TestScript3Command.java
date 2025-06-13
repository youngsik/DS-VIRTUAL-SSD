package com.samsung.command.testscript;

import com.samsung.command.Command;
import com.samsung.validator.ArgumentCountValidator;

public class TestScript3Command implements Command {
    private final ScriptManager scriptManager;

    public TestScript3Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentCountValidator.validateOneArgs(cmdArgs);
        boolean isTestPassed = scriptManager.testScript3();
        validatePostcondition(isTestPassed);
    }

    private void validatePostcondition(boolean isTestPassed) {
        if (!isTestPassed) {
            throw new RuntimeException("TestScript3 is Failed!");
        }
    }

}
