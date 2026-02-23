public class TripleRoomFee implements FeeComponent {
    @Override
    public Money monthlyFee() {
        return new Money(12000.0);
    }
}
