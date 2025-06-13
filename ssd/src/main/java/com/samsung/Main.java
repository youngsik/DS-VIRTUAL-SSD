package com.samsung;

import com.samsung.file.FileManager;
import com.samsung.resolver.CommandResolver;
import com.samsung.common.CmdData;

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
        }
        return cmdData;
    }

    public static void run(CmdData cmdData) {
        SSDManager ssdManager = new SSDManager(cmdData, FileManager.getInstance());
        ssdManager.cmdExecute();
    }

}
