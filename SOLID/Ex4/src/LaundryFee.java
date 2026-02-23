public class LaundryFee implements FeeComponent {
    @Override
    public Money monthlyFee() {
        return new Money(500.0);
    }
}
