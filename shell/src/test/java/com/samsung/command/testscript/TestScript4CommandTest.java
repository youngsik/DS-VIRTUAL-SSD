package com.samsung.command.testscript;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestScript4CommandTest {
    @Mock
    ScriptManager scriptManager;
    @InjectMocks
    TestScript4Command testScript4Command;

    @Test
    @DisplayName("TestScript4 실행 테스트")
    void callTestScript3Command(){
        testScript4Command.execute(new String[1]);

        verify(scriptManager).testScript4();
    }
}
