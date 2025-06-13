package com.samsung.file;

import com.samsung.handler.ExceptionHandlingProxy;

import java.io.*;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.samsung.file.FileConstants.*;

public class FileManager{

    private static FileManager instance;

    private FileManager() {
        resetNandFile();
    }

    public static FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }
        return instance;
    }

    public void readFile(int index) {
        String result = getValueFromFile(index);
        writeOnOutputFile(result);
    }

    public String getValueFromFile(int index) {
        String result = null;
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(SSD_NAND_FILE_NAME, "r");
            file.seek((long) index * OFFSET);
            byte[] buf = new byte[10];
            file.read(buf);
            result = new String(buf);
        } catch (IOException e) {
        }
        return result;
    }

    public String getResultFromOutputFile() {
        String result = null;

        try {
            result = Files.readString(Path.of(SSD_OUTPUT_FILE_NAME));
        } catch (IOException e) {

        }

        return result;
    }

    public List<String> getAllValuesFromFile() {
        List<String> result = null;
        try {
            result = Files.readAllLines(Path.of(SSD_NAND_FILE_NAME));
        } catch (IOException e) {

        }
        return result;
    }

    public void writeFile(int index, String value) {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(SSD_NAND_FILE_NAME, "rw");
            file.seek((long) index * OFFSET);
            file.writeBytes(value);
        } catch (IOException e) {

        }
    }

    public void writeOnOutputFile(String result) {
        try (RandomAccessFile file = new RandomAccessFile(SSD_OUTPUT_FILE_NAME, "rw")) {
            file.setLength(0);
            file.writeBytes(result);
        } catch (IOException e) {

        }
    }

    private void resetNandFile() {
        if (new File(SSD_NAND_FILE_NAME).exists())
            return;
        try (RandomAccessFile file = new RandomAccessFile(SSD_NAND_FILE_NAME, "rw")) {
            for (int i = 0; i < FILE_MAX_LINE; i++) {
                file.writeBytes(BLANK_DATA + "\n");
            }
            file.writeBytes(BLANK_DATA);
        } catch (IOException e) {

        }
    }
}
