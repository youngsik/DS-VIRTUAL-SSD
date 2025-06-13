package com.samsung.command;

import com.samsung.command.testscript.*;
import com.samsung.command.testshell.*;
import com.samsung.file.FileManager;
import com.samsung.file.JarExecutor;

import java.util.Map;

public class CommandRegistry {

    private final Map<String, Command> commandMap;

    public CommandRegistry(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
        initShellCommand();
        initScriptCommand();
    }

    public Command getCommand(String name) {
        return commandMap.get(name);
    }

    private void initShellCommand() {
        TestShellManager testShellManager = new TestShellManager(new JarExecutor(), FileManager.getInstance());
        register("write", new WriteCommand(testShellManager));
        register("read", new ReadCommand(testShellManager));
        register("erase", new EraseCommand(testShellManager));
        register("erase_range", new EraseRangeCommand(testShellManager));
        register("exit", new ExitCommand(testShellManager));
        register("help", new HelpCommand(testShellManager));
        register("fullwrite", new FullWriteCommand(testShellManager));
        register("fullread", new FullReadCommand(testShellManager));
        register("flush", new FlushCommand(testShellManager));
    }

    private void initScriptCommand() {
        ScriptManager scriptManager = new ScriptManager(FileManager.getInstance(), new JarExecutor(), RandomHex.getInstance());

        String[] script1CommandNames = {"1_FullWriteAndReadCompare", "1_"};
        String[] script2CommandNames = {"2_PartialLBAWrite", "2_"};
        String[] script3CommandNames = {"3_WriteReadAging", "3_"};
        String[] script4CommandNames = {"4_EraseAndWriteAging", "4_"};

        register(script1CommandNames, new TestScript1Command(scriptManager));
        register(script2CommandNames, new TestScript2Command(scriptManager));
        register(script3CommandNames, new TestScript3Command(scriptManager));
        register(script4CommandNames, new TestScript4Command(scriptManager));
    }

    private void register(String name, Command command) {
        commandMap.put(name, command);
    }

    private void register(String[] names, Command command) {
        for (String name : names) {
            register(name, command);
        }
    }
}
