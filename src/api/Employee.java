package api;

import java.io.Serializable;

/**
 * αναπαριστα εναν υπαλληλο/χρηστη, κληρονομει τα βασικα στοιχεια ταυτοτητας απο την κλαση Person
 * και η κλαση υλοποιει το Serializable interface
 * @author Ελευθερια Μπορμποκη
 */
public class Employee extends Person implements Serializable{
    //Ιδιωτικο πεδιο της κλασης Employee
    //Αυτα αποθηκευουν τα στοιχεια συνδεσης του υπαλληλου login credentials
    private String username;
    private String password;

    /**
     * Κατασκευαστης της κλασης Employee
     * Αρχικοποιει τα πεδια του Employee και της γονικης κλασης Person
     * @param username το ονομα χρηστη για τη συνδεση
     * @param password ο κωδικος προσβασης
     * @param fullName το πληρες ονομα του υπαλληλου
     * @param email η ηλεκτρονικη διευθυνση του υπαλληλου
     */
    public Employee(String username, String password, String fullName, String email){
        super(fullName, email);
        this.username=username;
        this.password=password;
    }

    //Μεθοδος για την ανακτηση του ονοματος του χρηστη
    public String getUsername(){ return username;}

    //Μεθοδος ανακτησης του κωδικου προσβασης
    public String getPassword(){return password;}

    /**
     * Μεθοδος για αλλαγη κωδικου προβασης
     * @param password ο νεος κωδικος προσβασης
     */
    public void setPassword(String password){this.password=password;} //οριζει νεο κωδικο προσβασης

    /**
     * Υπερκαλυπτει την μεθοδο toString της κλασης Object
     * Επιστρεφει μια περιγραφη του αντικειμενου Employee σε μορφη String
     * Χρησιμοποιωντας το ονομα χρηστη και το πληρες ονομα fullName
     * Δεν επιστρεφει τον κωδικο προσβασης
     */
    @Override
    public String toString(){
        return String.format("Employee [Username: %s | Ονομα: %s]",username,getFullName());
    }

}
