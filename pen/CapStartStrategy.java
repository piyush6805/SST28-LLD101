public class CapStartStrategy implements StartStrategy {
    @Override
    public void start(Pen pen) {
        System.out.println("Removing cap to start writing.");
    }

    @Override
    public void close(Pen pen) {
        System.out.println("Putting cap back to close the pen.");
    }

    @Override
    public String mechanismName() {
        return "CAP";
    }
}
