package com.samsung.testshell.commands;

import com.samsung.Command;
import com.samsung.testshell.TestShellManager;

public class FullReadCommand implements Command {

    private final TestShellManager testShellManager;

    public FullReadCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        // 명령어 길이 체크
        if (cmdArgs.length != 1) {
            throw new RuntimeException("INVALID COMMAND");
        }

        testShellManager.fullread();
    }
}
