package com.samsung.command.testshell;

import com.samsung.command.Command;
import com.samsung.validator.ArgumentsValidator;

public class HelpCommand implements Command {

    private final TestShellManager testShellManager;

    public HelpCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentsValidator.validateOneArgs(cmdArgs);
        testShellManager.help();
    }

}
