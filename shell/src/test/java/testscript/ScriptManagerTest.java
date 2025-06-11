package testscript;

import com.samsung.testscript.ScriptManager;
import main.SsdApplication;
import org.junit.jupiter.api.BeforeEach;
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
        for (int i = 0; i <= 96; i += 4) {
            for (int j = i; j < i + 4; j++) {
                when(ssdApplication.execute("R " + j)).thenReturn(DUMMY_VALUE);
                when(ssdApplication.execute("W " + j + " " + DUMMY_VALUE)).thenReturn("OK");
            }
        }

        scriptManager.testScript1();

        // write 호출 검증
        for (int i = 0; i < 99; i++) {
            verify(ssdApplication).execute("W " + i + " " + DUMMY_VALUE);
        }

        // read 호출 검증
        for (int i = 0; i < 99; i++) {
            verify(ssdApplication).execute("R " + i);
        }

        // 호출 횟수까지 확인
        verify(ssdApplication, times(200)).execute(anyString());
    }
}
