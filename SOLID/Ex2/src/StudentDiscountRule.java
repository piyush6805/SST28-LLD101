public class StudentDiscountRule implements DiscountRule {
    @Override
    public double discountAmount(double subtotal, int distinctItems) {
        if (subtotal >= 180.0) return 10.0;
        return 0.0;
    }
}
