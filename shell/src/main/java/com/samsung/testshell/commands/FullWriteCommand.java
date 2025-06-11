package com.samsung.testshell.commands;

import com.samsung.testshell.ShellCommand;
import com.samsung.testshell.TestShellManager;

public class FullWriteCommand implements ShellCommand {

    private final TestShellManager testShellManager;

    public FullWriteCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void execute(String[] cmdArgs) {
        // 명령어 길이 체크
        if (cmdArgs.length != 2) {
            throw new RuntimeException("INVALID COMMAND");
        }

        String value = cmdArgs[1];

        // value 파라미터 null 체크
        if (value == null) {
            throw new RuntimeException("INVALID COMMAND");
        }

        // value 정규표현식 검증
        if (!value.matches("^0x[0-9A-F]{8}$")) {
            throw new RuntimeException("INVALID COMMAND");
        }

        testShellManager.fullwrite(value);
    }
}
