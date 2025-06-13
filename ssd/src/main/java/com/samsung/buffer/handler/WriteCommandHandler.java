package com.samsung.buffer.handler;

import com.samsung.CmdData;

import java.util.List;
import java.util.Map;

import static com.samsung.CommandType.WRITE;

public class WriteCommandHandler implements CommandHandler {
    private final List<CmdData> buffer;
    private final Map<Integer, String> memory;

    public WriteCommandHandler(List<CmdData> buffer, Map<Integer, String> memory) {
        this.buffer = buffer;
        this.memory = memory;
    }

    @Override
    public String handle(CmdData cmd) {
        memory.put(cmd.getLba(), cmd.getValue());
        buffer.removeIf(c -> c.getCommand() == WRITE && c.getLba() == cmd.getLba());
        buffer.add(cmd);
        return "void";
    }
}