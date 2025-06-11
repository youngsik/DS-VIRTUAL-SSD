package com.samsung.testshell.commands;

import com.samsung.Command;
import com.samsung.testshell.TestShellManager;

public class ExitCommand implements Command {

    private TestShellManager testShellManager;

    public ExitCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void excute() {
        testShellManager.exit();
    }
}
