package com.samsung.file;

import java.util.List;

public interface FileManagerInterface {
    public static FileManagerInterface getInstance() {
        return null;
    }
    public void readFile(int index);
    public String getValueFromFile(int index);
    public String getResultFromOutputFile();
    public List<String> getAllValuesFromFile();
    public void writeFile(int index, String value);
    public void writeOnOutputFile(String result);
}
