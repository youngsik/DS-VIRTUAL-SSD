import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class SSDManagerTest {
    public static final int LBA_LOCATION = 0;
    public static final int LBA_WRITE_LOCATION = 0;
    public static final String WRITE_VALUE = "0x0000000000";

    @Mock
    FileManager fileManager;

    @InjectMocks
    SSDManager ssdManager = new SSDManager("W", LBA_WRITE_LOCATION, WRITE_VALUE);

    @Test
    public void readTest() {
        ssdManager.fileRead(LBA_LOCATION);
        verify(fileManager, times(1)).readFile(LBA_WRITE_LOCATION);
    }

    @Test
    public void writeTest() {
        ssdManager.fileWrite(LBA_WRITE_LOCATION, WRITE_VALUE);
        verify(fileManager, times(1)).writeFile(LBA_WRITE_LOCATION, WRITE_VALUE);
    }

    @Test
    public void readTwiceTest() {
        ssdManager.fileRead(LBA_LOCATION);
        ssdManager.fileRead(LBA_LOCATION);
        verify(fileManager, times(2)).readFile(LBA_WRITE_LOCATION);
    }

}
