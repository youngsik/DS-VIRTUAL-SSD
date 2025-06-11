package testscript;

import main.SsdApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScriptManagerTest {
    public static final String DUMMY_VALUE = "0xAAAABBBB";
    @Mock
    SsdApplication ssdApplication;
    @InjectMocks
    ScriptManager scriptManager;

    @Test
    void createSscScriptManager(){
        assertNotNull(scriptManager);
    }

    @Test
    void callSsdApplicationReadTest(){
        String expected = DUMMY_VALUE;
        when(ssdApplication.execute("R 1")).thenReturn(expected);

        String result = scriptManager.read(1);

        verify(ssdApplication).execute("R 1");
        assertEquals(expected, result);
    }

    @Test
    void callSsdApplicationWriteTest(){
        scriptManager.write(1, DUMMY_VALUE);

        verify(ssdApplication).execute("W 1 0xAAAABBBB");
    }

    @Test
    void callSsdApplicationReadAndCompareTest(){
        when(ssdApplication.execute("R 1")).thenReturn(DUMMY_VALUE);

        boolean result = scriptManager.readAndCompare(1, DUMMY_VALUE);

        verify(ssdApplication).execute("R 1");
        assertTrue(result);
    }
}
