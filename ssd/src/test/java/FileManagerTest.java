import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FileManagerTest {

    public static final String CORRECT_VALUE = "0x1298CDEF";
    public static final int INDEX = 1;

    @Test
    void write_success() {
        FileManager fileManager = new FileManager();

        fileManager.writeFile(INDEX, CORRECT_VALUE);
        String ret = fileManager.readNandFile(INDEX);

        assertThat(ret).isEqualTo(CORRECT_VALUE);
    }
}