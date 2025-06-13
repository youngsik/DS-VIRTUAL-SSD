package com.samsung.command.testscript;

import com.samsung.command.Command;
import com.samsung.command.support.CommandValidator;

public class TestScript1Command implements Command {
    private final ScriptManager scriptManager;
    
    public TestScript1Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        CommandValidator.validateOneArgs(cmdArgs);
        boolean isTestPassed = scriptManager.testScript1();
        validatePostcondition(isTestPassed);
    }

    private static void validatePostcondition(boolean isTestPassed) {
        if (!isTestPassed) {
            throw new RuntimeException("TestScript1 is Failed!");
        }
    }

}
