import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class FileManager {
    public static final String BLANK_DATA = "0x00000000";
    public static final String SSD_NAND_FILE_NAME = "ssd_nand.txt";
    public static final String SSD_OUTPUT_FILE_NAME = "ssd_output.txt";

    private Map<Integer, String> hashmap = new HashMap<>();

    public void readFile(int index) {
        String result = readNandFile(index);
        File file = getOrCreateFile(SSD_OUTPUT_FILE_NAME);
        writeOnOutputFile(file, result);
    }

    public void writeFile(int index, String value) {
        hashmap.put(index,value);

        File file = getOrCreateFile(SSD_NAND_FILE_NAME);
        writeOnFile(file);
    }

    private File getOrCreateFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("파일 생성 오류 발생");
            }
        }
        return file;
    }

    private List<String> getDataFromNandFile(File file) {
        List<String> data = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 오류 발생");
        }
        return data;
    }

    private String readNandFile(int index) {
        File file = getOrCreateFile(SSD_NAND_FILE_NAME);
        List<String> data = getDataFromNandFile(file);
        updateHashMap(data);
        return hashmap.getOrDefault(index, BLANK_DATA);
    }

    private void updateHashMap(List<String> data) {
        for(String d : data){
            String[] tmp = d.split(" ");
            hashmap.put(Integer.parseInt(tmp[0]), tmp[1]);
        }
    }

    private void writeOnFile(File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Entry<Integer, String> entry : hashmap.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeOnOutputFile(File file, String result) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(result);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void throwExcpetion(String result) {

    }
}
