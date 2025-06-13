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
        testShellManager.erase(
                extractValidatedEraseLba(cmdArgs),
                extractValidatedEraseSize(cmdArgs));
    }

    private Integer extractValidatedEraseLba(String[] cmdArgs) {
        Integer eraseLBA = toInt(cmdArgs[1]);
        CommandValidator.validateLbaRange(eraseLBA);
        return eraseLBA;
    }

    private Integer extractValidatedEraseSize(String[] cmdArgs) {
        Integer eraseSize = toInt(cmdArgs[2]);
        return eraseSize;
    }

    private Integer toInt(String arg) {
        try{
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new RuntimeException("INVALID COMMAND");
        }
    }
}
