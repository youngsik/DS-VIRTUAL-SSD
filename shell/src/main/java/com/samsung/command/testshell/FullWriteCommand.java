package com.samsung.command.testshell;

import com.samsung.command.ArgumentResolver;
import com.samsung.command.Command;
import com.samsung.validator.ArgumentsValidator;

public class FullWriteCommand implements Command {

    private final TestShellManager testShellManager;

    public FullWriteCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentsValidator.validateTwoArgs(cmdArgs);
        testShellManager.fullwrite(extractValidatedValue(cmdArgs));
    }

    private String extractValidatedValue(String[] cmdArgs) {
        return ArgumentResolver.resolveValue(cmdArgs[1]);
    }
}
