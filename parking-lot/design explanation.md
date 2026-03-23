# Multilevel Parking Lot Design Explanation

## 1) What we are solving

This design models a realistic multilevel parking lot where:

- We support three vehicle types: BIKE, CAR, BUS.
- We support three slot types: SMALL, MEDIUM, LARGE.
- Vehicles are parked using the nearest compatible slot for the chosen entry gate.
- Billing is based on the slot type actually allocated, not on vehicle type.
- A single lot has multiple floors, multiple entry gates, and exit gates.

The goal was not just to "make it run", but to keep the design open for policy changes. That is why slot allocation and pricing are strategy-based.

---

## 2) Core class structure and why it is split this way

### ParkingLot (main orchestrator)

`ParkingLot` is the aggregate root. It owns:

- floors
- entry gates
- exit gates
- slot allocation strategy
- pricing strategy
- active ticket store

This class exposes the three core operations expected by the problem:

- `park(vehicle, entryTime, requestedSlotType, entryGateId)`
- `status()`
- `exit(ticket, exitTime)`

Why this shape?

Because `ParkingLot` should coordinate the flow, not implement every policy itself. The business rules that can change independently (allocation and pricing) are delegated to strategy interfaces.

### Floor and Slot

A floor contains slots (composition). This mirrors physical ownership: if a floor is removed, its slots do not exist independently in this model.

`Slot` stores:

- slot id
- floor number
- slot type
- availability
- per-gate distance metadata

The distance map in `Slot` is important for nearest-slot lookup. It allows us to compare the same slot from different gate perspectives without hardcoding gate logic in many places.

### Vehicle

`Vehicle` keeps only domain essentials:

- vehicle id
- vehicle type

The vehicle type knows its minimum required slot type through `minimumSlotType()`. This avoids scattering compatibility logic in many classes.

### Ticket and Bill

A `Ticket` captures entry-side state:

- ticket id
- entry time
- vehicle
- allocated slot
- entry gate id

A `Bill` captures exit-side result:

- ticket id
- amount
- exit time

This separation keeps entry and exit concerns clean.

### EntryGate and ExitGate

Gates are lightweight and focused:

- Entry gate generates ticket.
- Exit gate generates bill.

This follows the UML intent while still keeping the orchestration in `ParkingLot`.

---

## 3) Strategy pattern usage

Two interfaces were introduced intentionally:

- `SlotAllocationStrategy`
- `PricingStrategy`

### SlotAllocationStrategy

Current implementation: `NearestSlotStrategy`.

Responsibilities:

- find nearest compatible slot for a given entry gate
- update strategy state when a slot becomes occupied
- update strategy state when a slot is freed

Why this is helpful:

If tomorrow allocation changes from nearest-slot to least-used-floor or VIP-first, we can swap strategy without rewriting `ParkingLot`.

### PricingStrategy

Current implementation: `HourlyPricingStrategy`.

Responsibilities:

- compute bill amount using ticket + exit time
- round time to billable hours
- apply per-slot-type hourly rate

Why this is helpful:

If pricing changes to slab-based, dynamic pricing, weekend multipliers, etc., it can be added as a new strategy class.

---

## 4) Nearest slot allocation with TreeSet

The key requirement from class discussion was to use TreeSet per entry gate.

### Data structure

`NearestSlotStrategy` keeps:

- `Map<gateId, TreeSet<Slot>>`

Each TreeSet is sorted by:

1. distance from that gate
2. floor number (tie-breaker)
3. slot id (final deterministic tie-breaker)

### Why TreeSet here

TreeSet gives balanced-tree behavior and keeps elements ordered automatically.

- Nearest lookup follows sorted order.
- Remove/add for occupancy updates are efficient.
- Deterministic ordering avoids random allocation surprises.

### Occupy and free flow

When a slot is occupied:

- remove that slot from every gate's TreeSet

When a slot is freed:

- add that slot back to every gate's TreeSet

This keeps all gate views consistent.

### Compatibility rule handling

A slot is considered valid if:

- it is available
- it can fit the vehicle type
- it satisfies the requested minimum slot type

This supports:

- bike in small/medium/large
- car in medium/large
- bus only in large

And if a smaller requested type is unavailable, a larger compatible slot can still be chosen.

---

## 5) Billing design

`HourlyPricingStrategy` stores hourly rates keyed by slot type.

Billing steps:

1. Compute total parked minutes.
2. Convert to billable hours with ceiling behavior.
3. Enforce minimum 1 hour.
4. Multiply by the rate of allocated slot type.

Important detail:

We bill by allocated slot type (`ticket.getSlot().getSlotType()`), which exactly matches the requirement.

Example:

If a bike gets parked in a MEDIUM slot, MEDIUM rate applies.

---

## 6) API lifecycle walkthrough

### park()

- Validate/find entry gate.
- Ask strategy for nearest compatible slot.
- Mark slot occupied.
- Inform strategy (`onSlotOccupied`).
- Generate ticket through entry gate.
- Save ticket in active map.
- Return ticket.

### status()

- Scan all floors and count available slots by type.
- Return summary map.

### exit()

- Validate ticket and active presence.
- Compute amount via pricing strategy.
- Mark slot available.
- Inform strategy (`onSlotFreed`).
- Generate bill via exit gate.
- Return bill.

---

## 7) Why this design is practical for interviews and real code

This design is intentionally simple but extensible.

Simple because:

- plain entities (Vehicle, Slot, Ticket, Bill)
- single orchestrator (ParkingLot)
- clean API methods

Extensible because:

- strategy interfaces isolate volatile logic
- gate model can grow with gate-specific policy later
- slot metadata already supports distance-based behavior

It also maps directly to UML relationships:

- composition: ParkingLot -> Floor -> Slot
- association/aggregation: ParkingLot with EntryGate, ExitGate, strategies
- policy inheritance: interfaces with concrete strategy classes

---

## 8) Trade-offs and future improvements

Current trade-offs:

- In-memory data only; no persistence/recovery.
- No concurrency locks in this version.
- Distances are configured by factory, not computed from a geometry model.

Natural next upgrades:

- thread-safe occupancy updates
- persistent ticket store
- reservation support
- per-floor pricing overrides
- real distance matrix from gate to slot

Even with those limitations, the current model is strong enough for LLD evaluation because it cleanly separates entity state from policy behavior and satisfies all functional requirements.
