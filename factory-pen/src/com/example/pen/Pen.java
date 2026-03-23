package com.example.pen;

import java.util.Objects;

/**
 * Abstract base class for all pen types.
 *
 * <p>Holds common state (brand, color, inkLevel) and delegates
 * the start and refill behaviours to pluggable Strategy objects,
 * so new pen types can mix-and-match behaviours without sub-classing.
 */
public abstract class Pen {

    private final String brand;
    private final String color;
    private int inkLevel; // 0..100
    private final StartStrategy startStrategy;
    private final RefillStrategy refillStrategy;

    /** Maximum ink level (fully refilled). */
    static final int MAX_INK_LEVEL = 100;

    protected Pen(String brand, String color, int inkLevel,
                  StartStrategy startStrategy, RefillStrategy refillStrategy) {
        this.brand = Objects.requireNonNull(brand, "brand must not be null");
        this.color = Objects.requireNonNull(color, "color must not be null");
        this.inkLevel = Math.max(0, Math.min(MAX_INK_LEVEL, inkLevel));
        this.startStrategy = Objects.requireNonNull(startStrategy, "startStrategy must not be null");
        this.refillStrategy = Objects.requireNonNull(refillStrategy, "refillStrategy must not be null");
    }

    /** Activates the pen using its configured {@link StartStrategy}. */
    public final void start() {
        startStrategy.start(this);
    }

    /** Refills the pen using its configured {@link RefillStrategy}. */
    public final void refill() {
        refillStrategy.refill(this);
    }

    /** Writes the given text; concrete subclasses define the visual effect. */
    public abstract void write(String text);

    public String getBrand() { return brand; }
    public String getColor() { return color; }
    public int getInkLevel() { return inkLevel; }

    /** Called by subclasses and strategies to update the ink level. */
    void setInkLevel(int level) {
        this.inkLevel = Math.max(0, Math.min(MAX_INK_LEVEL, level));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[brand=" + brand + ", color=" + color + ", ink=" + inkLevel + "%]";
    }
}
