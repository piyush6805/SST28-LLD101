package com.example.pen;

/** Starts the pen by removing the cap to expose the tip. */
public class CapStartStrategy implements StartStrategy {

    @Override
    public void start(Pen pen) {
        System.out.println("[Cap removed] Tip exposed — " + pen);
    }
}
