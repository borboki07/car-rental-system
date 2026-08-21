package api;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Currency;

/**αναπαριστα μια ενοικιαση αυτοκινητου
 * συνδεει τις οντοτητες car employee customer και κρατα τα δεδομενα χρονου
 * η κλαση υλοποιει το Serializable interface για την αποθηκευση/φορτωση σε αρχεια
 * @author Ελευθερια Μπορμποκη
 */
public class Rental implements Serializable {

    //πεδια
    private int rentalId;
    private Car car;
    private Customer customer;
    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean returned=false;

    /**
     * Κατασκευης της κλασης Rental με ολα τα στοιχεια του χρειαζεται
     * @param rentalId ο μοναδικος κωδικος ενοικιασης
     * @param car το αντικειμενο car που ενοικιαζεται
     * @param customer ο πελατης που κανει την ενοικιαση
     * @param employee ο υπαλληλος που καταχωρει την ενοικιαση
     * @param startDate Ημερομηνια εναρξης
     * @param endDate ημερομηνια ληξης
     */
    public Rental(int rentalId, Car car, Customer customer, Employee employee, LocalDate startDate, LocalDate endDate){
        this.rentalId=rentalId;
        this.car=car;
        this.customer=customer;
        this.employee=employee;
        this.startDate=startDate;
        this.endDate=endDate;
    }

    /**
     * Ελεγχει αν το αυτοκινητο εχει επιστραφει για τη συγκεκριμενη ενοικιαση
     * Χρησιμοποιειται απο το GUI για το φιλτραρισμα των ενεργων ενοικιασεων
     * @return true αν το οχημα εχει επιστραφει, false αν η ενοικιαση ειναι ακομα ενεργη
     */
    public boolean isReturned(){return returned;}

    /**
     * Επιστρεφει τον κωδικο ενοικιασης
     * @return ο κωδικος int
     */
    public int getRentalId(){return rentalId;}

    /**
     * Οριζει την κατασταση επιστροφης της ενοικιασης
     * Καταγραφη αν το αυτοκινητο παραδοθηκε στην εταιρια
     * @param returned Η κατασταση επιστροφης
     */
    public void setReturned(boolean returned){this.returned=returned;}

    /**
     * Επιστρεφει το αυτοκινητο που εχει ενοικιαστει
     * @return το αυτοκινητο car
     */
    public Car getCar(){return car;}

    /**
     * Επιστρεφει τον πελατη που εκανε την ενοικιαση
     * @return το αντικειμενο Customer
     */
    public Customer getCustomer(){return customer;}

    /**
     * Επιστρεφει τον υπαλληλο που καταχωρει την ενοικιαση
     * @return Το αντικειμενο Employee
     */
    public Employee getEmployee(){return employee;}

    /**
     * Επιστρεφει την ημερομηνια εναρξης ενοικιασης
     * @return Η ημερομηνια LocalDate
     */
    public LocalDate getStartDate(){return startDate;}

    /**
     * Επιστρεφει την ημερομηνια ληξης ενοικιασης
     * @return η ημερομηνια LocalDate
     */
    public LocalDate getEndDate(){return endDate;} //επιστρεφει την ημερομηνια ληξης


    /**
     * οριζει την ημερομηνια ληξης (για προωρη επιστρογη ή παραταση)
     * @param endDate η νεα ημερομηνια ληξης
     */
    public void setEndDate(LocalDate endDate){this.endDate=endDate;}

    /**
     * Επιστρεφει την αναπαρασταση String αντικειμενουν Rental για εμφανιση στο ιστορικο
     * @return String με τα βασικα στοιχεια ενοικιασης
     */
    @Override
    public String toString(){
        String status=returned ? "[ΕΠΙΣΤΡΑΦΗΚΕ]" : "[ΕΝΕΡΓΗ]";
       return String.format("%s ID: %d | Car: %s - Customer: %s | Dates: %s to %s",status,rentalId, car.getLicensePlate(), customer.getFullName(), startDate, endDate);
   }
}