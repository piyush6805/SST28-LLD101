package com.example.pen;

/** Starts the pen by clicking a button to deploy the tip. */
public class ClickStartStrategy implements StartStrategy {

    @Override
    public void start(Pen pen) {
        System.out.println("[Click] Tip deployed — " + pen);
    }
}
