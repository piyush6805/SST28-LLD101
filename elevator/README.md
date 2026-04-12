# Elevator Management System - LLD Design

A comprehensive low-level design (LLD) implementation of a multi-elevator management system with advanced scheduling strategies, maintenance capabilities, and safety features.

## System Features

### Core Features
- **Multiple Elevator Management**: Support for multiple elevators operating simultaneously
- **Intelligent Request Scheduling**: Multiple scheduling strategies for optimal elevator assignment
- **Request Queuing**: Handles scenarios when all elevators are busy
- **Real-time Status Monitoring**: Track elevator state, floor, load, and queue information

### Safety & Capacity Features
- **Weight Capacity Management**: Enforces maximum weight limits with alarm triggers
- **Door Safety**: Prevents door closure when overloaded
- **Emergency Alarm**: Immediate stop at nearest floor with alarm
- **Maintenance Support**: Mark elevators as under maintenance to exclude from service

### Operational Features
- **Floor Maintenance**: Disable up/down buttons on floors during maintenance
- **Elevator Maintenance**: Complete elevator isolation during maintenance
- **Load Tracking**: Real-time tracking of passenger weight
- **Request Queue Management**: Smart queuing when no elevators are available

### Button Controls
**Floor Panels (Outside Elevator)**:
- UP button: Request elevator going up
- DOWN button: Request elevator going down

**Elevator Panels (Inside Elevator)**:
- Floor buttons (1-N): Select destination floor
- Open Door button: Manually open doors
- Close Door button: Manually close doors
- Emergency button: Trigger emergency stop and alarm

## Architecture

### Key Classes

#### Elevators
- **ElevatorCar**: Represents a single elevator
  - Manages current floor, direction, state
  - Handles requests and movement
  - Tracks weight capacity
  - Supports maintenance and emergency modes

#### Controller
- **ElevatorController**: Central system coordinator
  - Routes requests to elevators using strategies
  - Manages system-wide scheduling
  - Handles maintenance operations
  - Queues pending requests

#### Panels
- **ElevatorPanel**: Inside elevator control panel
  - Floor selection
  - Door control
  - Emergency button
  - Status monitoring

- **FloorPanel**: Outside elevator call panel
  - Up/Down buttons
  - Floor maintenance support
  - Request submission

#### State Management
- **ElevatorState**: IDLE, MOVING, MAINTENANCE, EMERGENCY
- **Direction**: UP, DOWN, IDLE

#### Strategy Pattern
- **SchedulerStrategy** (Interface)
  - **NearestElevatorStrategy**: Selects closest elevator
  - **WaitingTimeOptimizationStrategy**: Minimize user wait time
  - **EnergyEfficientStrategy**: Minimize energy consumption

## Usage Example

```java
// Create controller with scheduling strategy
ElevatorController controller = new ElevatorController(
    new WaitingTimeOptimizationStrategy()
);

// Create and register elevators
ElevatorCar e1 = new ElevatorCar("ELEVATOR-A", 750); // 750 kg capacity
ElevatorCar e2 = new ElevatorCar("ELEVATOR-B", 750);
controller.addElevator(e1);
controller.addElevator(e2);

// Create floor panels
FloorPanel floor5 = new FloorPanel(5, controller);

// Request elevator
floor5.pressUp();

// Inside elevator
ElevatorPanel panel = new ElevatorPanel(e1);
panel.pressFloor(10);
panel.closeDoor();

// Simulate system
controller.simulate(10);  // Run 10 simulation steps
```

## Scheduling Strategies

### 1. Nearest Elevator Strategy
- Selects elevator closest to request floor
- Simple and predictable
- Good for balanced load distribution

### 2. Waiting Time Optimization Strategy
- Prioritizes idle elevators
- Minimizes user wait time
- Considers queue size for moving elevators

### 3. Energy Efficient Strategy
- Calculates energy cost (distance + direction change + load)
- Selects elevator with lowest total cost
- Best for minimizing power consumption

## Design Patterns Used

### 1. Strategy Pattern
- Multiple scheduling algorithms
- Pluggable strategies via constructor
- Easy to add new algorithms

### 2. State Pattern
- Elevator states (IDLE, MOVING, MAINTENANCE, EMERGENCY)
- Different behaviors based on state

### 3. Observer Pattern
- Door operations trigger state changes
- Load changes affect close door behavior

## Key Design Decisions

### 1. TreeSet for Requests
- Maintains sorted order of floor requests
- Efficient element removal
- Natural SCAN algorithm support

### 2. Request Queuing
- Handles overload gracefully
- Queued requests processed when elevators available
- Prevents request loss

### 3. Maintenance Isolation
- Elevators under maintenance don't accept requests
- Ensures safety and prevents usage during repair
- Transparent to request handling

### 4. Weight Validation
- Prevents door closure if overloaded
- Triggers audible alarm
- Protects elevator cable system

## Extensibility

The system is designed for easy enhancement:

1. **New Strategies**: Implement `SchedulerStrategy` interface
2. **New States**: Add to `ElevatorState` enum
3. **New Features**: Extend `ElevatorCar` or `ElevatorController`
4. **More Buttons**: Extend `ElevatorPanel` or `FloorPanel`

## Simulation Features

The system includes comprehensive simulation capabilities:

```java
// Single step
controller.step();

// Multiple steps with status
controller.simulate(10);

// Check status
controller.printStatus();

// Get available elevators
List<ElevatorCar> available = controller.getAvailableElevators();
```

## Requirements Met

✅ Multiple elevator operation
✅ Up/Down buttons on each floor
✅ Floor buttons inside elevator
✅ Efficient task assignment
✅ Waiting time minimization
✅ Energy consumption optimization
✅ Pluggable scheduling strategies
✅ Elevator maintenance support
✅ Floor maintenance support
✅ Open/Close/Emergency buttons
✅ Weight limit enforcement with alarm
✅ Multiple elevator states
✅ Real-time system monitoring
✅ Request queue management

## Performance Characteristics

- **Time Complexity**
  - Strategy selection: O(n) where n = number of elevators
  - Request assignment: O(1) amortized
  - Step simulation: O(n) where n = number of elevators

- **Space Complexity**
  - O(n*m) where n = elevators, m = average request queue size
  - Memory efficient for typical building scenarios

## Testing Scenarios

The Main.java includes comprehensive test scenarios:

1. **Normal Operations**: Basic request and movement
2. **Weight Limit Testing**: Overload scenarios
3. **Elevator Maintenance**: Removing elevator from service
4. **Floor Maintenance**: Disabling floor buttons
5. **Emergency Alarm**: Emergency stop functionality
6. **Request Queuing**: Multiple requests with limited elevators
7. **Multiple Strategies**: Comparing different scheduling approaches
