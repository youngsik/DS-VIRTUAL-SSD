package com.samsung.buffer.handler;


import com.samsung.ssd.CmdData;

public interface CommandHandler {
    String handle(CmdData cmd);
}