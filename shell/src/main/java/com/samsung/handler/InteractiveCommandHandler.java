package com.samsung.handler;

import com.samsung.command.CommandInvoker;

import java.util.Scanner;

public class InteractiveCommandHandler {

    private final CommandInvoker commandInvoker;

    public InteractiveCommandHandler(CommandInvoker commandInvoker) {
        this.commandInvoker = commandInvoker;
    }

    public void handle() {
        while (true) {
            try {
                String[] cmdArgs = getInput().split(" ");
                commandInvoker.execute(cmdArgs);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }
}
