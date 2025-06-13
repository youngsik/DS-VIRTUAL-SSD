package com.samsung.command.testshell;

import com.samsung.support.ArgumentResolver;
import com.samsung.command.Command;
import com.samsung.support.CommandValidator;

public class FullWriteCommand implements Command {

    private static final int VALUE_INDEX = 1;

    private final TestShellManager testShellManager;

    public FullWriteCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        CommandValidator.validateTwoArgs(cmdArgs);
        testShellManager.fullwrite(extractValidatedValue(cmdArgs));
    }

    private String extractValidatedValue(String[] cmdArgs) {
        return ArgumentResolver.resolveValue(cmdArgs[VALUE_INDEX]);
    }
}
