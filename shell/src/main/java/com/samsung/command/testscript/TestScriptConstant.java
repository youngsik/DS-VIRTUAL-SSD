package com.samsung.command.testscript;

import java.util.List;

public class TestScriptConstant {
    public static final String TEST_VALUE = "0xAAAABBBB";
    public static final String TEST_VALUE_OVERWRITE = "0xAAAAAAAA";
    public static final String EMPTY_VALUE = "0x00000000";

    public static final int LOOP_30 = 30;
    public static final int LOOP_100 = 100;
    public static final int VERIFY_TERM = 4;

    public static final int MAX_RAND_BOUND = 1000;
    public static final int LBA_FIRST = 0;
    public static final int LBA_LAST = 99;
    public static final int ERASE_BLOCK_LENGTH = 3;

    public static final List<Integer> script2LbaOrder = List.of(4, 0, 3, 1, 2);
}
