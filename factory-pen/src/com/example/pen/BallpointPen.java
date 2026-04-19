package com.example.pen;

/**
 * A ballpoint pen: activated by a click, refilled via cartridge.
 * Consumes 1% ink per write operation.
 */
public class BallpointPen extends Pen {

    public BallpointPen(String brand, String color) {
        super(brand, color, 100, new ClickStartStrategy(), new CartridgeRefillStrategy());
    }

    @Override
    public void write(String text) {
        if (getInkLevel() == 0) {
            System.out.println("[BallpointPen] Out of ink — cannot write.");
            return;
        }
        setInkLevel(getInkLevel() - 1);
        System.out.println("[BallpointPen] Writing: \"" + text + "\" (ink: " + getInkLevel() + "%)");
    }
}
