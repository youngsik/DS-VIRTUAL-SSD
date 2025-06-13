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

        int beginLBA;
        int endLBA;

        try{
            beginLBA = Integer.parseInt(cmdArgs[1]);
            endLBA = Integer.parseInt(cmdArgs[2]);
        } catch(NumberFormatException e) {
            throw new RuntimeException("INVALID COMMAND PARAMETER");
        }

        CommandValidator.validateLbaRange(beginLBA);
        CommandValidator.validateLbaRange(endLBA);

        testShellManager.eraseRange(beginLBA, endLBA);
    }
}
