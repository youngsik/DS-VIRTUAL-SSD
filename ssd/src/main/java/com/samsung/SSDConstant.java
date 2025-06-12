package com.samsung;

public class SSDConstant {
    private SSDConstant() {}

    public static final String COMMAND_ERROR = "ERROR";
    public static final String COMMAND_READ = "R";
    public static final String COMMAND_WRITE = "W";
    public static final String COMMAND_ERASE = "E";
    public static final String ERASE_VALUE = "0x00000000";
    public static final int MAX_LBA = 99;
}
