package com.example.pen;

/**
 * Strategy interface for refilling a pen.
 * Different pen types are refilled in different ways
 * (replace cartridge, fill from ink bottle, etc.).
 */
public interface RefillStrategy {
    void refill(Pen pen);
}
