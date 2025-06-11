import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    @Test
    void read_success() {
        FileManager fileManager = new FileManager();
        fileManager.writeFile(INDEX, CORRECT_VALUE);
        String expect = CORRECT_VALUE;

        fileManager.readFile(INDEX);

        try {
            File file = new File("ssd_output.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String ret = null;
            while ((ret = bufferedReader.readLine()) != null) {
                assertThat(ret).isEqualTo(expect);
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 오류 발생");
        }
    }
}