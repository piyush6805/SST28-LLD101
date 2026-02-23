import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");
        BookingRequest req = new BookingRequest(LegacyRoomTypes.DOUBLE, List.of(AddOn.LAUNDRY, AddOn.MESS));

        FeeRegistry registry = FeeRegistry.createDefault();
        HostelFeeCalculator calc = new HostelFeeCalculator(registry);
        FeeResult result = calc.calculate(req);

        ReceiptPrinter.print(req, result);

        BookingRepo repo = new FakeBookingRepo();
        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000));
        repo.save(bookingId, req, result.monthly, result.deposit);
    }
}
