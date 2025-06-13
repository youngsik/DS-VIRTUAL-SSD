package com.samsung.command.testshell;

import com.samsung.command.ArgumentResolver;
import com.samsung.command.Command;
import com.samsung.validator.ArgumentsValidator;

public class EraseCommand implements Command {

    private final TestShellManager testShellManager;

    public EraseCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentsValidator.validateThreeArgs(cmdArgs);
        testShellManager.erase(
                extractValidatedEraseLba(cmdArgs),
                extractValidatedEraseSize(cmdArgs));
    }

    private Integer extractValidatedEraseLba(String[] cmdArgs) {
        return ArgumentResolver.resolveLba(cmdArgs[1]);
    }

    private Integer extractValidatedEraseSize(String[] cmdArgs) {
        return ArgumentResolver.toInt(cmdArgs[2]);
    }
}
