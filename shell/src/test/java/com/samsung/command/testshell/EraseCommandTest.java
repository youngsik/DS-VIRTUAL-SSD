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
class EraseCommandTest {
    @Mock
    TestShellManager testShellManager;

    @InjectMocks
    EraseCommand eraseCommand;

    @Test
    @DisplayName("LBA가 0에서 99 사이이고, length가 정수인 모든 경우에 대해 작동")
    public void validLBA() {
        Random random = new Random();

        int startLBA = random.nextInt(0, 100);
        int size = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);

        String[] cmdArgs = new String[]{"erase", String.valueOf(startLBA), String.valueOf(size)};
        eraseCommand.execute(cmdArgs);

        Mockito.verify(testShellManager, Mockito.times(1)).erase(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    @DisplayName("LBA가 0 이하거나 99 초과이면 작동 안함")
    public void invalidLBA() {
        Random random = new Random();

        int startLBA;
        int size = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);

        if(random.nextBoolean()) {
            startLBA = random.nextInt(Integer.MIN_VALUE, 0);
        }
        else{
            startLBA = random.nextInt(100, Integer.MAX_VALUE);
        }

        String[] cmdArgs = new String[] {"erase", String.valueOf(startLBA), String.valueOf(size)};
        assertThrows(RuntimeException.class, () -> eraseCommand.execute(cmdArgs));
    }

    @Test
    @DisplayName("파라미터 개수 오류(1개)")
    public void invalidParameterCountLess() {
        String[] cmdArgs = new String[] {"erase"};
        assertThrows(RuntimeException.class, () -> eraseCommand.execute(cmdArgs));
    }

    @Test
    @DisplayName("파라미터 개수 오류(5개)")
    public void invalidParameterCountMany() {
        String[] cmdArgs = new String[] {"erase", "0", "0", "0"};
        assertThrows(RuntimeException.class, () -> eraseCommand.execute(cmdArgs));
    }

    @Test
    public void invalidParameterNotInteger() {
        String[] cmdArgs = new String[]{"erase", "A", "10"};
        assertThrows(RuntimeException.class, () -> eraseCommand.execute(cmdArgs));
    }

    @Test
    public void invalidParameterNotIntegerLength() {
        String[] cmdArgs = new String[] {"erase", "0", "F"};
        assertThrows(RuntimeException.class, () -> eraseCommand.execute(cmdArgs));
    }
}
