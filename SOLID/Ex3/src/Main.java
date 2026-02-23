import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Placement Eligibility ===");

        // Rule thresholds from config
        RuleInput config = new RuleInput();

        // Rules evaluated in order; first failure short-circuits
        List<EligibilityRule> rules = List.of(
                new DisciplinaryFlagRule(),
                new CgrRule(config.minCgr),
                new AttendanceRule(config.minAttendance),
                new CreditsRule(config.minCredits)
        );

        EligibilityStore store = new FakeEligibilityStore();
        EligibilityEngine engine = new EligibilityEngine(rules, store);

        StudentProfile s = new StudentProfile("23BCS1001", "Ayaan", 8.10, 72, 18, LegacyFlags.NONE);
        engine.runAndPrint(s);
    }
}
