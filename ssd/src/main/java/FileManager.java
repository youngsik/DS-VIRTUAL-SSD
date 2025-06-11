import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

class FileManager {
    public static final String BLANK_DATA = "0x00000000";
    public static final String SSD_NAND_FILE_NAME = "ssd_nand.txt";

    private Map<Integer, String> hashmap = new HashMap<>();

    public void readFile(int index) {
    }

    public void writeFile(int index, String value) {
        hashmap.put(index,value);

        File file = getOrCreateFile(SSD_NAND_FILE_NAME);
        writeOnFile(file);
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

    public String readNandFile(int index) {
        File file = getOrCreateFile(SSD_NAND_FILE_NAME);
        updateHashMapForNandFile(file);
        return hashmap.getOrDefault(index, BLANK_DATA);
    }

    private File getOrCreateFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("파일 생성 오료");
            }
        }
        return file;
    }

    private void updateHashMapForNandFile(File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tmp = line.split(" ");
                hashmap.put(Integer.parseInt(tmp[0]), tmp[1]);
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 오류 발생");
        }
    }
}
