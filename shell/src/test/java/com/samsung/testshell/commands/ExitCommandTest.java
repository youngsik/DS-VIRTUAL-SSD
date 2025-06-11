package com.samsung.testshell.commands;

import com.samsung.testshell.TestShellManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExitCommandTest {

    public static final Integer INDEX = 1;
    public static final String VALUE = "0XFFFFFF";

    @Mock
    TestShellManager testShellManager;

    @InjectMocks
    ExitCommand exitCommand;

    @DisplayName("execute 실행 테스트")
    @Test
    void executeTest() {
        exitCommand.execute(INDEX, VALUE);
        verify(testShellManager, times(1)).exit();
    }
}