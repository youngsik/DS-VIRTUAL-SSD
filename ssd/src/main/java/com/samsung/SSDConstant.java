package com.samsung;

public class SSDConstant {
    private SSDConstant() {}

    public static final String COMMAND_ERROR = "ERROR";
    public static final String COMMAND_READ = "R";
    public static final String COMMAND_WRITE = "W";
    public static final String COMMAND_ERASE = "E";
    public static final String COMMAND_FLUSH = "F";
    public static final String ERASE_VALUE = "0x00000000";
    public static final int MAX_LBA = 99;
    public static final int MAX_ERASE_SIZE = 10;

    public static final int MIN_BUFFER_NUMBER = 1;
    public static final int MAX_BUFFER_NUMBER = 5;
}
