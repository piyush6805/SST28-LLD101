public class BallpointPen extends Pen {
    private static final double INK_CONSUMPTION = 0.25;

    public BallpointPen(String inkColor, StartStrategy startStrategy, RefillStrategy refillStrategy) {
        super(PenType.BALLPOINT, inkColor, startStrategy, refillStrategy);
    }

    @Override
    protected String getWritingPrefix() {
        return "Being written by a ballpoint pen: ";
    }

    @Override
    protected double getInkConsumptionRate() {
        return INK_CONSUMPTION;
    }
}
