package com.samsung;

import com.samsung.command.CommandInvoker;
import com.samsung.handler.CommandHandler;
import com.samsung.handler.FileCommandHandler;
import com.samsung.handler.InteractiveCommandHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class TestShellApplication {
    public static void main(String[] args) {
        try {
            if (args.length != 0 && args[0] != null) {
                CommandHandler fileCommandHandler = new FileCommandHandler(new CommandInvoker(new HashMap<>()));
                fileCommandHandler.handle(args[0]);
            } else {
                CommandHandler interactiveCommandHandler = new InteractiveCommandHandler(new CommandInvoker(new HashMap<>()));
                interactiveCommandHandler.handle();
            }
        } catch (Exception e) {

        }
    }
}
