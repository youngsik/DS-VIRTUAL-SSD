package testscript;

import main.SsdApplication;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ScriptManagerTest {
    @Mock
    SsdApplication ssdApplication;

    @Test
    void createSscScriptManager(){
        ScriptManager scriptManager = new ScriptManager(ssdApplication);

        assertNotNull(scriptManager);
    }

    @Test
    void callSsdApplicationReadTest(){
        String expected = "0xAAAABBBB";
        ScriptManager scriptManager = new ScriptManager(ssdApplication);
        when(ssdApplication.execute("R 1")).thenReturn(expected);

        String result = scriptManager.read(1);

        verify(ssdApplication).execute("R 1");
        assertEquals(expected, result);
    }

    @Test
    void callSsdApplicationWriteTest(){
        ScriptManager scriptManager = new ScriptManager(ssdApplication);

        scriptManager.write(1, "0xAAAABBBB");

        verify(ssdApplication).execute("W 1 0xAAAABBBB");
    }

    @Test
    void callSsdApplicationReadAndCompareTest(){
        String expected = "0xAAAABBBB";
        ScriptManager scriptManager = new ScriptManager(ssdApplication);
        when(ssdApplication.execute("R 1")).thenReturn(expected);

        boolean result = scriptManager.readAndCompare(1, "0xAAAABBBB");

        verify(ssdApplication).execute("R 1");
        assertTrue(result);
    }
}
