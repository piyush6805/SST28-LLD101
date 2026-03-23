public abstract class Pen {
    public static final double MAX_INK_LEVEL = 100.0;

    private final StartStrategy startStrategy;
    private final RefillStrategy refillStrategy;
    private final PenType type;

    private boolean started;
    private String inkColor;
    private double inkLevel;

    protected Pen(PenType type, String inkColor, StartStrategy startStrategy, RefillStrategy refillStrategy) {
        validateInputs(type, startStrategy, refillStrategy, inkColor);
        
        this.type = type;
        this.inkColor = inkColor.trim();
        this.startStrategy = startStrategy;
        this.refillStrategy = refillStrategy;
        this.started = false;
        this.inkLevel = MAX_INK_LEVEL;
    }

    private void validateInputs(PenType type, StartStrategy startStrategy, RefillStrategy refillStrategy, String inkColor) {
        if (type == null) {
            throw new InvalidPenConfigurationException("Pen type cannot be null.");
        }
        if (startStrategy == null) {
            throw new InvalidPenConfigurationException("Start strategy cannot be null.");
        }
        if (refillStrategy == null) {
            throw new InvalidPenConfigurationException("Refill strategy cannot be null.");
        }
        if (inkColor == null || inkColor.trim().isEmpty()) {
            throw new InvalidPenConfigurationException("Ink color cannot be null or blank.");
        }
    }

    public final void start() {
        if (started) {
            throw new PenAlreadyStartedException(type + " pen is already started.");
        }

        startStrategy.start(this);
        started = true;
    }

    public final void write(String text) {
        if (!started) {
            throw new PenNotStartedException(type + " pen is not started. Call start() first.");
        }
        if (text == null) {
            throw new InvalidPenConfigurationException("Text cannot be null.");
        }
        if (inkLevel <= 0.0) {
            throw new OutOfInkException(type + " pen has no ink left.");
        }

        double requiredInk = text.length() * getInkConsumptionRate();
        if (requiredInk > inkLevel) {
            throw new OutOfInkException(type + " pen does not have enough ink for this write operation.");
        }

        if (!text.isEmpty()) {
            inkLevel = Math.max(0.0, inkLevel - requiredInk);
        }

        displayWriting(text);
    }

    public final void close() {
        if (!started) {
            throw new PenNotStartedException(type + " pen is already closed.");
        }

        startStrategy.close(this);
        started = false;
    }

    public final void refill(String newColor) {
        if (started) {
            throw new InvalidRefillOperationException("Cannot refill while pen is started. Close it first.");
        }

        refillStrategy.refill(this, newColor);
    }

    private void displayWriting(String text) {
        System.out.println(getWritingPrefix() + text);
        System.out.printf("Ink left: %.2f%%%n", inkLevel);
    }

    protected abstract String getWritingPrefix();

    protected abstract double getInkConsumptionRate();

    public final PenType getType() {
        return type;
    }

    public final boolean isStarted() {
        return started;
    }

    public final String getInkColor() {
        return inkColor;
    }

    public final double getInkLevel() {
        return inkLevel;
    }

    public final String getMechanismName() {
        return startStrategy.mechanismName();
    }

    void setInkColor(String newColor) {
        if (newColor == null || newColor.trim().isEmpty()) {
            throw new InvalidPenConfigurationException("Ink color cannot be null or blank.");
        }
        this.inkColor = newColor.trim();
    }

    void setInkLevel(double newInkLevel) {
        if (newInkLevel < 0.0 || newInkLevel > MAX_INK_LEVEL) {
            throw new InvalidPenConfigurationException("Ink level must be between 0 and 100.");
        }
        this.inkLevel = newInkLevel;
    }
}
