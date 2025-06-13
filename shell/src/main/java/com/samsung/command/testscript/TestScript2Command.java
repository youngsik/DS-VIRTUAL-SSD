package com.samsung.command.testscript;

import com.samsung.command.Command;
import com.samsung.command.support.CommandValidator;

public class TestScript2Command implements Command {
    private final ScriptManager scriptManager;

    public TestScript2Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        CommandValidator.validateOneArgs(cmdArgs);
        boolean isTestPassed = scriptManager.testScript2();
        validatePostcondition(isTestPassed);
    }

    private void validatePostcondition(boolean isTestPassed) {
        if (!isTestPassed) {
            throw new RuntimeException("TestScript2 is Failed!");
        }
    }

}
