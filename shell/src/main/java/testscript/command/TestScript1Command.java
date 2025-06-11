package testscript.command;

import rootcommand.Command;
import testscript.ScriptManager;

public class TestScript1Command implements Command {
    private final ScriptManager scriptManager;
    
    public TestScript1Command(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute() {
        
    }
}
