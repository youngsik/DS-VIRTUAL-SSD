public class ExitCommand implements Command {

    private TestShellManager testShellManager;

    public ExitCommand(TestShellManager testShellManager) {
        this.testShellManager = testShellManager;
    }

    @Override
    public void excute() {
        testShellManager.exit();
    }
}
