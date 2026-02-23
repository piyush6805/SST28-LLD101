public class MessFee implements FeeComponent {
    @Override
    public Money monthlyFee() {
        return new Money(1000.0);
    }
}
