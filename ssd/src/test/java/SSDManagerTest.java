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
    public static final int LBA_LOCATION = 0;
    public static final String WRITE_VALUE = "0x00000000";

    @Mock
    FileManager fileManager;

    @InjectMocks
    SSDManager writeSsdManager = new SSDManager("W", LBA_LOCATION, WRITE_VALUE);

    @InjectMocks
    SSDManager readSsdManager = new SSDManager("R", LBA_LOCATION, WRITE_VALUE);

    @InjectMocks
    SSDManager errorSsdManager = new SSDManager("w", LBA_LOCATION, WRITE_VALUE);

    @Test
    @DisplayName("읽기 테스트")
    public void readTest() {
        readSsdManager.fileRead(LBA_LOCATION);
        verify(fileManager, times(1)).readFile(LBA_LOCATION);
    }

    @Test
    @DisplayName("쓰기 테스트")
    public void writeTest() {
        writeSsdManager.fileWrite(LBA_LOCATION, WRITE_VALUE);
        verify(fileManager, times(1)).writeFile(LBA_LOCATION, WRITE_VALUE);
    }

    @Test
    @DisplayName("읽기 2번 테스트")
    public void readTwiceTest() {
        readSsdManager.fileRead(LBA_LOCATION);
        readSsdManager.fileRead(LBA_LOCATION);
        verify(fileManager, times(2)).readFile(LBA_LOCATION);
    }

    @Test
    @DisplayName("읽기 명령어 테스트(성공)")
    void cmdExecuteReadPass() {
        readSsdManager.cmdExecute();
        verify(fileManager, times(1)).readFile(LBA_LOCATION);
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
        verify(fileManager, times(1)).writeFile(LBA_LOCATION, WRITE_VALUE);
    }

    @Test
    @DisplayName("쓰기 명령어 테스트(실패)")
    void cmdExecuteWriteFail() {
        SSDManager newSsdManager = new SSDManager("w", LBA_LOCATION, WRITE_VALUE);
        assertThrows(RuntimeException.class, () -> {
            newSsdManager.cmdExecute();
        });
    }
}
