package api;
import java.io.Serializable;

/**
 * η κλαση για ολα τα ατομα του συστηματος
 * περιλαμβανει κοινα στοιχεια ταυτοτητας
 * @author Ελευθερια Μπορμποκη
 */
public  class Person implements Serializable{

    private String fullName;
    private String email;

    /**
     * @param fullName το ονοματεπωνυμο
     * @param email του ατομου
     */
    public Person(String fullName, String email){
        this.fullName=fullName;
        this.email=email;
    }

    /**
     * επιστρεφει το πληρες ονομα
     * @return ονοματεπωνυμο
     */
    public String getFullName(){
        return fullName;
    }

    /**
     * επιστρεφει μειλ
     * @return email
     */
    public String getEmail(){
        return email;
    }

    /**
     * οριζει ονοματεπωνυμο
     * @param fullName το ονοματεπωνυμο
     */
    public void setFullName(String fullName){
        this.fullName=fullName;
    }

    /**
     * οριζει νεο email
     * @param email το μειλ
     */
    public void setEmail(String email) {
        this.email = email;
    }
}