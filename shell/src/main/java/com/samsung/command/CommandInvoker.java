package com.samsung.command;

import com.samsung.file.FileManager;
import com.samsung.command.testscript.*;
import com.samsung.command.testshell.*;
import com.samsung.file.JarExecutor;

import java.util.Map;

public class CommandInvoker {

    private final Map<String, Command> commandMap;

    public CommandInvoker(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    public void initAllCommands() {
        initShellCommand();
        initScriptCommand();
    }

    public void execute(String[] cmdArgs) {
        Command command = getCommand(cmdArgs[0]);
        command.execute(cmdArgs);
    }

    private Command getCommand(String commandName) {
        Command command = commandMap.get(commandName);

        // 명령어 존재 여부 체크
        if (command == null) {
            throw new RuntimeException("INVALID COMMAND");
        }

        return command;
    }

    private void initShellCommand() {
        TestShellManager testShellManager = new TestShellManager(new JarExecutor(), new FileManager());
        register("write", new WriteCommand(testShellManager));
        register("read", new ReadCommand(testShellManager));
        register("erase", new EraseCommand(testShellManager));
        register("erase_range", new EraseRangeCommand(testShellManager));
        register("exit", new ExitCommand(testShellManager));
        register("help", new HelpCommand(testShellManager));
        register("fullwrite", new FullWriteCommand(testShellManager));
        register("fullread", new FullReadCommand(testShellManager));
    }

    private void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    private void initScriptCommand() {
        ScriptManager scriptManager = new ScriptManager(new FileManager(), new JarExecutor(), RandomHex.getInstance());

        String[] script1CommandNames = {"1_FullWriteAndReadCompare", "1_"};
        String[] script2CommandNames = {"2_PartialLBAWrite", "2_"};
        String[] script3CommandNames = {"3_WriteReadAging", "3_"};
        String[] script4CommandNames = {"4_EraseAndWriteAging", "4_"};

        register(script1CommandNames, new TestScript1Command(scriptManager));
        register(script2CommandNames, new TestScript2Command(scriptManager));
        register(script3CommandNames, new TestScript3Command(scriptManager));
        register(script4CommandNames, new TestScript4Command(scriptManager));
    }

    private void register(String[] commandNames, Command command) {
        for (String commandName : commandNames) {
            commandMap.put(commandName, command);
        }
    }
}
