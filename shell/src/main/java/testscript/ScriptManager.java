package testscript;

import main.SsdApplication;

public class ScriptManager {
    private static final String READ_PREFIX_COMMAND = "R ";
    private static final String WRITE_PREFIX_COMMAND = "W ";
    private final SsdApplication ssdApplication;

    public ScriptManager(SsdApplication ssdApplication) {
        this.ssdApplication = ssdApplication;
    }

    public String read(Integer lba){
        String command = READ_PREFIX_COMMAND + lba;
        return ssdApplication.execute(command);
    }

    public void write(Integer lba, String value){
        String command = WRITE_PREFIX_COMMAND + lba + " " + value;
        ssdApplication.execute(command);
    }

    public boolean readAndCompare(Integer lba, String compareValue){
        String command = READ_PREFIX_COMMAND + lba;
        String readValue = ssdApplication.execute(command);
        return compareValue.equals(readValue);
    }
}
