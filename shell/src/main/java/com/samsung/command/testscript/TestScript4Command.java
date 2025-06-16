package com.samsung.command.testscript;

import com.samsung.command.Command;
import com.samsung.validator.ArgumentCountValidator;

public class TestScript4Command implements Command {
    private final ScriptManager scriptManager;

    public TestScript4Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentCountValidator.validateOneArgs(cmdArgs);
        printTestScriptResult(scriptManager.testScript4());
    }

    private static void printTestScriptResult(boolean isTestPassed) {
        if (isTestPassed) {
            System.out.println("PASS");
            return;
        }
        throw new RuntimeException("FAIL");
    }

}
