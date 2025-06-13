package com.samsung.command.testshell;

import com.samsung.command.Command;
import com.samsung.validator.ArgumentsValidator;

public class FlushCommand implements Command {

    private final TestShellManager testShellManager;

    public FlushCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentsValidator.validateOneArgs(cmdArgs);
        testShellManager.flush();
    }
}
