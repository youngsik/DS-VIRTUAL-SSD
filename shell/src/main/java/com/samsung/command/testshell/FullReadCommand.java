package com.samsung.command.testshell;

import com.samsung.command.Command;
import com.samsung.command.support.CommandValidator;

public class FullReadCommand implements Command {

    private final TestShellManager testShellManager;

    public FullReadCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        CommandValidator.validateOneArgs(cmdArgs);
        testShellManager.fullread();
    }

}
