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
        testShellManager.write(
                extractValidatedLba(cmdArgs),
                extractValidatedValue(cmdArgs));
    }

    private Integer extractValidatedLba(String[] cmdArgs) {
        Integer lba = toInt(cmdArgs[1]);
        CommandValidator.validateLbaRange(lba);
        return lba;
    }

    private String extractValidatedValue(String[] cmdArgs) {
        String value = cmdArgs[2];
        CommandValidator.validateNull(value);
        CommandValidator.validateValueFormat(value);
        return value;
    }

    private Integer toInt(String arg) {
        try{
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new RuntimeException("INVALID COMMAND");
        }
    }
}
