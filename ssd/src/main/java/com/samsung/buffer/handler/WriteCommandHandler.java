package com.samsung.buffer.handler;

import com.samsung.ssd.CmdData;
import com.samsung.ssd.CommandType;

import java.util.List;
import java.util.Map;

import static com.samsung.ssd.CommandType.WRITE;

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
        buffer.removeIf(c -> c.getCommand() == CommandType.ERASE && isLbaInEraseRange(cmd.getLba(), c));
        buffer.add(cmd);
        return "void";
    }

    private boolean isLbaInEraseRange(int lba, CmdData eraseCmd) {
        int eraseStart = eraseCmd.getLba();
        int eraseLen = Integer.parseInt(eraseCmd.getValue());
        int eraseEnd = eraseStart + eraseLen - 1;
        return lba >= eraseStart && lba <= eraseEnd;
    }
}