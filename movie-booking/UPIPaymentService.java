public class UPIPaymentService implements PaymentService {
    public PaymentStatus processPayment(User user, double amount) {
        System.out.println("UPI payment: " + amount);
        return PaymentStatus.SUCCESS;
    }
}