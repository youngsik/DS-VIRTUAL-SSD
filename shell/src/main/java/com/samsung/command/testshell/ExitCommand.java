package com.samsung.command.testshell;

import com.samsung.command.Command;

public class ExitCommand implements Command {

    private final TestShellManager testShellManager;

    public ExitCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        if (cmdArgs.length != 1) {
            throw new RuntimeException("INVALID COMMAND");
        }

        testShellManager.exit();
    }
}
