package com.samsung.command.testshell;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EraseRangeCommandTest {
    @Mock
    TestShellManager testShellManager;

    @InjectMocks
    EraseRangeCommand eraseRangeCommand;

    @Test
    public void validLBA() {
        Random random = new Random();

        int begin = random.nextInt(100);
        int end = random.nextInt(100);

        String[] cmdArgs = new String[]{"erase_range", String.valueOf(begin), String.valueOf(end)};

        eraseRangeCommand.execute(cmdArgs);
        Mockito.verify(testShellManager, Mockito.times(1)).eraseRange(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void invalidLBA() {
        Random random = new Random();

        int begin = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        int end = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);

        String[] cmdArgs = new String[]{"erase_range", String.valueOf(begin), String.valueOf(end)};

        assertThrows(RuntimeException.class, () -> eraseRangeCommand.execute(cmdArgs));
    }
}
