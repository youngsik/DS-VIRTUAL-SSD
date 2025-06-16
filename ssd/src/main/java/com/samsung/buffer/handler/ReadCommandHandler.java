package com.samsung.buffer.handler;


import com.samsung.common.CmdData;

import java.util.Map;

import static com.samsung.common.SSDConstants.EMPTY_VALUE;

public class ReadCommandHandler implements CommandHandler {
    private final Map<Integer, String> memory;

    public ReadCommandHandler(Map<Integer, String> memory) {
        this.memory = memory;
    }

    @Override
    public String handle(CmdData cmd) {
        return memory.getOrDefault(cmd.getLba(), EMPTY_VALUE);
    }
}
