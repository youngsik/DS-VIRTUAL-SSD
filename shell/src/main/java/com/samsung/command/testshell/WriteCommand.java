package com.samsung.command.testshell;

import com.samsung.resolver.ArgumentResolver;
import com.samsung.command.Command;
import com.samsung.validator.ArgumentCountValidator;

public class WriteCommand implements Command {

    private static final int LBA_INDEX = 1;
    private static final int VALUE_INDEX = 2;

    private final TestShellManager testShellManager;

    public WriteCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentCountValidator.validateThreeArgs(cmdArgs);
        testShellManager.write(
                extractValidatedLba(cmdArgs),
                extractValidatedValue(cmdArgs));
    }

    private Integer extractValidatedLba(String[] cmdArgs) {
        return ArgumentResolver.resolveLba(cmdArgs[LBA_INDEX]);
    }

    private String extractValidatedValue(String[] cmdArgs) {
        return ArgumentResolver.resolveValue(cmdArgs[VALUE_INDEX]);
    }
}
