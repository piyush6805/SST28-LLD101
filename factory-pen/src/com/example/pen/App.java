package com.example.pen;

import java.util.List;

/**
 * Demo entry point for the Factory + Strategy pen design.
 *
 * <p>Shows how {@link PenFactory} creates different pen types and how each pen
 * delegates its start and refill behaviour to the appropriate Strategy.
 */
public class App {

    public static void main(String[] args) {
        PenFactory factory = new PenFactory();

        List<Pen> pens = List.of(
                factory.create("ballpoint", "Parker",  "Blue"),
                factory.create("ink",       "Pilot",   "Black"),
                factory.create("gel",       "Uni",     "Red")
        );

        for (Pen pen : pens) {
            System.out.println("\n--- " + pen + " ---");
            pen.start();
            pen.write("Hello, LLD!");
            pen.write("Factory + Strategy pattern in action.");
            pen.refill();
        }
    }
}
