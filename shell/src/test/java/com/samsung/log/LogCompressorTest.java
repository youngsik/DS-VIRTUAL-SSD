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
        // Arrange
        when(logFile1.getName()).thenReturn("until_1.log");
        when(logFile1.lastModified()).thenReturn(1000L);
        when(logFile1.getAbsolutePath()).thenReturn("logs/until_1.log");

        when(logFile2.lastModified()).thenReturn(2000L);

        // 리네임 동작 설정
        when(logFile1.renameTo(any(File.class))).thenReturn(true);

        // 익명 서브클래스로 listLogFiles 오버라이드
        LogCompressor compressor = new LogCompressor() {
            @Override
            protected File[] listLogFiles() {
                return new File[]{logFile2, logFile1}; // 정렬 전 상태
            }
        };

        // Act
        compressor.compressIfNeeded();

        // Assert
        verify(logFile1, times(1))
                .renameTo(argThat(f -> f.getName().endsWith(".zip")));
    }
}