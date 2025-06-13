package com.samsung.command.testshell;

import com.samsung.command.Command;
import com.samsung.validator.ArgumentCountValidator;

public class FlushCommand implements Command {

    private final TestShellManager testShellManager;

    public FlushCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentCountValidator.validateOneArgs(cmdArgs);
        testShellManager.flush();
    }
}
