package com.samsung.testshell.commands;

import com.samsung.testshell.ShellCommand;
import com.samsung.testshell.TestShellManager;

public class FullWriteCommand implements ShellCommand {

    private TestShellManager testShellManager;

    public FullWriteCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void excute() {
        testShellManager.fullwrite();
    }
}
