package com.samsung;

public class CmdData {
    private CommandType command;
    private int lba;
    private String value;

    public CmdData(CommandType command, int lba, String value) {
        this.command = command;
        this.lba = lba;
        this.value = value;
    }

    public CommandType getCommand() {
        return command;
    }

    public void setCommand(CommandType command) {
        this.command = command;
    }

    public int getLba() {
        return lba;
    }

    public void setLba(int lba) {
        this.lba = lba;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
