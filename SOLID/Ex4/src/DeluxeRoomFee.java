public class DeluxeRoomFee implements FeeComponent {
    @Override
    public Money monthlyFee() {
        return new Money(16000.0);
    }
}
