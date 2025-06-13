package com.samsung.command.testshell;

import com.samsung.command.Command;
import com.samsung.validator.ArgumentsValidator;
import com.samsung.validator.CommandValidator;

public class ReadCommand implements Command {

    private final TestShellManager testShellManager;

    public ReadCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentsValidator.validateTwoArgs(cmdArgs);
        try {
            Integer index = Integer.parseInt(cmdArgs[1]);
            CommandValidator.validateLbaRange(index);
            testShellManager.read(index);
        } catch (NumberFormatException e) {
            throw new RuntimeException("INVALID COMMAND");
        }
    }

}
