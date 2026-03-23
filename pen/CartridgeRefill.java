public class CartridgeRefill implements RefillStrategy {
    @Override
    public void refill(Pen pen, String newColor) {
        String color = normalizeColor(newColor);
        pen.setInkColor(color);
        pen.setInkLevel(Pen.MAX_INK_LEVEL);
        System.out.println("Cartridge refilled to 100% with color: " + color);
    }

    private String normalizeColor(String color) {
        if (color == null || color.trim().isEmpty()) {
            throw new InvalidPenConfigurationException("Refill color cannot be null or blank.");
        }
        return color.trim();
    }
}
