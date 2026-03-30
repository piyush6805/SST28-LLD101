public class CardPaymentService implements PaymentService {

    @Override
    public PaymentStatus processPayment(User user, double amount) {
        System.out.println("Processing CARD payment of " + amount);
        return PaymentStatus.SUCCESS;
    }
}