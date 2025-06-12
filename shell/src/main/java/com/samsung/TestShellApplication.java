package com.samsung;

import com.samsung.command.CommandInvoker;
import com.samsung.command.testscript.*;
import com.samsung.command.testshell.*;
import com.samsung.file.FileManager;
import com.samsung.file.JarExecutor;
import com.samsung.validator.ArgumentsValidator;
import com.samsung.validator.CommandValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class TestShellApplication {
    public static void main(String[] args) {
        CommandInvoker commandInvoker = new CommandInvoker();

        // 명령어 등록
        initShellCommand(commandInvoker);
        initScriptCommand(commandInvoker);

        // 사용자 입력 받기
        System.out.println("testshell 명령어: write, read, exit, help, fullwrite, fullread");
        System.out.println("testscript 명령어: 1_FullWriteAndReadCompare, 2_PartialLBAWrite, 3_WriteReadAging");

        while (true) {
            try {
                String[] cmdArgs = split(getInput());

                // 명령어 파라미터 길이 필수 요건 체크
                ArgumentsValidator.validateArgsRequirements(cmdArgs);

                String commandName = cmdArgs[0];
                CommandValidator.validateNull(commandName);

                if (commandName.contains("_")) {
                    commandInvoker.execute(cmdArgs);
                } else {
                    commandInvoker.execute(cmdArgs);
                }
            } catch (Exception e) {
                log.info(e.getMessage());
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
    }

    private static void initScriptCommand(CommandInvoker commandInvoker) {
        ScriptManager scriptManager = new ScriptManager(new FileManager(), new JarExecutor());

        TestScript1Command testScript1Command = new TestScript1Command(scriptManager);
        TestScript2Command testScript2Command = new TestScript2Command(scriptManager);
        TestScript3Command testScript3Command = new TestScript3Command(scriptManager);
        TestScript4Command testScript4Command = new TestScript4Command(scriptManager);

        commandInvoker.register("1_FullWriteAndReadCompare", testScript1Command);
        commandInvoker.register("2_PartialLBAWrite", testScript2Command);
        commandInvoker.register("3_WriteReadAging", testScript3Command);
        commandInvoker.register("4_EraseAndWriteAging", testScript4Command);

        commandInvoker.register("1_", testScript1Command);
        commandInvoker.register("2_", testScript2Command);
        commandInvoker.register("3_", testScript3Command);
        commandInvoker.register("4_", testScript4Command);
    }

    private static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    private static String[] split(String input) {
        return input.split(" ");
    }
}
