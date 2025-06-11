package com.samsung;

import com.samsung.dto.InputDto;
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
        System.out.println("'testshell 명령어: write, read, exit, help, fullwrite, fullread");
        System.out.println("testscript 명령어: 1_FullWriteAndReadCompare, 2_PartialLBAWrite, 3_WriteReadAging");

        while (true) {
            try {
                InputDto inputDto = parse(padToThree(split(getInput())));

                if (inputDto.getCommandName().contains("_")) {
                    scriptCommandInvoker.execute(inputDto.getCommandName());
                } else {
                    shellCommandInvoker.execute(inputDto.getCommandName(), inputDto.getIndex(), inputDto.getValue());
                }
            } catch (Exception e) {
                System.out.println("에러 발생: " + e.getMessage());
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
        scriptCommandInvoker.register("1_fullwriteandreadcompare", new TestScript1Command(scriptManager));
        scriptCommandInvoker.register("2_partiallbawrite", new TestScript2Command(scriptManager));
        scriptCommandInvoker.register("3_writereadaging", new TestScript3Command(scriptManager));
    }

    private static InputDto parse(String[] inputs) {
        String commandName = inputs[0];
        Integer index = inputs[1] == null ? null : Integer.valueOf(inputs[1]);
        String value = inputs[2];

        if (commandName == null) {
            throw new RuntimeException("명령어 입력은 필수입니다.");
        }
        return new InputDto(commandName, index, value);
    }

    private static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    private static String[] split(String input) {
        return input.split(" ");
    }

    private static String[] padToThree(String[] splitInput) {
        String[] result = new String[3]; // 입력 최대 3개
        for (int i = 0; i < 3; i++) {
            if (i < splitInput.length) {
                result[i] = splitInput[i];
            } else {
                result[i] = null;
            }
        }
        return result;
    }
}
