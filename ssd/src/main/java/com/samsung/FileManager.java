package com.samsung;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FileManager {
    public static final String BLANK_DATA = "0x00000000";
    public static final String SSD_NAND_FILE_NAME = "ssd_nand.txt";
    public static final String SSD_OUTPUT_FILE_NAME = "ssd_output.txt";

    private Map<Integer, String> hashmap = new HashMap<>();

    public void readFile(int index) {
        settingHashMapFromNandFile();
        String result = getValue(index);
        writeOnOutputFile(result);
    }

    public void writeFile(int index, String value) {
        settingHashMapFromNandFile();
        hashmap.put(index,value);
        writeOnNandFile();
    }

    private void settingHashMapFromNandFile() {
        List<String> data = getDataFromNandFile();
        updateHashMap(data);
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

    private List<String> getDataFromNandFile() {
        File file = getOrCreateFile(SSD_NAND_FILE_NAME);
        List<String> data = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {

        }
        return data;
    }

    private String getValue(int index) {
        return hashmap.getOrDefault(index, BLANK_DATA);
    }

    private void updateHashMap(List<String> data) {
        for(String d : data){
            String[] tmp = d.split(" ");
            hashmap.put(Integer.parseInt(tmp[0]), tmp[1]);
        }
    }

    private void writeOnNandFile() {
        File file = getOrCreateFile(SSD_NAND_FILE_NAME);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Entry<Integer, String> entry : hashmap.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {

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
}
