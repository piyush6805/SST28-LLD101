package com.example.pen;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Factory for creating {@link Pen} instances by type name.
 *
 * <p>Three types are registered by default:
 * <ul>
 *   <li>{@code "ballpoint"} → {@link BallpointPen}</li>
 *   <li>{@code "ink"}       → {@link InkPen}</li>
 *   <li>{@code "gel"}       → {@link GelPen}</li>
 * </ul>
 *
 * <p>New pen types can be added at runtime via {@link #register}.
 */
public class PenFactory {

    private final Map<String, BiFunction<String, String, Pen>> registry = new HashMap<>();

    public PenFactory() {
        registry.put("ballpoint", BallpointPen::new);
        registry.put("ink",       InkPen::new);
        registry.put("gel",       GelPen::new);
    }

    /**
     * Creates a pen of the given type.
     *
     * @param type  pen type key (case-insensitive), e.g. {@code "ballpoint"}
     * @param brand brand name, e.g. {@code "Parker"}
     * @param color ink colour, e.g. {@code "Blue"}
     * @return a fully configured {@link Pen}
     * @throws IllegalArgumentException if the type is not registered
     */
    public Pen create(String type, String brand, String color) {
        Objects.requireNonNull(type,  "type must not be null");
        Objects.requireNonNull(brand, "brand must not be null");
        Objects.requireNonNull(color, "color must not be null");

        BiFunction<String, String, Pen> creator = registry.get(type.toLowerCase());
        if (creator == null) {
            throw new IllegalArgumentException("Unknown pen type: \"" + type
                    + "\". Registered types: " + registry.keySet());
        }
        return creator.apply(brand, color);
    }

    /**
     * Registers a new pen type (or overrides an existing one).
     *
     * @param type    pen type key (case-insensitive)
     * @param creator factory function (brand, color) → Pen
     */
    public void register(String type, BiFunction<String, String, Pen> creator) {
        Objects.requireNonNull(type,    "type must not be null");
        Objects.requireNonNull(creator, "creator must not be null");
        registry.put(type.toLowerCase(), creator);
    }
}
