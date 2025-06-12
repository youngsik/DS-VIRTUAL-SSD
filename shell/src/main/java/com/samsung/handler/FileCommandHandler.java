package com.samsung.handler;

import com.samsung.command.CommandInvoker;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileCommandHandler {

    private final CommandInvoker commandInvoker;

    public FileCommandHandler(CommandInvoker commandInvoker) {
        this.commandInvoker = commandInvoker;
    }

    public void handle(String fileName) {
        validatePrecondition(fileName);
        try {
            executeAllCommands(Files.readAllLines(Paths.get(fileName)));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static void validatePrecondition(String fileName) {
        if (!fileName.endsWith(".txt")) {
            throw new RuntimeException("유효하지 않은 파일 타입입니다.");
        }
    }

    private void executeAllCommands(List<String> commandNames) {
        for (String commandName : commandNames) {
            executeCommand(commandName);
        }
    }

    private void executeCommand(String commandName) {
        String[] cmdArgs = new String[]{commandName};

        System.out.print(commandName + "  ---  Run... ");
        try {
            commandInvoker.execute(cmdArgs);
            System.out.println("PASS");
        } catch (RuntimeException e) {
            System.out.println("FAIL!");
            throw new RuntimeException("FileCommandHandler Fail 발생");
        }
    }
}
