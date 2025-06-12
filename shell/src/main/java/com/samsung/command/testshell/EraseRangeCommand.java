package com.samsung.command.testshell;

import com.samsung.command.Command;

public class EraseRangeCommand implements Command {
    private final TestShellManager testShellManager;

    public EraseRangeCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        if(cmdArgs.length != 3) {
            throw new RuntimeException("INVALID COMMAND PARAMETER");
        }

        int beginLBA;
        int endLBA;

        // LBA 숫자 여부 확인
        try{
            beginLBA = Integer.parseInt(cmdArgs[1]);
            endLBA = Integer.parseInt(cmdArgs[2]);
        } catch(NumberFormatException e) {
            throw new RuntimeException("INVALID COMMAND PARAMETER");
        }

        // LBA 범위 확인
        if(!isLBAValid(beginLBA) || !isLBAValid(endLBA)) {
            throw new RuntimeException("INVALID COMMAND PARMETER");
        }

        if(beginLBA > endLBA) {
            int tempLBA = beginLBA;
            beginLBA = endLBA;
            endLBA = tempLBA;
        }

        testShellManager.eraseRange(beginLBA, endLBA);
    }

    private boolean isLBAValid(int LBA) {
        return LBA >= 0 && LBA < 100;
    }
}
