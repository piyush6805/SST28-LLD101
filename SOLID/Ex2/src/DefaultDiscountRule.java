public class DefaultDiscountRule implements DiscountRule {
    @Override
    public double discountAmount(double subtotal, int distinctItems) {
        return 0.0;
    }
}
