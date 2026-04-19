package com.example.pen;

/** Refills the pen by replacing its ink cartridge. */
public class CartridgeRefillStrategy implements RefillStrategy {

    @Override
    public void refill(Pen pen) {
        pen.setInkLevel(Pen.MAX_INK_LEVEL);
        System.out.println("[Cartridge replaced] Refilled to 100% — " + pen);
    }
}
