package com.samsung.command.testscript;

import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("TestScript2 실행 테스트")
    void callTestScript2Command(){
        try {
            testScript2Command.execute(new String[1]);
        } catch (Exception e) {

        }
        verify(scriptManager).testScript2();
    }
}
