package com.example.pen;

/**
 * A fountain / ink pen: activated by removing the cap, refilled from an ink bottle.
 * Consumes 2% ink per write operation (ink flows more freely).
 */
public class InkPen extends Pen {

    public InkPen(String brand, String color) {
        super(brand, color, 100, new CapStartStrategy(), new InkBottleRefillStrategy());
    }

    @Override
    public void write(String text) {
        if (getInkLevel() == 0) {
            System.out.println("[InkPen] Out of ink — cannot write.");
            return;
        }
        setInkLevel(getInkLevel() - 2);
        System.out.println("[InkPen] Writing: \"" + text + "\" (ink: " + getInkLevel() + "%)");
    }
}
