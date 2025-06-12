package com.samsung.handler;

import com.samsung.command.CommandInvoker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileCommandHandlerTest {

    @Mock
    private CommandInvoker mockInvoker;

    @InjectMocks
    private FileCommandHandler handler;

    @DisplayName("handle() 메서드 실행 테스트 - 유효한 txt 파일")
    @Test
    void handleExecutionTest(@TempDir Path tempDir) throws FileNotFoundException {
        Path txtFile = tempDir.resolve("valid.txt");
        try (PrintWriter writer = new PrintWriter(txtFile.toFile())) {
            writer.println("1_FullWriteAndReadCompare");
            writer.println("2_PartialLBAWrite");
            writer.println("3_WriteReadAging");
            writer.println("4_EraseAndWriteAging");
        }
        String validFileName = txtFile.toString();

        handler.handle(validFileName);

        verify(mockInvoker, times(4)).execute(any());
    }

    @DisplayName("handle() 메서드 예외 테스트 - 유효하지 않은 txt 파일")
    @Test
    void handleIOExceptionTest() {
        String invalidFileName = "QWERTYsdfDsFGHZXCSsdfN.txt";
        assertThrows(RuntimeException.class, () -> handler.handle(invalidFileName));
    }

    @DisplayName("handle() 메서드 예외 테스트 - 세 번째 실행 스크립트에서 예외 발생")
    @Test
    void handleIOExceptionTest2(@TempDir Path tempDir) throws FileNotFoundException {
        Path txtFile = tempDir.resolve("valid.txt");
        try (PrintWriter writer = new PrintWriter(txtFile.toFile())) {
            writer.println("1_FullWriteAndReadCompare");
            writer.println("2_PartialLBAWrite");
            writer.println("3_WriteReadAging");
            writer.println("4_EraseAndWriteAging");
        }
        String validFileName = txtFile.toString();

        doNothing().when(mockInvoker).execute(eq(new String[] { "1_FullWriteAndReadCompare" }));
        doNothing().when(mockInvoker).execute(eq(new String[] { "2_PartialLBAWrite" }));
        doThrow(new RuntimeException()).when(mockInvoker).execute(eq(new String[]{ "3_WriteReadAging" }));

        try {
            handler.handle(validFileName);
        } catch (RuntimeException e) {
            verify(mockInvoker, times(3)).execute(any());
            assertThat(e.getMessage()).isEqualTo("FileCommandHandler Fail 발생");
        }
    }
}