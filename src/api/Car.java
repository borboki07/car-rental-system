package api;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.Serializable;

/**
 * Η αναπαρασταση ενος αυτοκινητου που μπορει να ενοικιαστει
 * Η κλαση υλοποιει το Serializable interface για την αποθηκευση και φορτωση σε αρχεια
 * @author Ελευθερια Μπορμποκη
 */


public class Car implements Serializable{
    //Πεδια
    private int id; // κωδικος αυτοκινητου
    private String licensePlate; //Η πινακιδα του αυτοκινητου
    private String brand; // Η μαρκα του αυτοκινητου
    private String type; // ο τυπος αυτοκινητου
    private String model; //Το συγκεκριμενο μοντελο
    private int year; //Ετος κατασκευης αυτοκινητου
    private String color; // Το χρωμα του αυτοκινητου
    private CarStatus status; // Κατασταση Διαθεσιμο ή ενοικιασμενο


    /**
     * Κατασκευαστης της κλασης Car
     * @param id ο μοναδικος κωδικος του αυτοκινητου
     * @param licensePlate Η πινακιδα του αυτοκινητου
     * @param brand Η μαρκα του αυτοκινητου
     * @param type Ο τυπος αυτοκινητου
     * @param model Το μοντελο του αυτοκινητου
     * @param year Το ετος κατασκευης
     * @param color Το χρωμα του αυτοκινητου
     * @param status Η αρχικη κατασταση απο CarStatus
     */

    public Car(int id, String licensePlate, String brand, String type, String model, int year, String color, CarStatus status){
        this.id=id;
        this.licensePlate=licensePlate;
        this.brand=brand;
        this.type=type;
        this.model=model;
        this.year=year;
        this.color=color;
        this.status=status;
    }

    /**
     * Επιστρεφει τον μοναδικο κωδικο του αυτοκινητου
     * @return ID του αυτοκινητου int
     */
    public int getId(){return id;}

    /**
     * Επιστρεφει την πινακιδα του αυτοκινητου
     * @return πινακιδα String
     */
    public String getLicensePlate(){ return licensePlate;}

    /**
     * Επιστρεφει την μαρκα του αυτοκινητου
     * @return η μαρκα String
     */
    public String getBrand(){return brand;}

    /**
     *Επιστρφει τον τυπο του αυτοκινητου
     * @return ο τυπος String
     */
    public String getType(){return type;}

    /**
     * Επιστρφει το μοντελο του αυτοκινητου
     * @return το μοντελο String
     */
    public String getModel(){return model;}

    /**
     * Επιστρφει το ετος κατασκευης
     * @return το ετος int
     */
    public int getYear(){return year;}

    /**
     * Επιστρεφει το χρωματ του αυτοκινητου
     * @return Το χρωμα String
     */
    public String getColor(){return color;}

    /**
     * Επιστρεφει την τρεχουσα κατασταση του αυτοκινητου
     * @return Η κατασταση CarStatus enum
     */
    public CarStatus getStatus(){return status;}


    /**
     * οριζει την νεα μαρκα αυτοκινητου
     * @param brand η νεα μαρκα
     */
    public void setBrand(String brand){this.brand=brand;}

    /**
     * οριζει τον νεο τυπο αυτοκινητου
     * @param type ο νεος τυπος
     */
    public void setType(String type){this.type=type;}

    /**
     * οριζει το νεο μοντελο του αυτοκινητου
     * @param model το νεο μοντελο
     */
    public void setModel(String model){this.model=model;}

    /**
     * Οριζει το νεο ετος κατασκευης
     * @param year το νεο ετος κατασκευης
     */
    public void setYear(int year){this.year=year;}

    /**
     * Οριζει το νεο χρωματ του αυτοκινητου
     * @param color Το νεο χρωμα
     */
    public void setColor(String color){this.color=color;}

    /**
     * Οριζει τη νεα κατασταση του αυτοκινητου
     * @param status η νεα κατασταση (CarStatus enum)
     */
    public void setStatus(CarStatus status){this.status=status;}


    /**
     * Ελεγχει αν το αυτοκινητο ειναι διαθεσιμο για ενοικιαση
     * @return true αν η κατασταση ειναι AVAILABLE, false διαφορετικα
     */
    public boolean isAvailable(){
        return this.status==CarStatus.AVAILABLE;
    }


    /**
     * Επιστρεφει την αναπαρασταση string του αντικειμενου car για εκτυπωση
     * @return String με τις βασικες πληροφοριες του αυτοκινητου
     */
    @Override
    public String toString(){
        return "Car[ID:"+id+
                ",Πινακιδα:"+ licensePlate+
                ", Μαρκα:"+brand+
                ", Μοντελο"+model+
                ", Κατασταση"+status.toString()+
                "]";
    }
}

