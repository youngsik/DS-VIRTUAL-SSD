package com.samsung.handler.impl;

import com.samsung.command.CommandInvoker;
import com.samsung.handler.CommandHandler;

import java.util.Scanner;

public class InteractiveCommandHandler implements CommandHandler {

    private final CommandInvoker commandInvoker;

    public InteractiveCommandHandler(CommandInvoker commandInvoker) {
        this.commandInvoker = commandInvoker;
    }

    @Override
    public void handle(String... args) {
        while (true) {
            printPrompt();
            try {
                String[] cmdArgs = getInput().split(" ");
                commandInvoker.execute(cmdArgs);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printPrompt() {
        System.out.println();
        System.out.print("Shell > ");
    }

    private String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }
}
