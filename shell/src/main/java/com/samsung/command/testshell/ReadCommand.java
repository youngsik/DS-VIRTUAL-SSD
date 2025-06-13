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
        testShellManager.read(extractValidatedLba(cmdArgs));
    }

    private Integer extractValidatedLba(String[] cmdArgs) {
        Integer lba = toInt(cmdArgs[1]);
        CommandValidator.validateLbaRange(lba);
        return lba;
    }

    private Integer toInt(String arg) {
        try{
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new RuntimeException("INVALID COMMAND");
        }
    }

}
