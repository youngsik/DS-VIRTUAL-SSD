package com.samsung.buffer;


import com.samsung.ssd.CmdData;
import com.samsung.ssd.CommandType;
import com.samsung.buffer.handler.CommandHandler;
import com.samsung.buffer.handler.EraseCommandHandler;
import com.samsung.buffer.handler.ReadCommandHandler;
import com.samsung.buffer.handler.WriteCommandHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.samsung.ssd.CommandType.*;

@Slf4j
public class BufferProcessor {
    @Getter
    private final List<CmdData> buffer = new ArrayList<>();
    private final Map<Integer, String> memory = new HashMap<>();
    private final Map<CommandType, CommandHandler> commandHandlerMap = new HashMap<>();

    public BufferProcessor(){
        commandHandlerMap.put(WRITE, new WriteCommandHandler(buffer, memory));
        commandHandlerMap.put(READ, new ReadCommandHandler(memory));
        commandHandlerMap.put(ERASE, new EraseCommandHandler(buffer, memory));
    }

    public String process(CmdData cmd) {
        CommandHandler handler = commandHandlerMap.get(cmd.getCommand());
        if (handler == null) {
            log.error("Buffer 내 이상 데이터 발견: {}", cmd.getCommand());
        }
        return handler.handle(cmd);
    }

    public void clear(){
        buffer.clear();
        memory.clear();
    }
}
