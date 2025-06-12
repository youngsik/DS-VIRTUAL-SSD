package com.samsung.command.testshell;

import com.samsung.command.Command;

public class FlushCommand implements Command {
    private final TestShellManager testShellManager;

    public FlushCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        if(cmdArgs.length != 1) {
            throw new RuntimeException("FLUSH COMMAND ERROR");
        }

        testShellManager.flush();
    }
}
