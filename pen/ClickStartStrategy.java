public class ClickStartStrategy implements StartStrategy {
    @Override
    public void start(Pen pen) {
        System.out.println("Clicking pen to extend tip.");
    }

    @Override
    public void close(Pen pen) {
        System.out.println("Clicking pen to retract tip.");
    }

    @Override
    public String mechanismName() {
        return "CLICK";
    }
}
