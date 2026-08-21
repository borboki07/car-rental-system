package gui;
import api.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;


public class RentalFrame extends JFrame {
    public RentalFrame(CarRentalService service, Employee loggedEmployee){
        setTitle("Νεα Ενοικίαση");
        setSize(400,300);
        setLayout(new GridLayout(6,2,10,10));

        JTextField plateField= new JTextField();
        JTextField afmField= new JTextField();
        JTextField startDateField = new JTextField(LocalDate.now().toString());
        JTextField endDateField = new JTextField(LocalDate.now().plusDays(1).toString());

        JButton rentBtn = new JButton("Ολοκλήρωση Ενοικίασης");

        rentBtn.addActionListener(e->{
            String plate = plateField.getText().trim();
            String afm = afmField.getText().trim();

            try {
                LocalDate start = LocalDate.parse(startDateField.getText().trim());
                LocalDate end = LocalDate.parse(endDateField.getText().trim());

                if(end.isBefore(start)){
                    JOptionPane.showMessageDialog(this, "Λάθος ημερομηνία!");
                    return;
                }

                Car car = service.searchCars(null, null, plate, null, CarStatus.AVAILABLE).stream().findFirst().orElse(null);
                Customer customer = service.searchCustomers(afm, null, null).stream().findFirst().orElse(null);

                if (car!=null && customer!=null){
                    service.rentCar(car, customer, loggedEmployee,start, end);
                    JOptionPane.showMessageDialog(this, "Η ενοικίαση καταχωρήθηκε επιτυχώς!");
                    dispose();
                }else {
                    JOptionPane.showMessageDialog(this, "Το αυτοκίνητο (διαθέσιμο) ή ο πελάτης δεν βρέθηκε.");
                }
            }catch (java.time.format.DateTimeParseException ex){
                JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε ημερομηνίες στη μορφή: ΕΕΕΕ-ΜΜ-ΗΗ");
            }
        });

        add(new JLabel("Πινακίδα Αυτοκινήτου:")); add(plateField);
        add(new JLabel("ΑΦΜ Πελάτη:")); add(afmField);
        add(new JLabel("Ημερομηνία Εναρξης (ΥΥΥΥ-ΜΜ-DD):")); add(startDateField);
        add(new JLabel("Ημερομηνία Λήξης (ΥΥΥΥ-ΜΜ-DD):")); add(endDateField);
        add(new JLabel()); add(rentBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
