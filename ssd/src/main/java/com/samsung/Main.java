package com.samsung;

import com.samsung.file.FileManager;
import com.samsung.file.FileManagerInterface;
import com.samsung.validator.CmdValidChecker;

class Main {
    public static void main(String[] args) {
        CmdData cmdData = getCmdData(args);
        if (cmdData == null) return;
        run(cmdData);
    }

    public static CmdData getCmdData(String[] args) {
        CmdData cmdData = null;
        try {
            CmdValidChecker cmdValidChecker = new CmdValidChecker();
            cmdData = cmdValidChecker.cmdValidCheckAndParsing(args);
        }catch (RuntimeException e){
            FileManagerInterface fileManager = FileManager.getInstance();
            fileManager.writeOnOutputFile("ERROR");
        }
        return cmdData;
    }

    public static void run(CmdData cmdData) {
        SSDManager ssdManager = new SSDManager(cmdData, FileManager.getInstance());
        ssdManager.cmdExecute();
    }

}
