import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;
import java.util.List;

public class TryIt {

    public static void main(String[] args) {
        TicketService service = new TicketService();

        IncidentTicket t = service.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Created : " + t);

        IncidentTicket assigned  = service.assign(t, "agent@example.com");
        IncidentTicket escalated = service.escalateToCritical(assigned);

        System.out.println("\nOriginal (unchanged) : " + t);
        System.out.println("After assign         : " + assigned);
        System.out.println("After escalate       : " + escalated);

        List<String> tags = escalated.getTags();
        tags.add("HACKED_FROM_OUTSIDE");
        System.out.println("\nExternal list after add : " + tags);
        System.out.println("Ticket tags (safe)      : " + escalated.getTags());
    }
}
