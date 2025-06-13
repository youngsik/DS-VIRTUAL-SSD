package com.samsung.command.testshell;

import com.samsung.command.Command;
import com.samsung.validator.ArgumentCountValidator;

public class HelpCommand implements Command {

    private final TestShellManager testShellManager;

    public HelpCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentCountValidator.validateOneArgs(cmdArgs);
        testShellManager.help();
    }

}
