import java.time.Duration;
import java.time.LocalDateTime;

public class HourlyPricingStrategy implements PricingStrategy {
    // private final Map<SlotType, Double> hourlyRateBySlotType;

    // public HourlyPricingStrategy(double smallRate, double mediumRate, double largeRate) {
    //     this.hourlyRateBySlotType = new EnumMap<SlotType, Double>(SlotType.class);
    //     this.hourlyRateBySlotType.put(SlotType.SMALL, smallRate);
    //     this.hourlyRateBySlotType.put(SlotType.MEDIUM, mediumRate);
    //     this.hourlyRateBySlotType.put(SlotType.LARGE, largeRate);
    // }

    // @Override
    // public double calculateAmount(Ticket ticket, LocalDateTime exitTime) {
    //     if (ticket == null || exitTime == null) {
    //         throw new IllegalArgumentException("ticket and exitTime are required");
    //     }

    //     long minutes = Duration.between(ticket.getEntryTime(), exitTime).toMinutes();
    //     if (minutes < 0) {
    //         throw new IllegalArgumentException("exitTime cannot be before entryTime");
    //     }

    //     long billableHours = Math.max(1, (minutes + 59) / 60);

    //     SlotType billedSlotType = ticket.getSlot().getSlotType();
    //     double hourlyRate = hourlyRateBySlotType.get(billedSlotType);
    //     return billableHours * hourlyRate;
    // }
    @Override
    public double calculateAmount(Ticket ticket, LocalDateTime exitTime) {
        long hours = Duration.between(ticket.getEntryTime(), exitTime).toHours() + 1;

        switch (ticket.getSlot().getSlotType()) {
            case SMALL: return hours * 10;
            case MEDIUM: return hours * 20;
            case LARGE: return hours * 30;
        }
        return 0;
    }
}
