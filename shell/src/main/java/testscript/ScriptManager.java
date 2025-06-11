package testscript;

import main.SsdApplication;

public class ScriptManager {
    private final SsdApplication ssdApplication;

    public ScriptManager(SsdApplication ssdApplication) {
        this.ssdApplication = ssdApplication;
    }

    public String read(Integer lba){
        String command = "R " + lba;
        return ssdApplication.execute(command);
    }

    public void write(Integer lba, String value){
        String command = "w " + lba + " " + value;
        ssdApplication.execute(command);
    }

    public boolean readAndCompare(Integer lba, String compareValue){
        String command = "R " + lba;
        String readValue = ssdApplication.execute(command);
        return compareValue.equals(readValue);
    }
}
