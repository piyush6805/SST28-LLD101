public class StaffDiscountRule implements DiscountRule {
    @Override
    public double discountAmount(double subtotal, int distinctItems) {
        if (distinctItems >= 3) return 15.0;
        return 5.0;
    }
}
