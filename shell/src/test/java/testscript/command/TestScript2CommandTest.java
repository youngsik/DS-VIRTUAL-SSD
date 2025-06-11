package testscript.command;

import com.samsung.testscript.ScriptManager;
import com.samsung.testscript.command.TestScript1Command;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestScript2CommandTest {
    @Mock
    ScriptManager scriptManager;
    @InjectMocks
    TestScript2Command testScript2Command;

    @Test
    void callTestScript2Command(){
        testScript2Command.execute();

        verify(scriptManager).testScript2();
    }

}
