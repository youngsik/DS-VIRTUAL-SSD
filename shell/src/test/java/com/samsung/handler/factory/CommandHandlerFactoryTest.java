package com.samsung.handler.factory;

import com.samsung.handler.CommandHandler;
import com.samsung.handler.impl.FileCommandHandler;
import com.samsung.handler.impl.InteractiveCommandHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommandHandlerFactoryTest {

    public static final String[] EMPTY_ARGS = {};
    public static final String[] NOT_EMPTY_ARGS = {"scripts.txt"};

    @DisplayName("인자 값이 존재하면 FileCommandHandler를 반환한다")
    @Test
    void returnFileCommandHandler_WhenArgExists() {
        CommandHandler handler = CommandHandlerFactory.getCommandHandler(NOT_EMPTY_ARGS);
        assertThat(handler).isInstanceOf(FileCommandHandler.class);
    }

    @DisplayName("인자 값이 존재하지 않으면 InteractiveCommandHandler를 반환한다")
    @Test
    void returnFileCommandHandler_WhenArgNotExists() {
        CommandHandler handler = CommandHandlerFactory.getCommandHandler(EMPTY_ARGS);
        assertThat(handler).isInstanceOf(InteractiveCommandHandler.class);
    }
}