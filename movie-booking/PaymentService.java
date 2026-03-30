public interface PaymentService {
    PaymentStatus processPayment(User user, double amount);
}