package testscript;

import com.samsung.FileManager;
import com.samsung.testscript.ScriptManager;
import main.SsdApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

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
    @Mock
    FileManager fileManager;
    @InjectMocks
    ScriptManager scriptManager;

    @Test
    void createSscScriptManager(){
        assertNotNull(scriptManager);
    }

    @Test
    @DisplayName("TestScript1 구동 테스트")
    void testScript1() {
        Map<Integer, String> hashMap = new HashMap<>();
        for (int i = 0; i <= SCRIPT1_LOOP - SCRIPT1_TERM; i += SCRIPT1_TERM) {
            for (int j = i; j < i + SCRIPT1_TERM; j++) {
                doNothing().when(fileManager).readFile(j);
                when(ssdApplication.execute(getWriteCommand(j, DUMMY_VALUE))).thenReturn("OK");
                hashMap.put(j, DUMMY_VALUE);
            }
        }
        when(fileManager.getHashmap()).thenReturn(hashMap);

        boolean result = scriptManager.testScript1();

        for (int i = 0; i < SCRIPT1_LOOP; i++) {
            verify(ssdApplication).execute(getWriteCommand(i, DUMMY_VALUE));
        }
        for (int i = 0; i < SCRIPT1_LOOP; i++) {
            verify(fileManager).readFile(i);
        }

        assertTrue(result);
        verify(ssdApplication, times(100)).execute(anyString());
    }

    @Test
    @DisplayName("TestScript2 구동 테스트")
    void testScript2(){
        for (int i = 0; i <= 4; i++){
            when(ssdApplication.execute(getWriteCommand(i, DUMMY_VALUE))).thenReturn("OK");
        }

        Map<Integer, String> hashMap = new HashMap<>();
        for (int i = 0; i <= 4; i++){
            doNothing().when(fileManager).readFile(i);
            hashMap.put(i, DUMMY_VALUE);
        }
        when(fileManager.getHashmap()).thenReturn(hashMap);

        boolean result = scriptManager.testScript2();

        assertTrue(result);
    }

    @Test
    @DisplayName("TestScript3 구동 테스트")
    void testScript3(){
        when(fileManager.getHashmap()).thenReturn(lastWritten);
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
        doNothing().when(fileManager).readFile(anyInt());

        boolean result = scriptManager.testScript3();

        assertTrue(result);
        verify(ssdApplication, times(200)).execute(startsWith("W 0"));
        verify(ssdApplication, times(200)).execute(startsWith("W 99"));
        verify(fileManager, times(200)).readFile(0);
        verify(fileManager, times(200)).readFile(99);
        verify(fileManager, atLeast(400)).getHashmap();
    }

    private String getWriteCommand(int i, String value) {
        return "W " + i + " " + value;
    }
}
