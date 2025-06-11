package com.samsung.testshell.commands;

import com.samsung.testshell.Command;
import com.samsung.testshell.TestShellManager;

public class ExitCommand implements Command {

    private final TestShellManager testShellManager;

    public ExitCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        // 명령어 길이 체크
        if (cmdArgs.length != 1) {
            throw new RuntimeException("INVALID COMMAND");
        }

        testShellManager.exit();
    }
}
