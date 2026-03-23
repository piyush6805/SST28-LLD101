package com.example.pen;

/**
 * A gel pen: activated by a click, refilled via gel cartridge.
 * Consumes 1% ink per write operation.
 */
public class GelPen extends Pen {

    public GelPen(String brand, String color) {
        super(brand, color, 100, new ClickStartStrategy(), new GelCartridgeRefillStrategy());
    }

    @Override
    public void write(String text) {
        if (getInkLevel() == 0) {
            System.out.println("[GelPen] Out of ink — cannot write.");
            return;
        }
        setInkLevel(getInkLevel() - 1);
        System.out.println("[GelPen] Writing: \"" + text + "\" (ink: " + getInkLevel() + "%)");
    }
}
