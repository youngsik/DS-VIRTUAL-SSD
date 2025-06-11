import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class SSDManagerTest {
    public static final int LBA = 0;
    public static final String WRITE_VALUE = "0x00000000";

    @Mock
    FileManager fileManager;

    @InjectMocks
    SSDManager writeSsdManager = new SSDManager("W", LBA, WRITE_VALUE);

    @InjectMocks
    SSDManager readSsdManager = new SSDManager("R", LBA, WRITE_VALUE);

    @InjectMocks
    SSDManager errorSsdManager = new SSDManager("w", LBA, WRITE_VALUE);

    @Test
    @DisplayName("읽기 테스트")
    public void readTest() {
        readSsdManager.fileRead(LBA);
        verify(fileManager, times(1)).readFile(LBA);
    }

    @Test
    @DisplayName("쓰기 테스트")
    public void writeTest() {
        writeSsdManager.fileWrite(LBA, WRITE_VALUE);
        verify(fileManager, times(1)).writeFile(LBA, WRITE_VALUE);
    }

    @Test
    @DisplayName("읽기 2번 테스트")
    public void readTwiceTest() {
        readSsdManager.fileRead(LBA);
        readSsdManager.fileRead(LBA);
        verify(fileManager, times(2)).readFile(LBA);
    }

    @Test
    @DisplayName("읽기 명령어 테스트(성공)")
    void cmdExecuteReadPass() {
        readSsdManager.cmdExecute();
        verify(fileManager, times(1)).readFile(LBA);
    }

    @Test
    @DisplayName("읽기 명령어 테스트(실패)")
    void cmdExecuteReadFail() {
        assertThrows(RuntimeException.class, () -> {
            errorSsdManager.cmdExecute();
        });
    }

    @Test
    @DisplayName("쓰기 명령어 테스트(성공)")
    void cmdExecuteWritePass() {
        writeSsdManager.cmdExecute();
        verify(fileManager, times(1)).writeFile(LBA, WRITE_VALUE);
    }
}
