import java.util.*;
public class Printer {
    public void inputPrint(String raw){
        System.out.println("INPUT: " + raw);
    }
    public void errorPrint(List<String> errors){
        if (!errors.isEmpty()) {
            System.out.println("ERROR: cannot register");
            for (String e : errors) System.out.println("- " + e);
        }
    }
    public void confirmationPrint(StudentRecord rec, int totalStudents){
        System.out.println("OK: created student " + rec.id);
        System.out.println("Saved. Total students: " + totalStudents);
        System.out.println("CONFIRMATION:");
        System.out.println(rec);
    }
    public void finalPrint(FakeDb db){
        System.out.println();
        System.out.println("-- DB DUMP --");
        System.out.print(TextTable.render3(db));
    }
}