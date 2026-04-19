package com.example.pen;

/** Refills the pen by replacing its gel ink cartridge. */
public class GelCartridgeRefillStrategy implements RefillStrategy {

    @Override
    public void refill(Pen pen) {
        pen.setInkLevel(Pen.MAX_INK_LEVEL);
        System.out.println("[Gel cartridge replaced] Refilled to 100% — " + pen);
    }
}
