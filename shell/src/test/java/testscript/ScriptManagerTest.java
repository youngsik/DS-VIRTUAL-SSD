package testscript;

import com.samsung.testscript.ScriptManager;
import main.SsdApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScriptManagerTest {
    public static final String DUMMY_VALUE = "0xAAAABBBB";
    public static final int SCRIPT1_LOOP = 100;
    public static final int SCRIPT1_TERM = 4;
    @Mock
    SsdApplication ssdApplication;
    @InjectMocks
    ScriptManager scriptManager;

    @Test
    void createSscScriptManager(){
        assertNotNull(scriptManager);
    }

    @Test
    void testScript1_shouldWriteAndReadCorrectly() {
        for (int i = 0; i <= SCRIPT1_LOOP - SCRIPT1_TERM; i += SCRIPT1_TERM) {
            for (int j = i; j < i + SCRIPT1_TERM; j++) {
                when(ssdApplication.execute(getReadCommand(j))).thenReturn(DUMMY_VALUE);
                when(ssdApplication.execute(getWriteCommand(j))).thenReturn("OK");
            }
        }

        scriptManager.testScript1();

        for (int i = 0; i < SCRIPT1_LOOP; i++) {
            verify(ssdApplication).execute(getWriteCommand(i));
        }
        for (int i = 0; i < SCRIPT1_LOOP; i++) {
            verify(ssdApplication).execute(getReadCommand(i));
        }

        verify(ssdApplication, times(200)).execute(anyString());
    }

    private String getReadCommand(int j) {
        return "R " + j;
    }

    private String getWriteCommand(int i) {
        return "W " + i + " " + DUMMY_VALUE;
    }
}
