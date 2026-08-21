package api;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Κεντρικη κλαση διαχειρισης του συστηματος ενοικιασης αυτοκινητων Car Rental Service
 * Αναλαμβανει την διαχειριση ολων των δεδομενων (αυτοκινητα πελατες υπαλληλοι ενοικιασης)
 * καθως και τις λειτουργιες αποθηκευσης.φορτωσης δεδομενων και αρχικοποιησηε του συστηματος
 * @author Ελευθερια Μπορμποκη
 */

public class CarRentalService {
    //Δημιουργια στατικης μεταβλητης που κραταει το μοναδικο αντικειμενο
    private static CarRentalService instance;

    //κεντρικες λιστες δεδομενων που αποθηκευουν ολα τα αντικειμενα του συστηματος
    private ArrayList<Car> cars;
    private ArrayList<Customer> customers;
    private ArrayList<Employee> employees;
    private ArrayList<Rental> rentals;

    /**
     * Κατασκευαστης της κλασης CarRentalService
     * Αρχικοποιει ολες τις κεντρικες λιστες δεδομενων ως κενες ArrayLists
     */
    private CarRentalService() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    /**
     * Στατικη μεθοδος getInstance() που καλει η main
     * @return
     */
    public static CarRentalService getInstance(){
        if(instance==null){
            instance=new CarRentalService();
        }
        return instance;
    }

    /**
     * Φορτωνει τα αρχικα δεδομενα των υπαλληλων απο ενα αρχειο CSV
     * @param fileName το ονομα του αρχειο
     */
    private void loadEmployeesFromCSV(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); //παραβλεψει γραμμης κεφαλιδας
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                //Ελεγχος για επαρκη δεδομενα και πραβλεψει γραμμης κεφαλιδας σε περιπτωση λαθους
                if (data.length >= 5 && !data[0].equalsIgnoreCase("name")) {
                    String fullName = data[0] + " " + data[1];
                    Employee newEmployee = new Employee(
                            data[2], //username
                            data[4], //password
                            fullName,
                            data[3] //email
                    );
                    this.employees.add(newEmployee);
                }
            }
        } catch (IOException e) {
            System.err.println("Σφάλμα κατα τη φόρτωση του αρχειου χρηστών: " + e.getMessage());
        }
    }


    /**
     * Φορτωνει τα αρχικα δεδομενα των αυτοκινητων απο ενα αρχειο CSV
     * @param fileName το ονομα του αρχειου CSV
     */
    private void loadCarsFromCSV(String fileName){
        try(BufferedReader br=new BufferedReader(new FileReader(fileName))){
            String line;
            br.readLine(); //παραβλεψει γραμμης κεφαλιδας

            while ((line=br.readLine()) !=null){
                String[] data=line.split(",");

                if(data.length>=8){
                    //μετατροπη των String πεδιων σε αριθμητικους τυπους
                    int id=Integer.parseInt(data[0].trim());
                    int year=Integer.parseInt(data[5].trim());

                    //μετατροπη της ελληνικης περιγραφης σε CarStatuw Enum
                    CarStatus status=convertStatus(data[7].trim());

                    Car newCar=new Car(
                            id,
                            data[1].trim(), //πινακιδα
                            data[2].trim(), //μαρκα
                            data[3].trim(), //τυπος
                            data[4].trim(), //μοντελο
                            year,
                            data[6].trim(), //χρωμα
                            status
                    );
                    this.addCar(newCar);
                }
            }
        // Χειρισμος σφαλματων εισοδου/εξοδου και μορφοποιησης αριθμων
        }catch(IOException | NumberFormatException e){
            System.err.println("Σφάλμα κατα την φόρτωση του αρχείου οχημάτων: "+e.getMessage());
        }
    }

    /**
     * Μετατροπη της ελληνικης περιγραφης της καταστασης αυτοκινητου σε CarStatus Enum
     * @param greekStatus η ελληνικη συμβολοσειρα καταστασης
     * @return το αντιστοιχο CarStatus enum
     * @throws IllegalArgumentException αν η κατασταση δεν αναγνωριζεται
     */
    private CarStatus convertStatus(String greekStatus){
        if(greekStatus.equalsIgnoreCase("Διαθέσιμο")){
            return CarStatus.AVAILABLE;
        }else if (greekStatus.equalsIgnoreCase("Ενοικιασμενο")){
            return CarStatus.RENTED;
        }else{
            throw new IllegalArgumentException("Αγνωστη κατάσταση αυτοκινητου: "+ greekStatus);
        }
    }

   //σταθερες για αρχεια CSV αρχικη φορτωση
    private static final String USERS_CSV="users.csv";
    private static final String VEHICLES_CSV="vehicles_with_plates.csv";

    /**
     * Εκτελει την αρχικοποιηση του προγραμματος (καλειται μονο αν δεν βρεθουν αποθηκευμενα αρχεια)
     * Φορτωνει τα αρχικα δεδομενα απο τα CSV,δημιουργει αρχικους πελατες και αποθηκευει τα δεδομενα
     */
    private void initializeData(){
        System.out.println("Αρχικοποιηση προγραμματος. Φορτωση αρχικων δεδομενων...");
        loadEmployeesFromCSV(USERS_CSV);
        loadCarsFromCSV(VEHICLES_CSV);

        //προσθηκη 2 καταχωρημενων πελατων
        this.customers.add(new Customer("100000000", "Νικος Παπαδοπουλος","6970123456","nikos.p@test.gr"));
        this.customers.add(new Customer("100000001","Μαρια Γεωργιου", "6970654321","maria.g@test.gr"));

        System.out.println("Επιτυχης αρχικοποιηση. Συνολικα Αυτοκινητα: "+cars.size()+", Υπάλληλος: "+ employees.size());
        saveData(); //αποθηκευει τα δεδομενα για δημουργηθουν τα αρχεια
    }

    //αρχικες για αρχεια Serialization (.ser)
    private static final String CAR_FILE="cars.ser";
    private static final String CUSTOMER_FILE="customers.ser";
    private static final String EMPLOYEE_FILE="employees.ser";
    private static final String RENTAL_FILE="rentals.ser";

    //αποθηκευει ολες τις λιστες δεδομεων σε σειριακα αρχειων
    //καταγραφει την τρεχουσα κατασταση του συστηματος πριν τον τερματισμο
    public void saveData(){
        //Αποθηκευση Employee
        try (ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(EMPLOYEE_FILE))){
            oos.writeObject(employees);
        }catch (IOException e){
            System.err.println("Σφάλμα κατα την παοθηκευση των Employees: "+e.getMessage());
        }

        //Αποθηκευση Customer
        try (ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(CUSTOMER_FILE))){
            oos.writeObject(customers);
        }catch (IOException e){
            System.err.println("Σφάλμα κατα την αποθήκευση των Customers: "+e.getMessage());
        }

        //Αποθηκευση Cars
        try (ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(CAR_FILE))){
             oos.writeObject(cars);
        }catch(IOException e){
            System.err.println("Σφάλμα κατα την αποθήκευση Cars: "+ e.getMessage());
        }

        //Αποθηκευση Rentals
        try (ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(RENTAL_FILE))){
            oos.writeObject(rentals);
        }catch (IOException e){
            System.err.println("Σφάλμα κατα την αποθήκευση των Rentals: "+ e.getMessage());
        }
        System.out.println("Επιτυχης αποθήκευση δεδομενων.");

    }

    /**
     * Φορτωνει τα δεδομενα απο τα σειριακα αρχεια και την εκκινηση του προγραμματος
     * Αν δεν βρεθουν αποθηκευμενα αρχεια καλει την μεθοδο initializeData()
     */
    public void loadData(){
        File employeeFile=new File(EMPLOYEE_FILE);
        //Ελεγχει αν υπαρχει ηδη το αρχειο των υπαλληλων
        if(employeeFile.exists()){
            System.out.println("Βρέθηκαν αποθηκευμενα δεδομενα. Φορτωση...");

            //φορτωση Employee
            try (ObjectInputStream ois=new ObjectInputStream(new FileInputStream(EMPLOYEE_FILE))){
                this.employees=(ArrayList<Employee>) ois.readObject();
            }catch (IOException | ClassNotFoundException e){
                System.err.println("Σφάλμα φορτωσης Employees: "+e.getMessage());
            }

            // Φορτωση Customer
            try (ObjectInputStream ois= new ObjectInputStream(new FileInputStream(CUSTOMER_FILE))){
                this.customers=(ArrayList<Customer>) ois.readObject();
            }catch (IOException | ClassNotFoundException e){
                System.err.println("Σφάλμα φόρτωσης Customers: "+ e.getMessage());
            }

            //Φορτωση Cars
            try(ObjectInputStream ois= new ObjectInputStream(new FileInputStream(CAR_FILE))){
                this.cars=(ArrayList<Car>) ois.readObject();
            }catch (IOException | ClassNotFoundException e){
                System.err.println("Σφάλμα φόρτωσης Cars: "+e.getMessage());
            }

            //Φορτωση Rentals
            try (ObjectInputStream ois=new ObjectInputStream(new FileInputStream(RENTAL_FILE))){
                this.rentals=(ArrayList<Rental>) ois.readObject();
            }catch (IOException|ClassNotFoundException e){
                System.err.println("Σφάλμα φόρτωσης Rentals: "+e.getMessage());
            }


        }else{
            //Αν δεν βρεθουν αρχεια εκτελειται αρχικοποιηση
            initializeData();
        }

    }

    /**
     * ελεγχει τα credentials του χρηστη και τον συνδεει στο συστημα
     * @param username το ονομα χρηστη
     * @param password ο κωδικος προσβασης
     * @return το αντικειμενο Employee αν η συνδεση ειναι επιτυχης αλλιως null
     */
    public Employee login(String username, String password){
        //Αρχικος ελεγχος για κενα ή null στοιχεια συνδεσης
        if(username== null || password==null || username.isEmpty() || password.isEmpty()){
            return null;
        }

        //Αναζητηση του υπαλληλου στην λιστα με βαση το username
        for (Employee employee : this.employees){
            if(employee.getUsername().equalsIgnoreCase(username)){
                //Ελεγχος κωδικου προσβασης
                if(employee.getPassword().equals(password)){
                    return employee; //επιτυχης προσβαση
                }
            }
        }
        return null; // Αποτυχια συνδεσης
    }
    public boolean plateExists(String plate){
        if(plate==null || plate.trim().isEmpty()) return false;

        for (Car car: this.cars){
            if (car.getLicensePlate().equalsIgnoreCase(plate.trim())){
                return true; //c=car
            }
        }
        return false;
    }
    /**
     * Προσθετει ενα νεο αντικειμενο στο συστημα ελεγχοντας για μοναδικοτητα ID και πιναδικες
     * @param car το αυτοκινητο Car προς προσθηκη
     * @return true αν η προσθηκη ειναι επιτυχης, false αλλιως
     */
    public boolean addCar(Car car){
        if(car==null) return false;

        //Ελεγχοε για διπλο ID ή διπλη πινακιδα
        for (Car existingCar : this.cars){
            if(existingCar.getId()==car.getId()){
                System.err.println("Αποτυχια προσθηκης: Υπαρχει ηδη αυτοκινητο με το ID: "+car.getId());
                return false;
            }

            if(existingCar.getLicensePlate().equalsIgnoreCase(car.getLicensePlate())){
                System.err.println(("Αποτυχια προσθηκης: Υπαρχει ηδη αυτοκινητο με Πινακιδα: "+car.getLicensePlate()));
                return false;
            }
        }
        this.cars.add(car); //Προσθηκη
        saveData(); //Αποθηκευση αλλαγων
        return true;
    }

    /**
     *Ενημερωνει τα στοιχεια ενος υπαρχοντος αυτοκινητου, βρισκοντας το με το ID
     * @param updatedCar το αντικειμενο Car με τα ενημερωμενα στοιχεια
     * @return true αν η ενημερωση ειναι επιτυχης, false αν δεν βρεθει το αυτοκινητο
     */
    public boolean updateCar(Car updatedCar){
        for(int i=0; i<cars.size(); i++){
            Car c=cars.get(i);
            if(c.getId()==updatedCar.getId()){
                cars.set(i,updatedCar); //Αντικατασταση του παλιου αντικειμενου με το ενημερωμενο
                saveData();
                return true;
            }
        }
        return false; // το αυτοκινητο δεν βρεθηκε
    }

    /**
     * Αναζητα αυτοκινητα με βαση την μαρκα το μοντελο και την κατασταση
     * Οι παραμετροι μπορει να ειναι null ή κενες για να παραλειφθει ο αντιστοιχος ελεγχος
     * @param brand η μαρκα του αυτοκινητου
     * @param model το μοντελο του αυτοκινητου
     * @param plate το plate του αυτοκινητου
     * @param color το χρωμα του αυτοκινητου
     * @param status η κατασταση του αυτοκινητου
     * @return Μια ArrayList με τα αυτοκινητα που ταιριζουν με τα κριτηρια
     */
    public ArrayList<Car> searchCars(String brand, String model, String plate, String color, CarStatus status){
        ArrayList<Car> results= new ArrayList<>();

        for(Car car: this.cars){
            //Ελεγχος αν η μαρκα ταιριαζει αλλιως η παραμετρος ειναι κενη
            boolean brandMatches=(brand==null || brand.isEmpty() || car.getBrand().equalsIgnoreCase(brand));
            //Ελεγχοε αν το μοντελο ταιριαζει
            boolean modelMatches=(model==null || model.isEmpty() || car.getModel().equalsIgnoreCase(model));
            boolean plateMatches=(plate==null || plate.isEmpty() || car.getLicensePlate().equalsIgnoreCase(plate));
            boolean colorMatches=(color==null || color.isEmpty() || car.getColor().equalsIgnoreCase(color));
            //Ελεγχοε αν η κατασταση ταιριαζει
            boolean statusMatches=(status==null || car.getStatus()==status);

            if(brandMatches && modelMatches && plateMatches && colorMatches && statusMatches){
                results.add(car);
            }
        }

        return results;
    }

    /**
     * Αναζητα πελατες στην λιστα με βαση τα στοιχεια τους
     * Η αναζητηση επιτρεπει τον συνδιασμο κριτηριων και δεν απαιτει πληρη ταυτιση
     * @param afm το ΑΦΜ του πελατη
     * @param name Το ονοματεπωνυμο του πελατη
     * @param phone Το τηλεφωνο επικοινωνιας
     * @return μια ArrayList με τους πελατες που ικανοποιουν ολα τα κριτηρια αναζητησης
     */
    public ArrayList<Customer> searchCustomers(String afm, String name, String phone){
        ArrayList<Customer> results = new ArrayList<>();

        for(Customer c : this.customers){

            //Ελεγχος αν το ΑΦΜ ταιριαζει ή αν η παραμετρος ειναι κενη
            boolean afmMatches=(afm==null || afm.isEmpty() || c.getAfm().contains(afm));

            //Ελεγχος αν το ονομα περιεχεται στο fullName του πελατη (μετατροπη σε πεζα)
            boolean nameMatches = (name==null || name.isEmpty() || c.getFullName().toLowerCase().contains(name.toLowerCase()));

            //Ελεγχος αν ταιριαζει το τηλεφωνο
            boolean phoneMatches = (phone==null || phone.isEmpty() || c.getPhoneNumber().contains(phone));

            //Αν ο πελατης ικανοποιει ολα τα κριτηρια που εχουν εισαχθει, προστιθεται στα αποτελεσματα
            if(afmMatches && nameMatches && phoneMatches){
                results.add(c);
            }
        }
        return results;
    }


    /**
     * Προσθετει εναν νεο πελατη στο συστημα, ελεγχοντας για μοναδικοτητα του ΑΦΜ
     * @param customer Το αντικειμενο Customer προς προσθηκη
     * @return true αν η προσθηκη ειναι επιτυχης, false αλλιως
     */
    public boolean addCustomer(Customer customer){
        if(customer==null) return false;

        //Ελεγχος για διπλο ΑΦΜ
        for(Customer existingCustomer: this.customers){
            if(existingCustomer.getAfm().equals(customer.getAfm())){
                System.err.println("Αποτυχια προσθηκης: Υπαρχει ηδη πελατης με ΑΦΜ: "+customer.getAfm());
                return false;
            }
        }

        this.customers.add(customer);
        System.out.println("Επιτυχης προσθηκη πελατη με ΑΦΜ: "+customer.getAfm());
        saveData();
        return true;
    }

    /**
     * Ενημερωνει τα στοιχεια ενος υπαρχοντος πελατη, βρισκοντας τον με το ΑΦΜ
     * @param updatedCustomer το αντικειμενο Customer με τα ενημερωμενα στοιχεια
     * @return true αν η ενημερωση ειναι επιτυχης, false αν δεν βρεθει ο πελατης
     */
    public boolean updateCustomer(Customer updatedCustomer){
        for(int i=0; i<customers.size(); i++){
            Customer c=customers.get(i);
            if(c.getAfm().equals(updatedCustomer.getAfm())){
                customers.set(i,updatedCustomer); //αντικατασταση του παλιου αντικειμενου
                saveData();
                return true;
            }
        }
        return false; // ο πελατης δεν βρεθηκε
    }

    /**
     * Επιστρεφει την λιστα με ολους τους εγγρεγραμενους υπαλληλους του συστηματος
     * @return μια ArrayList που περιεχει τα αντικειμενα Employee
     */
    public ArrayList<Employee> getEmployees(){
        return this.employees;
    }

    /**
     * Προσθετει έναν νεο υπαλληλο στο συστημα, αφου ελεγξει αν το ονομα χρηστη ειναι μοναδικο
     * Μετα την επιτυχη προσθηκη τα δεδομενα αποθηκευονται αυτοματα στο συστημα
     * @param employee Το αντικειμενο Employee προς προσθηκη
     * @return true αν η προσθηκη ολοκληρωθηκε, false αν το υπαρχει ηδη
     */
    public boolean addEmployee(Employee employee){
        for(Employee e : this.employees){

            //To username πρεπει να ειναι μοναδικο για καθε χρηστη
            if(e.getUsername().equalsIgnoreCase(employee.getUsername())){
                return false;
            }
        }
        this.employees.add(employee);
        saveData(); //Ενημερωση του αρχειου employees.ser
        return true;
    }

    /**
     * Διαγραφει ενα υπαλληλο απο το συστημα με βαση το ονομα χρηστη του
     * @param username το μοναδικο ονομα χρηστη του υπαλληλου προς διαγραφη
     * @return true αν ο υπαλληλος βρεθηκε και διαγραφτηκε, false σε αντιθετη περιπτωση
     */
    public boolean deleteEmployee(String username){
        for(int i=0; i<employees.size(); i++){
            if(employees.get(i).getUsername().equalsIgnoreCase(username)){
                employees.remove(i);
                saveData(); //Αποθηκευση αλλαγων για διατηρηση μετα τον τερματισμο
                return true;
            }
        }
        return false;
    }

    /**
     * Υπολογιζει το επομενο διαθεσιμο ID για ενοικιαση
     * @return το επομενο ID ως int
     */
    private int generateNextRentalId(){
        int maxId=0;
        for (Rental r: rentals){
            if(r.getRentalId()>maxId){
                maxId=r.getRentalId();
            }
        }
        return maxId+1;
    }



    /**
     * Δημιουργει μια νεα ενοικιαση αυτοκινητου
     * Αλλαζει την κατασταση του αυτοκινητου σε RENTED και αποθηκευει την ενοικιαση
     * @param car το αυτοκινητο προς ενοικιαση
     * @param customer ο πελατης
     * @param employee ο υπαλληλος που εκανε την ενοικιαση
     * @param startDate η ημερομηνια εναρξης
     * @param endDate η ημερομηνια ληξης
     * @return το νεο αντικειμενο Rental ή null αν αποτυχει η ενοικιαση
     */
    public Rental rentCar(Car car, Customer customer, Employee employee, LocalDate startDate, LocalDate endDate){
        //Ελεγχος διαθεσιμοτητας και εγκυροτητας ημερομηνιας
        if(car==null || !car.isAvailable()){
            System.err.println("Αποτυχια Ενοικιασης: Το αυτοκινητο δεν ειναι διαθεσιμο.");
            return null;
        }

        int newId= generateNextRentalId();
        Rental newRental = new Rental(newId, car, customer, employee, startDate, endDate);
        this.rentals.add(newRental);
        car.setStatus(CarStatus.RENTED);
        saveData();
        return newRental;

    }

    /**
     * Διαχειριζεται την επιστροφη ενος αυτοκινητου με βαση τον κωδικο ενοικιασης
     * Επαναφερει την κατασταση του αυτοκινητου σε AVAILABLE
     * @param rentalId ο κωδικος της ενοικιασης προς επιστροφη
     * @return true αν η επιστροφη ειναι επιτυχης false αλλιως
     */
    public boolean returnCar(int rentalId){
        for (Rental rental : this.rentals){
            if(rental.getRentalId()==rentalId){
                rental.getCar().setStatus(CarStatus.AVAILABLE);
                rental.setReturned(true);
                saveData();
                return true;

            }
        }
        return false;
    }

    /**
     * Επιστρεφει το ιστορικο ενοικιασεων για ενα συγκεκριμενο πελατη με βαση το ΑΦΜ
     * @param afm το ΑΦΜ του πελατη
     * @return μια ArrayList με ολες τις ενοικιασεις του πελατη
     */
    public ArrayList<Rental> getRentalsByCustomerAfm(String afm){
        ArrayList<Rental> customerHistory=new ArrayList<>();

        if(afm==null || afm.isEmpty()) return customerHistory;

        for (Rental rental : this.rentals){
            if(rental.getCustomer().getAfm().equals(afm)){
                customerHistory.add(rental);
            }
        }
        return customerHistory;
    }

    /**
     * Επιστρεφει το ιστορικο ενοικιασεων για ενα συγκεκριμενο αυτοκινητο με βαση την πινακιδα
     * @param licensePlate η πινακιδα του αυτοκινητου
     * @return μια ArrayList με ολες τις ενοικιασεις του αυτοκινητου
     */
    public ArrayList<Rental> getRentalsByCarLicensePlate(String licensePlate){
        ArrayList<Rental> carHistory=new ArrayList<>();

        if (licensePlate==null || licensePlate.isEmpty()) return carHistory;

        for (Rental rental: this.rentals){
            if(rental.getCar().getLicensePlate().equalsIgnoreCase(licensePlate)){
                carHistory.add(rental);
            }
        }
        return carHistory;
    }

    /**
     * Αναζητα ολες τις τρεχουσες ενεργες ενοικιασεις για ενα συγκεκριμενο αυτοκινητο
     * Μια ενοικιαση θεωρειται ενεργη αν η κατασταση του αυτοκινητου ειναι RENTED
     * @param plate Η πινακιδα του αυτοκινητου προς αναζητηση
     * @return μια λιστα με τα αντικειμενα που αφορουντο συγκεκριμενο οχημα
     */
    public List<Rental> getActiveRentalsByCar(String plate){
        List<Rental> active = new ArrayList<>();

        for(Rental r: rentals){
            //Ελεγχος ταυτισης πινακιδας και καταστασης ενοικιασης
            if (r.getCar().getLicensePlate().equalsIgnoreCase(plate) && r.getCar().getStatus()==CarStatus.RENTED){
                active.add(r);
            }
        }
        return active;
    }

    /**
     * Αναζητα ολες τις ενεργες ενοικιασεις που εχει πραγματοποιησςι ενας συγκεκριμενος πελατης
     * Χρησιμοποιειται για την διευκολυνση της διαδικασιας επιστρογης οχημαος
     * @param afm το ΑΦΜ του πελατη
     * @return μια λιστα με τις τρεχουσες ενοικιασεις του πελατη
     */
    public List<Rental> getActiveRentalsByCustomer(String afm){
        List<Rental> active=new ArrayList<>();
        for(Rental r: rentals){
            //Ελεγχος ταυτισης ΑΦΜ και καταστασης ενοικιασης
            if(r.getCustomer().getAfm().equals(afm) && r.getCar().getStatus()==CarStatus.RENTED){
                active.add(r);
            }
        }
        return active;
    }

    /**
     * Αναζητα μια συγκεκριμενη ενοικιαση στο συστημα με βαση τον μοναδικο κωδικο της
     * @param id ο μοναδικος κωδικος ενοικιασης (Rental ID)
     * @return το αντικειμενο Rental αν βρεθει, αλλιως null
     */
    public Rental findRentalById(int id){
        for(Rental r: rentals){
            if(r.getRentalId()==id) return r;
        }
        return null;
    }


}