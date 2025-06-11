import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    @DisplayName("parsing 함수 테스트")
    void testParsing() {
        Main main = new Main();
        String[] args = {"W", "3", "0x12345678"};
        main.parsing(args);

        assertEquals("W", main.command);
        assertEquals(3, main.lbaLocation);
        assertEquals("0x12345678", main.value);
    }

}