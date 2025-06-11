import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExitCommandTest {

    @Mock
    TestShellManager testShellManager;

    @Test
    void excute() {
        ExitCommand exitCommand = new ExitCommand();
        exitCommand.excute();
        verify(testShellManager, times(1)).exit();
    }
}