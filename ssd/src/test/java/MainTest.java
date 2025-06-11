import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MainTest {

    @Mock
    FileManager mockFileManager;

    @Test
    @DisplayName("parsing 함수 테스트")
    void testParsing() {
        Main main = new Main();
        String[] args = {"W", "3", "0x12345678"};
        main.parsing(args);

        assertEquals("W", main.command);
        assertEquals(3, main.LBA);
        assertEquals("0x12345678", main.value);
    }

    @Test
    @DisplayName("parsing 함수 테스트2")
    void testParsing2() {
        Main main = new Main();
        String[] args = {"R", "3"};
        main.parsing(args);

        assertEquals("R", main.command);
        assertEquals(3, main.LBA);
        assertEquals(null, main.value);
    }

    @Test
    @DisplayName("command가 W, R이 아닌 경우 command ERROR 할당 테스트")
    void testParsingWithInvalidCommand() {
        Main main = new Main();
        String[] args = {"X", "5", "0x99999999"};
        main.parsing(args);

        assertEquals("ERROR", main.command);
        assertEquals("ERROR", main.value);
    }

    @Test
    @DisplayName("lbaLocation이 int가 아니면 command ERROR 할당 테스트")
    void testParsingWithInvalidLbaLocation() {
        Main main = new Main();
        String[] args = {"W", "notInt", "0x12345678"};
        main.parsing(args);
        assertEquals("ERROR", main.command);
        assertEquals("ERROR", main.value);
    }

}