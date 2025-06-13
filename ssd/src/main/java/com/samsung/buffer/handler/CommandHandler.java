package com.samsung.buffer.handler;


import com.samsung.common.CmdData;

public interface CommandHandler {
    String handle(CmdData cmd);
}