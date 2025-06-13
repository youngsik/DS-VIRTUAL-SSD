package com.samsung.command.testshell;

import com.samsung.support.ArgumentResolver;
import com.samsung.command.Command;
import com.samsung.support.CommandValidator;

public class EraseRangeCommand implements Command {

    private static final int BEGIN_LBA_INDEX = 1;
    private static final int END_LBA_INDEX = 2;

    private final TestShellManager testShellManager;

    public EraseRangeCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        CommandValidator.validateThreeArgs(cmdArgs);
        testShellManager.eraseRange(
                extractValidatedBeginLba(cmdArgs),
                extractValidatedEndLba(cmdArgs));
    }

    private Integer extractValidatedBeginLba(String[] cmdArgs) {
        return ArgumentResolver.resolveLba(cmdArgs[BEGIN_LBA_INDEX]);
    }

    private Integer extractValidatedEndLba(String[] cmdArgs) {
        return ArgumentResolver.resolveLba(cmdArgs[END_LBA_INDEX]);
    }
}
