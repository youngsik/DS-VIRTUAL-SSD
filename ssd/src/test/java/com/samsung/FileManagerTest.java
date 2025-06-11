package com.samsung;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FileManagerTest {

    public static final String TEST_ERROR_MESSAGE = "테스트에서 ssd_output.txt 파일 읽기 오류 발생";
    public static final String TEST_SSD_OUTPUT_TXT_FILE_NAME = "src/test/resources/test_ssd_output.txt";
    public static final String CORRECT_VALUE = "0x1298CDEF";
    public static final int INDEX = 1;

    @InjectMocks
    private FileManager fileManager;

    @BeforeEach
    void setUp() {
        resetTestOutputFile();
    }

    @DisplayName("특정 인덱스 위치의 파일 읽고, output 텍스트 파일에 결과 쓰기")
    @Test
    void read_success() {
        assertByReadingSpecificIndexFile(INDEX, CORRECT_VALUE);
    }

    @DisplayName("nand 텍스트 파일에 새로운 데이터 쓰기")
    @Test
    void write_success() {
        fileManager.writeFile(INDEX, CORRECT_VALUE);

        assertByReadingSpecificIndexFile(INDEX, CORRECT_VALUE);
    }

    @DisplayName("nand 텍스트 파일에 2개 이상의 데이터 쓰기")
    @Test
    void write_success_2() {
        fileManager.writeFile(INDEX, CORRECT_VALUE);
        fileManager.writeFile(INDEX+1, CORRECT_VALUE);

        assertByReadingSpecificIndexFile(INDEX, CORRECT_VALUE);
        assertByReadingSpecificIndexFile(INDEX+1, CORRECT_VALUE);
    }

    private void resetTestOutputFile(){
        try {
            File file = new File(TEST_SSD_OUTPUT_TXT_FILE_NAME);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(CORRECT_VALUE);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(TEST_ERROR_MESSAGE);
        }
    }

    private void assertByReadingSpecificIndexFile(int index, String expect) {
        fileManager.readFile(index);

        try {
            File file = new File(TEST_SSD_OUTPUT_TXT_FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String ret = null;
            while ((ret = bufferedReader.readLine()) != null) {
                assertThat(ret).isEqualTo(expect);
            }
        } catch (IOException e) {
            System.out.println(TEST_ERROR_MESSAGE);
        }
    }
}