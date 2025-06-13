package com.samsung.buffer;


import com.samsung.CmdData;
import com.samsung.buffer.handler.CommandHandler;
import com.samsung.buffer.handler.EraseCommandHandler;
import com.samsung.buffer.handler.ReadCommandHandler;
import com.samsung.buffer.handler.WriteCommandHandler;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BufferProcessor {
    @Getter
    private final List<CmdData> buffer = new ArrayList<>();
    private final Map<Integer, String> memory = new HashMap<>();

    private final CommandHandler writeHandler = new WriteCommandHandler(buffer, memory);
    private final CommandHandler eraseHandler = new EraseCommandHandler(buffer, memory);
    private final CommandHandler readHandler = new ReadCommandHandler(memory);

    public String process(CmdData cmd) {
        return switch (cmd.getCommand()) {
            case WRITE -> writeHandler.handle(cmd);
            case ERASE -> eraseHandler.handle(cmd);
            case READ -> readHandler.handle(cmd);
            default -> throw new IllegalArgumentException("Unknown command: " + cmd.getCommand());
        };
    }
}
