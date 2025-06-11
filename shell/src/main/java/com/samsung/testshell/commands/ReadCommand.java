package com.samsung.testshell.commands;

import com.samsung.testshell.Command;
import com.samsung.testshell.TestShellManager;

public class ReadCommand implements Command {

    private final TestShellManager testShellManager;

    public ReadCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        // 명령어 길이 체크
        if (cmdArgs.length != 2) {
            throw new RuntimeException("INVALID COMMAND");
        }

        try {
            Integer index = Integer.parseInt(cmdArgs[1]);

            // null 체크?

            // lba 범위 검증
            if (index < 0 || index > 99) {
                throw new RuntimeException("INVALID COMMAND");
            }

            testShellManager.read(index);
        } catch (NumberFormatException e) {
            throw new RuntimeException("INVALID COMMAND"); // 숫자 아니면 Exception
        }
    }
}
