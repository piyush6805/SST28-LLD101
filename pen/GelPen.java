public class GelPen extends Pen {
    private static final double INK_CONSUMPTION = 0.35;

    public GelPen(String inkColor, StartStrategy startStrategy, RefillStrategy refillStrategy) {
        super(PenType.GEL, inkColor, startStrategy, refillStrategy);
    }

    @Override
    protected String getWritingPrefix() {
        return "Being written by a gel pen: ";
    }

    @Override
    protected double getInkConsumptionRate() {
        return INK_CONSUMPTION;
    }
}
