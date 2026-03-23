public enum StartMechanism {
    CAP,
    CLICK;

    public static StartMechanism from(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidPenConfigurationException("Start mechanism cannot be null or blank.");
        }

        String normalized = value.trim().toUpperCase();
        
        if ("CAP".equals(normalized)) {
            return CAP;
        } else if ("CLICK".equals(normalized)) {
            return CLICK;
        }
        
        throw new InvalidPenConfigurationException("Unsupported start mechanism: " + value);
    }
}
