package com.samsung;

import com.samsung.buffer.BufferProcessor;
import com.samsung.file.FileManager;
import com.samsung.resolver.CommandResolver;
import com.samsung.common.CmdData;
import com.samsung.ssd.SSDManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Main {
    public static void main(String[] args) {
        CmdData cmdData = getCmdData(args);
        if (cmdData == null) return;
        run(cmdData);
    }

    public static CmdData getCmdData(String[] args) {
        CmdData cmdData = null;
        try {
            CommandResolver commandResolver = new CommandResolver();
            cmdData = commandResolver.cmdValidCheckAndParsing(args);
        }catch (RuntimeException e){
            FileManager fileManager = FileManager.getInstance();
            fileManager.writeOnOutputFile("ERROR");
            log.info("[fileErrorOutput] Error output 생성");
        }
        return cmdData;
    }

    public static void run(CmdData cmdData) {
        com.samsung.ssd.SSDManager ssdManager = new SSDManager(cmdData, FileManager.getInstance(), new BufferProcessor());
        ssdManager.cmdExecuteFromBuffer();
    }

}
