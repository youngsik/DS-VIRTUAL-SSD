package com.samsung.command.testshell;

import com.samsung.command.Command;
import com.samsung.validator.ArgumentsValidator;
import com.samsung.validator.CommandValidator;

public class FullWriteCommand implements Command {

    private final TestShellManager testShellManager;

    public FullWriteCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentsValidator.validateTwoArgs(cmdArgs);
        String value = cmdArgs[1];
        CommandValidator.validateNull(value);
        CommandValidator.validateValueFormat(value);

        testShellManager.fullwrite(value);
    }
}
