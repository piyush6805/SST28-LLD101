public class DoubleRoomFee implements FeeComponent {
    @Override
    public Money monthlyFee() {
        return new Money(15000.0);
    }
}
