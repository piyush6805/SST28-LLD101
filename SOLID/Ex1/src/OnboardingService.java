import java.util.*;

public class OnboardingService {
    private final FakeDb db;
    private final Parser parser = new Parser();
    private final Validator validator = new Validator();
    private final Printer printer = new Printer();

    public OnboardingService(FakeDb db) { this.db = db; }

    // Intentionally violates SRP: parses + validates + creates ID + saves + prints.
    public void registerFromRawInput(String raw) {
        // System.out.println("INPUT: " + raw);
        printer.inputPrint(raw);
        // Map<String,String> kv = new LinkedHashMap<>();
        // String[] parts = raw.split(";");
        // for (String p : parts) {
        //     String[] t = p.split("=", 2);
        //     if (t.length == 2) kv.put(t[0].trim(), t[1].trim());
        // }
        Map<String,String> kv = parser.parse(raw);
        // String name = kv.getOrDefault("name", "");
        // String email = kv.getOrDefault("email", "");
        // String phone = kv.getOrDefault("phone", "");
        // String program = kv.getOrDefault("program", "");

        // // validation inline, printing inline
        // List<String> errors = new ArrayList<>();
        // if (name.isBlank()) errors.add("name is required");
        // if (email.isBlank() || !email.contains("@")) errors.add("email is invalid");
        // if (phone.isBlank() || !phone.chars().allMatch(Character::isDigit)) errors.add("phone is invalid");
        // if (!(program.equals("CSE") || program.equals("AI") || program.equals("SWE"))) errors.add("program is invalid");
        List<String> errors = validator.validate(kv);
        if (!errors.isEmpty()) {  
            printer.errorPrint(errors);
            return;
        }

        String id = IdUtil.nextStudentId(db.count());
        StudentRecord rec = new StudentRecord(id, kv.get("name"), kv.get("email"), kv.get("phone"), kv.get("program"));

        db.save(rec);

        // System.out.println("OK: created student " + id);
        // System.out.println("Saved. Total students: " + db.count());
        // System.out.println("CONFIRMATION:");
        // System.out.println(rec);
        printer.confirmationPrint(rec, db.count());
    }
}
