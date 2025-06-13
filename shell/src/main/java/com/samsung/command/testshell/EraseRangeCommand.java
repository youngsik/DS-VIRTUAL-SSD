package com.samsung.command.testshell;

import com.samsung.command.ArgumentResolver;
import com.samsung.command.Command;
import com.samsung.validator.ArgumentsValidator;

public class EraseRangeCommand implements Command {

    private final TestShellManager testShellManager;

    public EraseRangeCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentsValidator.validateThreeArgs(cmdArgs);
        testShellManager.eraseRange(
                extractValidatedBeginLba(cmdArgs),
                extractValidatedEndLba(cmdArgs));
    }

    private Integer extractValidatedBeginLba(String[] cmdArgs) {
        return ArgumentResolver.resolveLba(cmdArgs[1]);
    }

    private Integer extractValidatedEndLba(String[] cmdArgs) {
        return ArgumentResolver.resolveLba(cmdArgs[2]);
    }
}
