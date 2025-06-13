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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileCommandHandlerTest {

    public static final String VALID_FILE_NAME = "valid.txt";

    public static final String TEST_SCRIPT1_COMMAND = "1_FullWriteAndReadCompare";
    public static final String TEST_SCRIPT2_COMMAND = "2_PartialLBAWrite";
    public static final String TEST_SCRIPT3_COMMAND = "3_WriteReadAging";
    public static final String TEST_SCRIPT4_COMMAND = "4_EraseAndWriteAging";

    @Mock
    private CommandInvoker mockInvoker;

    @InjectMocks
    private FileCommandHandler handler;

    @DisplayName("handle() 메서드 실행 테스트 - 유효한 txt 파일")
    @Test
    void handleExecutionTest(@TempDir Path tempDir) {
        Path txtFile = writeTempFile(tempDir);

        handler.handle(txtFile.toString());

        verify(mockInvoker, times(4)).execute(any());
    }

    @DisplayName("handle() 메서드 예외 테스트 - 세 번째 실행 스크립트에서 예외 발생")
    @Test
    void handleIOExceptionTest2(@TempDir Path tempDir) {
        Path txtFile = writeTempFile(tempDir);

        doNothing().when(mockInvoker).execute(eq(new String[] {TEST_SCRIPT1_COMMAND}));
        doNothing().when(mockInvoker).execute(eq(new String[] {TEST_SCRIPT2_COMMAND}));
        doThrow(new RuntimeException()).when(mockInvoker).execute(eq(new String[] {TEST_SCRIPT3_COMMAND}));

        try {
            handler.handle(txtFile.toString());
        } catch (RuntimeException e) {
            verify(mockInvoker, times(3)).execute(any());
            assertThat(e.getMessage()).isEqualTo("FAIL!");
        }
    }

    private Path writeTempFile(Path tempDir) {
        Path txtFile = tempDir.resolve(VALID_FILE_NAME);
        try (PrintWriter writer = new PrintWriter(txtFile.toFile())) {
            writer.println(TEST_SCRIPT1_COMMAND);
            writer.println(TEST_SCRIPT2_COMMAND);
            writer.println(TEST_SCRIPT3_COMMAND);
            writer.println(TEST_SCRIPT4_COMMAND);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return txtFile;
    }
}