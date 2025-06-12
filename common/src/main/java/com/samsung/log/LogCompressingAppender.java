package com.samsung.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class LogCompressingAppender extends AppenderBase<ILoggingEvent> {
    private final LogCompressor logCompressor = new LogCompressor();

    @Override
    protected void append(ILoggingEvent event) {
        logCompressor.compressIfNeeded();
    }
}