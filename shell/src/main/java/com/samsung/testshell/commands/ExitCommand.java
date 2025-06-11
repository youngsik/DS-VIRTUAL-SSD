package com.samsung.testshell.commands;

import com.samsung.testshell.ShellCommand;
import com.samsung.testshell.TestShellManager;

public class ExitCommand implements ShellCommand {

    private TestShellManager testShellManager;

    public ExitCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(Integer index, String value) {
        testShellManager.exit();
    }
}
