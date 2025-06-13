package com.samsung.command.testshell;

import com.samsung.command.ArgumentResolver;
import com.samsung.command.Command;
import com.samsung.validator.ArgumentsValidator;

public class WriteCommand implements Command {

    private final TestShellManager testShellManager;

    public WriteCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentsValidator.validateThreeArgs(cmdArgs);
        testShellManager.write(
                extractValidatedLba(cmdArgs),
                extractValidatedValue(cmdArgs));
    }

    private Integer extractValidatedLba(String[] cmdArgs) {
        return ArgumentResolver.resolveLba(cmdArgs[1]);
    }

    private String extractValidatedValue(String[] cmdArgs) {
        return ArgumentResolver.resolveValue(cmdArgs[2]);
    }
}
