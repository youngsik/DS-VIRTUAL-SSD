package com.samsung.command.testscript;

import com.samsung.command.Command;
import com.samsung.validator.ArgumentCountValidator;

public class TestScript1Command implements Command {
    private final ScriptManager scriptManager;
    
    public TestScript1Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentCountValidator.validateOneArgs(cmdArgs);
        printTestScriptResult(scriptManager.testScript1());
    }

    private static void printTestScriptResult(boolean isTestPassed) {
        if (isTestPassed) {
            System.out.println("PASS");
            return;
        }
        throw new RuntimeException("FAIL");
    }

}
