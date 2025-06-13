package com.samsung;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CmdData {
    private CommandType command;
    private int lba;
    private String value;

    public CmdData(CommandType command, int lba, String value) {
        this.command = command;
        this.lba = lba;
        this.value = value;
    }
}
