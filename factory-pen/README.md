# Factory + Strategy — Pen Design

## Design

Two complementary patterns work together:

| Pattern   | Role |
|-----------|------|
| **Factory** | `PenFactory` creates the right `Pen` subclass by name, hiding the `new` keyword from callers. |
| **Strategy** | `StartStrategy` and `RefillStrategy` inject independent, swappable behaviours into every pen without requiring a new subclass for each combination. |

### Class Diagram (text)

```
          «interface»            «interface»
          StartStrategy          RefillStrategy
          ──────────────         ───────────────
          start(Pen)             refill(Pen)
               ▲                       ▲
    ┌──────────┴──────────┐   ┌────────┴──────────────┐
    │                     │   │          │             │
ClickStart           CapStart  CartridgeRefill  InkBottleRefill  GelCartridgeRefill

                «abstract»
                   Pen
               ──────────────
               brand : String
               color : String
               inkLevel : int
               start()            ──→  StartStrategy
               refill()           ──→  RefillStrategy
               write(text)*
                   ▲
       ┌───────────┼────────────┐
  BallpointPen  InkPen        GelPen

                PenFactory
               ──────────────
               create(type, brand, color) : Pen
               register(type, creator)
```

## Pen Types

| Pen          | Start behaviour   | Refill behaviour         | Ink/write |
|--------------|-------------------|--------------------------|-----------|
| BallpointPen | Click button      | Replace cartridge        | 1%        |
| InkPen       | Remove cap        | Fill from ink bottle     | 2%        |
| GelPen       | Click button      | Replace gel cartridge    | 1%        |

## Build & Run

```bash
cd factory-pen/src
javac com/example/pen/*.java
java com.example.pen.App
```

## Extending the Design

### Add a new pen type

```java
factory.register("stylus", StylusPen::new);
Pen s = factory.create("stylus", "Wacom", "Black");
```

### Swap a strategy without changing the pen class

```java
// Hypothetical pen with custom start strategy
public class TwinTipPen extends Pen {
    public TwinTipPen(String brand, String color) {
        super(brand, color, 100,
              new ClickStartStrategy(),       // reuse existing
              new CartridgeRefillStrategy()); // reuse existing
    }
    @Override
    public void write(String text) { /* ... */ }
}
```

## Key Design Principles Applied

- **OCP** — adding a new pen type or strategy does not modify existing classes.
- **SRP** — `Pen` is responsible for pen state; strategies are responsible for specific behaviours.
- **DIP** — `Pen` depends on the `StartStrategy` / `RefillStrategy` abstractions, not concrete implementations.
