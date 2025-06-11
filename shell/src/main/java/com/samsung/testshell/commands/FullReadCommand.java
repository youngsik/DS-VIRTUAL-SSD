package com.samsung.testshell.commands;

import com.samsung.testshell.ShellCommand;
import com.samsung.testshell.TestShellManager;

public class FullReadCommand implements ShellCommand {

    private TestShellManager testShellManager;

    public FullReadCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void excute() {
        testShellManager.fullread();
    }
}
