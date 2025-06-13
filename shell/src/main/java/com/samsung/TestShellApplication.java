package com.samsung;

import com.samsung.handler.CommandHandler;
import com.samsung.handler.factory.CommandHandlerFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestShellApplication {
    public static void main(String[] args) {
        CommandHandler handler = CommandHandlerFactory.getCommandHandler(args);
        handler.handle(args);
    }
}
