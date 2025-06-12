package com.samsung.command.testshell;

import com.samsung.command.Command;

public class EraseCommand implements Command {
    private final TestShellManager testShellManager;

    public EraseCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        if(cmdArgs.length != 3) {
            throw new RuntimeException("INVALID COMMAND PARAMETER");
        }

        int eraseLBA;
        int eraseSize;

        try{
            eraseLBA = Integer.parseInt(cmdArgs[1]);
            eraseSize = Integer.parseInt(cmdArgs[2]);
        }catch(NumberFormatException e) {
            throw new RuntimeException("INVALID ERASE START LOCATION");
        }

        if(eraseLBA < 0 || eraseLBA > 99) {
            throw new RuntimeException("INVALID ERASE START LOCATION");
        }

        testShellManager.erase(eraseLBA, eraseSize);
    }
}
