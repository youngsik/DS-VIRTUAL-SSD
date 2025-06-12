package com.samsung;

public class CmdData {
    private String command;
    private int lba;
    private String value;

    public CmdData(String command, int lba, String value) {
        this.command = command;
        this.lba = lba;
        this.value = value;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
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
