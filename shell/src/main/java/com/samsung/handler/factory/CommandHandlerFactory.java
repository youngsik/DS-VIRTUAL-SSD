package com.samsung.handler.factory;

import com.samsung.command.CommandInvoker;
import com.samsung.command.CommandRegistry;
import com.samsung.handler.CommandHandler;
import com.samsung.handler.impl.FileCommandHandler;
import com.samsung.handler.impl.InteractiveCommandHandler;

import java.util.HashMap;

public class CommandHandlerFactory {

    public static CommandHandler getCommandHandler(String[] args) {
        if (hasArguments(args)){
            return new FileCommandHandler(createCommandInvoker());
        }
        return new InteractiveCommandHandler(createCommandInvoker());
    }

    private static boolean hasArguments(String[] mainArgs) {
        return mainArgs.length != 0;
    }

    private static CommandInvoker createCommandInvoker() {
        return new CommandInvoker(new CommandRegistry(new HashMap<>()));
    }

}
