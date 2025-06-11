package com.samsung.dto;

public class InputDto {
    private String commandName;
    private Integer index;
    private String value;

    public InputDto(String commandName, Integer index, String value) {
        this.commandName = commandName;
        this.index = index;
        this.value = value;
    }

    public String getCommandName() {
        return commandName;
    }

    public Integer getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }
}
