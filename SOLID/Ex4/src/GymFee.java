public class GymFee implements FeeComponent {
    @Override
    public Money monthlyFee() {
        return new Money(300.0);
    }
}
