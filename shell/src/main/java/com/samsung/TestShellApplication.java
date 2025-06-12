package com.samsung;

import com.samsung.command.CommandInvoker;
import com.samsung.command.testscript.ScriptManager;
import com.samsung.command.testscript.TestScript1Command;
import com.samsung.command.testscript.TestScript2Command;
import com.samsung.command.testscript.TestScript3Command;
import com.samsung.command.testshell.*;
import com.samsung.file.FileManager;
import com.samsung.file.JarExecutor;

import java.util.Scanner;

public class TestShellApplication {
    public static void main(String[] args) {
        CommandInvoker commandInvoker = new CommandInvoker();

        // 명령어 등록
        initShellCommand(commandInvoker);
        initScriptCommand(commandInvoker);

        // 사용자 입력 받기
        System.out.println("testshell 명령어: write, read, erase, erase_range, exit, help, fullwrite, fullread");
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
                    commandInvoker.execute(cmdArgs);
                } else {
                    commandInvoker.execute(cmdArgs);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private static void initShellCommand(CommandInvoker commandInvoker) {
        TestShellManager testShellManager = new TestShellManager(new JarExecutor(), new FileManager());
        commandInvoker.register("write", new WriteCommand(testShellManager));
        commandInvoker.register("read", new ReadCommand(testShellManager));
        commandInvoker.register("exit", new ExitCommand(testShellManager));
        commandInvoker.register("help", new HelpCommand(testShellManager));
        commandInvoker.register("fullwrite", new FullWriteCommand(testShellManager));
        commandInvoker.register("fullread", new FullReadCommand(testShellManager));
        commandInvoker.register("erase", new EraseCommand(testShellManager));
        commandInvoker.register("erase_range", new EraseRangeCommand(testShellManager));
    }

    private static void initScriptCommand(CommandInvoker commandInvoker) {
        ScriptManager scriptManager = new ScriptManager(new FileManager(), new JarExecutor());
        commandInvoker.register("1_FullWriteAndReadCompare", new TestScript1Command(scriptManager));
        commandInvoker.register("2_PartialLBAWrite", new TestScript2Command(scriptManager));
        commandInvoker.register("3_WriteReadAging", new TestScript3Command(scriptManager));
    }

    private static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    private static String[] split(String input) {
        return input.split(" ");
    }
}
