package com.samsung.log;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogCompressorTest {
    @Mock
    File logFile1;
    @Mock
    File logFile2;

    @Test
    void testCompressIfNeeded_CompressesOldestLogFile() {
        when(logFile1.getName()).thenReturn("until_1.log");
        when(logFile1.lastModified()).thenReturn(1000L);
        when(logFile1.getAbsolutePath()).thenReturn("logs/until_1.log");

        when(logFile2.lastModified()).thenReturn(2000L);

        when(logFile1.renameTo(any(File.class))).thenReturn(true);

        LogCompressor compressor = new LogCompressor() {
            @Override
            protected File[] listLogFiles() {
                return new File[]{logFile2, logFile1};
            }
        };

        compressor.compressIfNeeded();

        verify(logFile1, times(1))
                .renameTo(argThat(f -> f.getName().endsWith(".zip")));
    }
}