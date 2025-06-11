package com.samsung.testshell.commands;

import com.samsung.testshell.ShellCommand;
import com.samsung.testshell.TestShellManager;

public class WriteCommand implements ShellCommand {

    private final TestShellManager testShellManager;

    public WriteCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(Integer index, String value) {
        testShellManager.write(index, value);
    }
}
