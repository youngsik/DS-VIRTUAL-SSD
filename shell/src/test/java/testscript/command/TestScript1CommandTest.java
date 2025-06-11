package testscript.command;

import com.samsung.testscript.command.TestScript1Command;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.samsung.testscript.ScriptManager;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestScript1CommandTest {
    @Mock
    ScriptManager scriptManager;
    @InjectMocks
    TestScript1Command testScript1Command;

    @Test
    @DisplayName("TestScript1 실행 테스트")
    void callTestScript1Command() {
        testScript1Command.execute(new String[1]);

        verify(scriptManager).testScript1();
    }
}
