package com.samsung.command.testshell;

import com.samsung.command.ArgumentResolver;
import com.samsung.command.Command;
import com.samsung.validator.CommandValidator;

public class ReadCommand implements Command {

    private static final int LBA_INDEX = 1;

    private final TestShellManager testShellManager;

    public ReadCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        CommandValidator.validateTwoArgs(cmdArgs);
        testShellManager.read(extractValidatedLba(cmdArgs));
    }

    private Integer extractValidatedLba(String[] cmdArgs) {
        return ArgumentResolver.resolveLba(cmdArgs[LBA_INDEX]);
    }

}
