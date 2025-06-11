package com.samsung.testshell.commands;

import com.samsung.testshell.ShellCommand;
import com.samsung.testshell.TestShellManager;

public class WriteCommand implements ShellCommand {

    private final TestShellManager testShellManager;

    public WriteCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        // 명령어 길이 체크
        if (cmdArgs.length != 3) {
            throw new RuntimeException("INVALID COMMAND");
        }

        try {
            Integer index = Integer.parseInt(cmdArgs[1]);
            String value = cmdArgs[2];

            // lba 범위 검증
            if (index < 0 || index > 99) {
                throw new RuntimeException("INVALID COMMAND");
            }

            // null 체크
            if (value == null) {
                throw new RuntimeException("INVALID COMMAND");
            }

            // value 정규표현식 검증
            if (!value.matches("^0x[0-9A-F]{8}$")) {
                throw new RuntimeException("INVALID COMMAND");
            }

            testShellManager.write(index, value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("INVALID COMMAND"); // 숫자 아니면 Exception
        }
    }
}
