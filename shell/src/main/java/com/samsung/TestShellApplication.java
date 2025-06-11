package com.samsung;

import com.samsung.file.FileManager;
import com.samsung.file.JarExecutor;
import com.samsung.testscript.ScriptCommandInvoker;
import com.samsung.testscript.ScriptManager;
import com.samsung.testscript.command.TestScript1Command;
import com.samsung.testscript.command.TestScript2Command;
import com.samsung.testscript.command.TestScript3Command;
import com.samsung.testshell.ShellCommandInvoker;
import com.samsung.testshell.TestShellManager;
import com.samsung.testshell.commands.*;

import java.util.Scanner;

public class TestShellApplication {
    public static void main(String[] args) {
        ShellCommandInvoker shellCommandInvoker = new ShellCommandInvoker();
        ScriptCommandInvoker scriptCommandInvoker = new ScriptCommandInvoker();

        // 명령어 등록
        initShellCommand(shellCommandInvoker);
        initScriptCommand(scriptCommandInvoker);

        // 사용자 입력 받기
        System.out.println("testshell 명령어: write, read, exit, help, fullwrite, fullread");
        System.out.println("testscript 명령어: 1_FullWriteAndReadCompare, 2_PartialLBAWrite, 3_WriteReadAging");

        while (true) {
            try {
                String[] cmdArgs = split(getInput()); // 1~3개 args가 들어옴을 보장

                // 명령어 파라미터 길이 필수 요건 체크
                if (cmdArgs.length < 1 || cmdArgs.length > 4) {
                    throw new RuntimeException("INVALID COMMAND");
                }

                String commandName = cmdArgs[0];

                // 명령어 파라미터 null 체크
                if (commandName == null) {
                    throw new RuntimeException("INVALID COMMAND");
                }

                if (commandName.contains("_")) {
                    scriptCommandInvoker.execute(cmdArgs);
                } else {
                    shellCommandInvoker.execute(cmdArgs);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private static void initShellCommand(ShellCommandInvoker shellCommandInvoker) {
        TestShellManager testShellManager = new TestShellManager(new JarExecutor(), new FileManager());
        shellCommandInvoker.register("write", new WriteCommand(testShellManager));
        shellCommandInvoker.register("read", new ReadCommand(testShellManager));
        shellCommandInvoker.register("exit", new ExitCommand(testShellManager));
        shellCommandInvoker.register("help", new HelpCommand(testShellManager));
        shellCommandInvoker.register("fullwrite", new FullWriteCommand(testShellManager));
        shellCommandInvoker.register("fullread", new FullReadCommand(testShellManager));
    }

    private static void initScriptCommand(ScriptCommandInvoker scriptCommandInvoker) {
        ScriptManager scriptManager = new ScriptManager(new FileManager(), new JarExecutor());
        scriptCommandInvoker.register("1_FullWriteAndReadCompare", new TestScript1Command(scriptManager));
        scriptCommandInvoker.register("2_PartialLBAWrite", new TestScript2Command(scriptManager));
        scriptCommandInvoker.register("3_WriteReadAging", new TestScript3Command(scriptManager));
    }

    private static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    private static String[] split(String input) {
        return input.split(" ");
    }
}
