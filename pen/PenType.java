public enum PenType {
    BALLPOINT,
    FOUNTAIN,
    GEL;

    public static PenType from(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidPenConfigurationException("Pen type cannot be null or blank.");
        }

        String normalized = value.trim().toUpperCase();
        
        if ("BALLPOINT".equals(normalized)) {
            return BALLPOINT;
        } else if ("FOUNTAIN".equals(normalized)) {
            return FOUNTAIN;
        } else if ("GEL".equals(normalized)) {
            return GEL;
        }
        
        throw new InvalidPenConfigurationException("Unsupported pen type: " + value);
    }
}
