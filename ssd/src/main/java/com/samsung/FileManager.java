package com.samsung;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

import static com.samsung.Constants.*;

public class FileManager {

    public FileManager() {
        resetNandFile();
    }

    public void readFile(int index) {
        try {
            RandomAccessFile file = new RandomAccessFile(SSD_NAND_FILE_NAME, "r");
            file.seek(index * OFFSET);
            byte[] buf = new byte[10];
            file.read(buf);
            writeOnOutputFile(new String(buf));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeFile(int index, String value) {
        try {
            RandomAccessFile file = new RandomAccessFile(SSD_NAND_FILE_NAME, "rw");
            file.seek(index * OFFSET);
            file.writeBytes(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeOnOutputFile(String result) {
        File file = getOrCreateFile(SSD_OUTPUT_FILE_NAME);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(result);
            writer.flush();
            writer.close();
        } catch (IOException e) {

        }
    }

    private File getOrCreateFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {

            }
        }
        return file;
    }

    private void resetNandFile() {
        File file = getOrCreateFile(SSD_NAND_FILE_NAME);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < 98; i++) {
                writer.write(BLANK_DATA + "\n");
            }
            writer.write(BLANK_DATA);
            writer.flush();
            writer.close();
        } catch (IOException e) {

        }
    }
}
