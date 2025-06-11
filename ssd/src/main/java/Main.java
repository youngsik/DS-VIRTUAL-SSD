class Main {
    public void print(){
        System.out.println("print SSD");
    }

    public String command;
    public int lbaLocation;
    public String value;

    public void parsing(String[] args) {
        command = args[0];
        lbaLocation = Integer.parseInt(args[1]);
        value = args[2];

        if (!"W".equals(command) && !"R".equals(command)) {
            value = "ERROR";
        }
    }
}
