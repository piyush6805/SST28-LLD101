public class SingleRoomFee implements FeeComponent {
    @Override
    public Money monthlyFee() {
        return new Money(14000.0);
    }
}
