package com.samsung.command;

public class CommandInvoker {

    private static final int COMMAND_NAME_INDEX = 0;

    private final CommandRegistry registry;

    public CommandInvoker(CommandRegistry registry) {
        this.registry = registry;
    }

    public void execute(String[] cmdArgs) {
        Command command = registry.getCommand(cmdArgs[COMMAND_NAME_INDEX]);
        validateCommandNotNull(command);

        command.execute(cmdArgs);
    }

    private void validateCommandNotNull(Command command) {
        if (command == null) {
            throw new RuntimeException("INVALID COMMAND");
        }
    }

}
