package com.samsung.command.testshell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HelpCommandTest {

    @Mock
    TestShellManager testShellManager;

    @InjectMocks
    HelpCommand helpCommand;

    @DisplayName("execute 실행 테스트")
    @Test
    void executeTest() {
        helpCommand.execute(new String[1]);
        verify(testShellManager, times(1)).help();
    }
}