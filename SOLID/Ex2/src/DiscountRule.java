public interface DiscountRule {
    double discountAmount(double subtotal, int distinctItems);
}
