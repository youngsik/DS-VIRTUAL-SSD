package testscript;

import com.samsung.testscript.ScriptManager;
import main.SsdApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScriptManagerTest {
    public static final String DUMMY_VALUE = "0xAAAABBBB";
    public static final int SCRIPT1_LOOP = 100;
    public static final int SCRIPT1_TERM = 4;

    private final Map<Integer, String> lastWritten = new HashMap<>();

    @Mock
    SsdApplication ssdApplication;
    @InjectMocks
    ScriptManager scriptManager;

    @Test
    void createSscScriptManager(){
        assertNotNull(scriptManager);
    }

    @Test
    void testScript1() {
        for (int i = 0; i <= SCRIPT1_LOOP - SCRIPT1_TERM; i += SCRIPT1_TERM) {
            for (int j = i; j < i + SCRIPT1_TERM; j++) {
                when(ssdApplication.execute(getReadCommand(j))).thenReturn(DUMMY_VALUE);
                when(ssdApplication.execute(getWriteCommand(j, DUMMY_VALUE))).thenReturn("OK");
            }
        }

        boolean result = scriptManager.testScript1();

        for (int i = 0; i < SCRIPT1_LOOP; i++) {
            verify(ssdApplication).execute(getWriteCommand(i, DUMMY_VALUE));
        }
        for (int i = 0; i < SCRIPT1_LOOP; i++) {
            verify(ssdApplication).execute(getReadCommand(i));
        }

        assertTrue(result);
        verify(ssdApplication, times(200)).execute(anyString());
    }

    @Test
    void testScript2(){
        for (int i = 0; i <= 4; i++){
            when(ssdApplication.execute(getWriteCommand(i, DUMMY_VALUE))).thenReturn("OK");
        }
        for (int i = 0; i <= 4; i++){
            when(ssdApplication.execute(getReadCommand(i))).thenReturn(DUMMY_VALUE);
        }

        boolean result = scriptManager.testScript2();

        assertTrue(result);
    }

    @Test
    void testScript3(){
        doAnswer(invocation -> {
            String command = invocation.getArgument(0);
            if (command.startsWith("W ")) {
                String[] parts = command.split(" ");
                int lba = Integer.parseInt(parts[1]);
                String value = parts[2];
                lastWritten.put(lba, value);
            }
            return null;
        }).when(ssdApplication).execute(startsWith("W"));

        when(ssdApplication.execute(startsWith("R"))).thenAnswer(invocation -> {
            String command = invocation.getArgument(0);
            int lba = Integer.parseInt(command.split(" ")[1]);
            return lastWritten.getOrDefault(lba, "0x00000000");
        });

        boolean result = scriptManager.testScript3();

        assertTrue(result);
        verify(ssdApplication, times(100)).execute(startsWith("W 0"));
        verify(ssdApplication, times(100)).execute(startsWith("W 99"));
        verify(ssdApplication, times(100)).execute(startsWith("R 0"));
        verify(ssdApplication, times(100)).execute(startsWith("R 99"));
        verify(ssdApplication, times(400)).execute(anyString());
    }

    private String getReadCommand(int i) {
        return "R " + i;
    }

    private String getWriteCommand(int i, String value) {
        return "W " + i + " " + value;
    }
}
