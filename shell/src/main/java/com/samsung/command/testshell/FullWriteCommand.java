package com.samsung.command.testshell;

import com.samsung.command.Command;

public class FullWriteCommand implements Command {

    private final TestShellManager testShellManager;

    public FullWriteCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        if (cmdArgs.length != 2) {
            throw new RuntimeException("INVALID COMMAND");
        }

        String value = cmdArgs[1];

        if (value == null) {
            throw new RuntimeException("INVALID COMMAND");
        }

        if (!value.matches("^0x[0-9A-F]{8}$")) {
            throw new RuntimeException("INVALID COMMAND");
        }

        testShellManager.fullwrite(value);
    }
}
