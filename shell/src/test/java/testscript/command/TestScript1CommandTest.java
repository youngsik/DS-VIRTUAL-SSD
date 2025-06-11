package testscript.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rootcommand.Command;
import testscript.ScriptManager;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestScript1CommandTest {
    public static final String DUMMY_VALUE = "0xAAAABBBB";

    @Mock
    ScriptManager scriptManager;
    //• 0 ~ 4번 LBA까지 Write 명령어를 수행한다.
    //• 0 ~ 4번 LBA까지 ReadCompare 수행
    //• 5 ~ 9번 LBA까지 다른 값으로 Write 명령어를 수행한다.
    //• 5 ~ 9번 LBA까지 ReadCompare 수행
    //• 10 ~ 14번 LBA까지 다른 값으로 Write 명령어를 수행한다.
    //• 10 ~ 14번 LBA까지 ReadCompare 수행
    //• 위와 같은 규칙으로 전체 영역에 대해 Full Write + Read Compare를 수행한다

    @Test
    void callTestScript1Command() {
        Command command = new TestScript1Command(scriptManager);

        command.execute();

        for (int i = 0; i < 99; i++){
            verify(scriptManager).write(i, DUMMY_VALUE);
            verify(scriptManager).readAndCompare(i, DUMMY_VALUE);
        }
    }
}
