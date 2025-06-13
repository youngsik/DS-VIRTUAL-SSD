package com.samsung.command.testshell;

import com.samsung.command.Command;
import com.samsung.validator.ArgumentsValidator;
import com.samsung.validator.CommandValidator;

public class EraseCommand implements Command {
    private final TestShellManager testShellManager;

    public EraseCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentsValidator.validateThreeArgs(cmdArgs);

        int eraseLBA;
        int eraseSize;

        try{
            eraseLBA = Integer.parseInt(cmdArgs[1]);
            eraseSize = Integer.parseInt(cmdArgs[2]);
        }catch(NumberFormatException e) {
            throw new RuntimeException("INVALID ERASE START LOCATION");
        }

        CommandValidator.validateLbaRange(eraseLBA);

        testShellManager.erase(eraseLBA, eraseSize);
    }
}
