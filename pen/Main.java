public class Main {
    public static void main(String[] args) {
        Pen ballpoint = PenFactory.getPen("ballpoint", "Blue", "click");
        Pen fountain = PenFactory.getPen("fountain", "Black", "cap");
        Pen gel = PenFactory.getPen("gel", "Red", "click");

        ballpoint.start();
        ballpoint.write("Hello from ballpoint");
        ballpoint.close();

        fountain.start();
        fountain.write("Fountain pen are elegant");
        fountain.close();

        gel.start();
        gel.write("Smooth gel writing");
        gel.close();

        ballpoint.refill("Purple");
        ballpoint.start();
        ballpoint.write("Refilled with new color");
        ballpoint.close();
    }
}
