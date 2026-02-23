public interface EligibilityRule {
    /** Returns null if the student passes; otherwise returns the failure reason string. */
    String check(StudentProfile s);
}
