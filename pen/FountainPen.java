public class FountainPen extends Pen {
    private static final double INK_CONSUMPTION = 0.55;

    public FountainPen(String inkColor, StartStrategy startStrategy, RefillStrategy refillStrategy) {
        super(PenType.FOUNTAIN, inkColor, startStrategy, refillStrategy);
    }

    @Override
    protected String getWritingPrefix() {
        return "Being written by a fountain pen: ";
    }

    @Override
    protected double getInkConsumptionRate() {
        return INK_CONSUMPTION;
    }
}
