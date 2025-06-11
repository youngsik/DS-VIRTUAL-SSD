package testscript.command;

import com.samsung.testscript.ScriptManager;
import com.samsung.testscript.command.TestScript2Command;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestScript3CommandTest {
    @Mock
    ScriptManager scriptManager;
    @InjectMocks
    TestScript3Command testScript3Command;

    @Test
    void callTestScript3Command(){
        testScript3Command.execute();

        verify(scriptManager).testScript3();
    }
}
