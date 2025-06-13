package com.samsung.buffer.handler;


import com.samsung.CmdData;

public interface CommandHandler {
    String handle(CmdData cmd);
}