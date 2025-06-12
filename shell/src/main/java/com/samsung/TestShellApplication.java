package com.samsung;

import com.samsung.command.CommandInvoker;
import com.samsung.handler.InteractiveCommandHandler;

import java.util.HashMap;

public class TestShellApplication {
    public static void main(String[] args) {
        CommandInvoker commandInvoker = new CommandInvoker(new HashMap<>());
        commandInvoker.initAllCommands();

        InteractiveCommandHandler handler = new InteractiveCommandHandler(commandInvoker);
        handler.handle();
    }
}
