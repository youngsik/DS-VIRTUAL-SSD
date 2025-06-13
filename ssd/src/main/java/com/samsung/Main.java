package com.samsung;

import com.samsung.file.FileManager;
import com.samsung.file.FileManagerInterface;
import com.samsung.validator.CmdValidChecker;

class Main {
    public static void main(String[] args) {
        CmdData cmdData = cmdValidCheck(args);
        run(cmdData);
    }

    public static CmdData cmdValidCheck(String[] args) {
        CmdValidChecker cmdValidChecker = new CmdValidChecker();
        return cmdValidChecker.cmdValidCheckAndParsing(args);
    }

    public static void run(CmdData cmdData) {
        SSDManager ssdManager = new SSDManager(cmdData, FileManager.getInstance());
        ssdManager.cmdExecute();
    }

}
