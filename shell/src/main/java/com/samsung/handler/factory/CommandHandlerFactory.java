package com.samsung.handler.factory;

import com.samsung.command.CommandInvoker;
import com.samsung.handler.CommandHandler;
import com.samsung.handler.impl.FileCommandHandler;
import com.samsung.handler.impl.InteractiveCommandHandler;

import java.util.HashMap;

public class CommandHandlerFactory {

    public static CommandHandler getCommandHandler(String[] args) {
        CommandInvoker invoker = new CommandInvoker(new HashMap<>());

        if (hasArguments(args)) return new FileCommandHandler(invoker);
        return new InteractiveCommandHandler(invoker);
    }

    private static boolean hasArguments(String[] mainArgs) {
        return mainArgs.length != 0;
    }

}
