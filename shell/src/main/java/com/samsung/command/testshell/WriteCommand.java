package com.samsung.command.testshell;

import com.samsung.command.Command;
import com.samsung.validator.ArgumentsValidator;
import com.samsung.validator.CommandValidator;

public class WriteCommand implements Command {

    private final TestShellManager testShellManager;

    public WriteCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentsValidator.validateThreeArgs(cmdArgs);
        try {
            Integer index = Integer.parseInt(cmdArgs[1]);
            String value = cmdArgs[2];

            CommandValidator.validateLbaRange(index);
            CommandValidator.validateNull(value);
            CommandValidator.validateValueFormat(value);

            testShellManager.write(index, value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("INVALID COMMAND"); // 숫자 아니면 Exception
        }
    }

}
