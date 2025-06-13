package com.samsung.file;

import java.util.List;

public interface FileManagerInterface {
    static FileManagerInterface getInstance() {
        return null;
    }
    void readFile(int index);
    String getValueFromFile(int index);
    String getResultFromOutputFile();
    List<String> getAllValuesFromFile();
    void writeFile(int index, String value);
    void writeOnOutputFile(String result);
}
