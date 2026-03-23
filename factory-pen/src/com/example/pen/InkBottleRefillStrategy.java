package com.example.pen;

/** Refills the pen by filling from an ink bottle. */
public class InkBottleRefillStrategy implements RefillStrategy {

    @Override
    public void refill(Pen pen) {
        pen.setInkLevel(Pen.MAX_INK_LEVEL);
        System.out.println("[Ink bottle] Refilled to 100% — " + pen);
    }
}
