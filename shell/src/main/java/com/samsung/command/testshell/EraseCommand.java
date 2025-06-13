package com.samsung.command.testshell;

import com.samsung.command.ArgumentResolver;
import com.samsung.command.Command;
import com.samsung.validator.CommandValidator;

public class EraseCommand implements Command {

    private static final int LBA_INDEX = 1;
    private static final int ERASE_SIZE_INDEX = 2;

    private final TestShellManager testShellManager;

    public EraseCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        CommandValidator.validateThreeArgs(cmdArgs);
        testShellManager.erase(
                extractValidatedEraseLba(cmdArgs),
                extractValidatedEraseSize(cmdArgs));
    }

    private Integer extractValidatedEraseLba(String[] cmdArgs) {
        return ArgumentResolver.resolveLba(cmdArgs[LBA_INDEX]);
    }

    private Integer extractValidatedEraseSize(String[] cmdArgs) {
        return ArgumentResolver.toInt(cmdArgs[ERASE_SIZE_INDEX]);
    }
}
