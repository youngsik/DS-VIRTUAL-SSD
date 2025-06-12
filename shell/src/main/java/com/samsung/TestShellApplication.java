package com.samsung;

import com.samsung.command.CommandInvoker;
import com.samsung.handler.FileCommandHandler;
import com.samsung.handler.InteractiveCommandHandler;

import java.util.HashMap;

public class TestShellApplication {
    public static void main(String[] args) {
        CommandInvoker commandInvoker = new CommandInvoker(new HashMap<>());
        commandInvoker.initAllCommands();

        try {
            if (args.length != 0 && args[0] != null) {
                FileCommandHandler fileCommandHandler = new FileCommandHandler(commandInvoker);
                fileCommandHandler.handle(args[0]);
            } else {
                InteractiveCommandHandler interactiveCommandHandler = new InteractiveCommandHandler(commandInvoker);
                interactiveCommandHandler.handle();
            }
        } catch (Exception e) {
            
        }
    }
}
