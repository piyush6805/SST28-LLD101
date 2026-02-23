
public class ReceiptPrinter {
    public static void print(BookingRequest req, FeeResult result) {
        System.out.println("Room: " + LegacyRoomTypes.nameOf(req.roomType) + " | AddOns: " + req.addOns);
        System.out.println("Monthly: " + result.monthly);
        System.out.println("Deposit: " + result.deposit);
        System.out.println("TOTAL DUE NOW: " + result.total());
    }
}
