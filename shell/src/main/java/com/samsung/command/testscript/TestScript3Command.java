package com.samsung.command.testscript;

import com.samsung.command.Command;
import com.samsung.command.support.CommandValidator;

public class TestScript3Command implements Command {
    private final ScriptManager scriptManager;

    public TestScript3Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        CommandValidator.validateOneArgs(cmdArgs);
        boolean isTestPassed = scriptManager.testScript3();
        validatePostcondition(isTestPassed);
    }

    private void validatePostcondition(boolean isTestPassed) {
        if (!isTestPassed) {
            throw new RuntimeException("TestScript3 is Failed!");
        }
    }

}
