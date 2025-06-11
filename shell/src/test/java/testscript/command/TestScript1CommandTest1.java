package testscript.command;

import com.samsung.testscript.command.TestScript1Command;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.samsung.testscript.ScriptManager;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestScript1CommandTest1 {
    @Mock
    ScriptManager scriptManager;
    @InjectMocks
    TestScript1Command testScript1Command;

    @Test
    void callTestScript1Command() {
        testScript1Command.execute();

        verify(scriptManager).testScript1();
    }
}
