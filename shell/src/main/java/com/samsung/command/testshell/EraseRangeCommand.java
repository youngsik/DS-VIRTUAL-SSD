package com.samsung.command.testshell;
import com.samsung.command.Command;
import com.samsung.validator.ArgumentsValidator;
import com.samsung.validator.CommandValidator;

public class EraseRangeCommand implements Command {

    private final TestShellManager testShellManager;

    public EraseRangeCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        ArgumentsValidator.validateThreeArgs(cmdArgs);
        testShellManager.eraseRange(
                extractValidatedBeginLba(cmdArgs),
                extractValidatedEndLba(cmdArgs));
    }

    private Integer extractValidatedBeginLba(String[] cmdArgs) {
        Integer beginLBA = toInt(cmdArgs[1]);
        CommandValidator.validateLbaRange(beginLBA);
        return beginLBA;
    }

    private Integer extractValidatedEndLba(String[] cmdArgs) {
        Integer endLba = toInt(cmdArgs[2]);
        CommandValidator.validateLbaRange(endLba);
        return endLba;
    }


    private Integer toInt(String arg) {
        try{
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new RuntimeException("INVALID COMMAND");
        }
    }

}
