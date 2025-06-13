package com.samsung.buffer.handler;

import com.samsung.common.CmdData;
import com.samsung.common.CommandType;

import java.util.List;
import java.util.Map;

import static com.samsung.common.CommandType.WRITE;


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
        buffer.removeIf(c ->
                c.getCommand() == CommandType.ERASE
                        && isEraseSingleOverlapWithWrite(c, cmd)
        );
        buffer.add(cmd);
        return "void";
    }

    private boolean isEraseSingleOverlapWithWrite(CmdData erase, CmdData write) {
        int eStart = erase.getLba();
        int eLen = Integer.parseInt(erase.getValue());
        int wLba = write.getLba();

        return eStart == wLba && eLen == 1;
    }
}