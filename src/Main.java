
import javax.swing.SwingUtilities;
import api.CarRentalService;
import gui.LoginFrame;

/**
 * Κεντρική κλάση εκκίνησης της εφαρμογής Car Rental Service.
 * Διαχειρίζεται την αρχικοποίηση των δεδομένων και την εκκίνηση του GUI.
 * * @author Ραφαέλα Σιαμπάνη
 */

public class Main {
    /**
     * Η κυρια μεθοδος
     * Εκτελει τη φορτωση των δεδομεωνω και εκκινει το γραφικο περιβαλλον
     * @param args Ορισματα γραμμης εντολων
     */

    public static void main(String[] args) {
        System.out.println("Εκκίνηση συστήματος ενοικίασης αυτοκινήτων...");

        //Ληψη του μοναδικου αντικειμενου διαχειρισης
        CarRentalService service = CarRentalService.getInstance();

        // Φόρτωση δεδομένων από τα αρχεία
        try {
            service.loadData();
            System.out.println("Τα δεδομένα φορτώθηκαν επιτυχώς.");
        } catch (Exception e) {
            System.err.println("Σφάλμα κατά τη φόρτωση δεδομένων: " + e.getMessage());
        }

        //Εκκινηση του GUI μέσω του Event Dispatch Thread για ασφαλεια νηματων
        SwingUtilities.invokeLater(() -> {
            // Δημιουργία και εμφάνιση της φόρμας Login
            LoginFrame login = new LoginFrame(service);
            login.setVisible(true);
        });
    }
}

