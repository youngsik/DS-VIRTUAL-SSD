package com.samsung.command.testscript;

import com.samsung.command.Command;
import com.samsung.validator.ArgumentCountValidator;

public class TestScript2Command implements Command {
    private final ScriptManager scriptManager;

    public TestScript2Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentCountValidator.validateOneArgs(cmdArgs);
        printTestScriptResult(scriptManager.testScript2());
    }

    private static void printTestScriptResult(boolean isTestPassed) {
        if (isTestPassed) {
            System.out.println("PASS");
            return;
        }
        throw new RuntimeException("FAIL");
    }

}
