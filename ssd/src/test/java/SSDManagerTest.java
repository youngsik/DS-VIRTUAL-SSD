import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SSDManagerTest {
    public static final int LBA_LOCATION = 0;
    public static final int LBA_WRITE_LOCATION = 0;
    public static final String WRITE_VALUE = "0x0000000000";

    @Mock
    FileManager fileManager;

    SSDManager ssdManager = new SSDManager(null, 0, null);

    @Test
    public void readTest() {
        ssdManager.fileRead(LBA_LOCATION);
        Mockito.verify(fileManager, Mockito.times(1)).read();
    }

    @Test
    public void writeTest() {
        ssdManager.fileWrite(LBA_WRITE_LOCATION, WRITE_VALUE);
        Mockito.verify(fileManager, Mockito.times(1)).write(null);
    }

    @Test
    public void readTwiceTest() {
        ssdManager.fileRead(LBA_LOCATION);
        ssdManager.fileRead(LBA_LOCATION);
        Mockito.verify(fileManager, Mockito.times(2)).read();
    }
}
