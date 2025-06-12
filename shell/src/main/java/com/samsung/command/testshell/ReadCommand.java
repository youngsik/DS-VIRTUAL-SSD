package com.samsung.command.testshell;

import com.samsung.command.Command;

public class ReadCommand implements Command {

    private final TestShellManager testShellManager;

    public ReadCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        if (cmdArgs.length != 2) {
            throw new RuntimeException("INVALID COMMAND");
        }

        try {
            Integer index = Integer.parseInt(cmdArgs[1]);
            if (index < 0 || index > 99) {
                throw new RuntimeException("INVALID COMMAND");
            }

            testShellManager.read(index);
        } catch (NumberFormatException e) {
            throw new RuntimeException("INVALID COMMAND"); // 숫자 아니면 Exception
        }
    }
}
