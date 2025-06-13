package com.samsung.command.testshell;

import com.samsung.command.Command;
import com.samsung.support.CommandValidator;

public class HelpCommand implements Command {

    private final TestShellManager testShellManager;

    public HelpCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        CommandValidator.validateOneArgs(cmdArgs);
        testShellManager.help();
    }

}
