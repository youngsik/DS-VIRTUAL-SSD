package com.samsung.command.testshell;

import com.samsung.resolver.ArgumentResolver;
import com.samsung.command.Command;
import com.samsung.validator.ArgumentCountValidator;

public class FullWriteCommand implements Command {

    private static final int VALUE_INDEX = 1;

    private final TestShellManager testShellManager;

    public FullWriteCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentCountValidator.validateTwoArgs(cmdArgs);
        testShellManager.fullwrite(extractValidatedValue(cmdArgs));
    }

    private String extractValidatedValue(String[] cmdArgs) {
        return ArgumentResolver.resolveValue(cmdArgs[VALUE_INDEX]);
    }
}
