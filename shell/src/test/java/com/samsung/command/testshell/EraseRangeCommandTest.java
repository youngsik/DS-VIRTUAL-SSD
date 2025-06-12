package com.samsung.command.testshell;

import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("LBA 구간이 정상적으로 들어오는 경우")
    public void validLBA() {
        Random random = new Random();

        int begin = random.nextInt(100);
        int end = random.nextInt(100);

        String[] cmdArgs = new String[]{"erase_range", String.valueOf(begin), String.valueOf(end)};

        eraseRangeCommand.execute(cmdArgs);
        Mockito.verify(testShellManager, Mockito.times(1)).eraseRange(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    @DisplayName("LBA 구간이 비정상적으로 들어오는 경우 0에서 99사이 아님")
    public void invalidLBA() {
        Random random = new Random();

        int begin = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        int end = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);

        while(begin >= 0 && begin < 100 && end >= 0 && end < 100) {
            begin = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        String[] cmdArgs = new String[]{"erase_range", String.valueOf(begin), String.valueOf(end)};

        assertThrows(RuntimeException.class, () -> eraseRangeCommand.execute(cmdArgs));
    }

    @Test
    @DisplayName("인자로 문자열 전달")
    public void invalidStringParameter() {
        String[] cmdArgs = new String[] {"erase_range", "0x", "11"};
        assertThrows(RuntimeException.class, () -> eraseRangeCommand.execute(cmdArgs));
    }

    @Test
    @DisplayName("인자 개수 문제")
    public void invalidLessParameter() {
        String[] cmdArgs = new String[] {"erase_range"};
        assertThrows(RuntimeException.class, () -> eraseRangeCommand.execute(cmdArgs));
    }

    @Test
    public void invalidManyParameter() {
        String[] cmdArgs = new String[] {"erase_range", "0", "10", "hello"};
        assertThrows(RuntimeException.class, () -> eraseRangeCommand.execute(cmdArgs));
    }
}
