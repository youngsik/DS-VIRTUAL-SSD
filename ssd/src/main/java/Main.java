class Main {
    public void print(){
        System.out.println("print SSD");
    }

    public static void main(String[] args) {
        SSDManager ssdManager = new SSDManager("W", 0, "0x00000002");
        ssdManager.cmdExecute();

        ssdManager = new SSDManager("W", 0, "0x00000001");
        ssdManager.cmdExecute();
    }
}
