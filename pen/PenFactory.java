public final class PenFactory {
    private PenFactory() {
        // Factory utility class - no instantiation needed
    }

    public static Pen getPen(String type, String color, String mechanism) {
        PenType penType = PenType.from(type);
        StartMechanism startMechanism = StartMechanism.from(mechanism);

        StartStrategy startStrategy = getStartStrategy(startMechanism);
        RefillStrategy refillStrategy = getRefillStrategy(penType);

        return createPenInstance(penType, color, startStrategy, refillStrategy);
    }

    private static StartStrategy getStartStrategy(StartMechanism mechanism) {
        if (mechanism == StartMechanism.CAP) {
            return new CapStartStrategy();
        } else if (mechanism == StartMechanism.CLICK) {
            return new ClickStartStrategy();
        }
        
        throw new InvalidPenConfigurationException("Unknown mechanism: " + mechanism);
    }

    private static RefillStrategy getRefillStrategy(PenType penType) {
        if (penType == PenType.FOUNTAIN) {
            return new CartridgeRefill();
        } else if (penType == PenType.BALLPOINT || penType == PenType.GEL) {
            return new InkFillRefill();
        }
        
        throw new InvalidPenConfigurationException("Unknown pen type for refill: " + penType);
    }

    private static Pen createPenInstance(PenType type, String color, StartStrategy strategy, RefillStrategy refill) {
        if (type == PenType.BALLPOINT) {
            return new BallpointPen(color, strategy, refill);
        } else if (type == PenType.FOUNTAIN) {
            return new FountainPen(color, strategy, refill);
        } else if (type == PenType.GEL) {
            return new GelPen(color, strategy, refill);
        }
        
        throw new InvalidPenConfigurationException("Unsupported pen type: " + type);
    }
}
