package api;

import java.io.Serializable;

/**
 * αναπαριστα εναν πελατης της επιχειρησης, κληρονομει τα στοιχει αυτοτητας απο την κλαση Person
 * η κλαση υλοποιει το Serializable interface για αποθηκευση/φορτωση σε αρχεια
 * @author Ελευθερια Μπορμποκη
 */
public class Customer extends Person implements Serializable{

    //Πεδια
    private String afm; //Μοναδικος αριθμος μητρωου ΑΦΜ
    private String phoneNumber; //Τηλεφωνο επικοιωνιας

    /**
     * κατασκευαστης κλασης Customer
     * @param afm ο μοναδικος αριθμος μητρωου ΑΦΜ
     * @param fullName Το ονοματεπωνυμο του πελατη
     * @param phoneNumber το τηλεφωνο επικοιωνιας
     * @param email το email του πελατη
     */
    public Customer(String afm, String fullName, String phoneNumber, String email){
        super(fullName, email);
        this.afm=afm;
        this.phoneNumber=phoneNumber;
    }

    /**
     * Επιστρφει το μοναδικο ΑΦΜ του πελατη
     * @return το ΑΦΜ String
     */
    public String getAfm(){return afm;}

    /**
     * Οριζει το νεο μοναδικο ΑΦΜ του πελατη
     * @param afm Το νεο ΑΦΜ
     */
    public void setAfm(String afm){this.afm=afm;}

    /**
     * Επιστρεφει το τηλεφωνο επικοιωνιας του πελατη
     * @return το τηλεφωνο String
     */
    public String getPhoneNumber(){return phoneNumber;}

    /**
     * Οριζει το νεο τηλεφωνο επικοιωνιας του πελατη
     * @param phoneNumber το νεο τηλεφωνο
     */
    public void setPhoneNumber(String phoneNumber){ this.phoneNumber=phoneNumber;}

    /**
     * Επιστρεφει την αναπαρασταση String του αντικειμενου Customer
     * @return Μορφοποιηση String με τα βασικα στοιχεια του πελατη
     */
    @Override
    public String toString(){
        return String.format("Customer [Ονομα: %s | ΑΦΜ: %s | Τηλ: %s]",getFullName(), afm, phoneNumber);
    }
}
