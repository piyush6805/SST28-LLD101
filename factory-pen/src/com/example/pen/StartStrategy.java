package com.example.pen;

/**
 * Strategy interface for starting (activating) a pen.
 * Different pen types expose their tip in different ways
 * (click button, remove cap, etc.).
 */
public interface StartStrategy {
    void start(Pen pen);
}
