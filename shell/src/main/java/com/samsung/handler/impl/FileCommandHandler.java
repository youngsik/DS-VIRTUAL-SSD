package com.samsung.handler.impl;

import com.samsung.command.CommandInvoker;
import com.samsung.handler.CommandHandler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileCommandHandler implements CommandHandler {

    public static final int FILE_NAME_INDEX = 0;

    private final CommandInvoker commandInvoker;

    public FileCommandHandler(CommandInvoker commandInvoker) {
        this.commandInvoker = commandInvoker;
    }

    @Override
    public void handle(String... args) {
        try {
            String fileName = args[FILE_NAME_INDEX];
            executeAllCommands(Files.readAllLines(Paths.get(fileName)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void executeAllCommands(List<String> commandNames) {
        for (String commandName : commandNames) {
            executeCommand(commandName);
        }
    }

    private void executeCommand(String commandName) {
        System.out.print(commandName + "  ---  Run... ");
        String[] cmdArgs = { commandName };
        commandInvoker.execute(cmdArgs);
    }
}
