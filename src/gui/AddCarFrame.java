package gui;

import api.*;
import javax.swing.*;
import java.awt.*;
import java.nio.channels.ScatteringByteChannel;

/**
 * Φόρμα προσθήκης νέου αυτοκινήτου
 * * @author Ραφαέλα Σιαμπάνη
 */
public class AddCarFrame extends JFrame {

    public AddCarFrame(CarRentalService service) {
        setTitle("Προσθήκη Αυτοκινήτου");
        setSize(400, 450);
        setLocationRelativeTo(null);

        JTextField plateField = new JTextField();
        JTextField brandField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField colorField = new JTextField();
        JComboBox<CarStatus> statusBox = new JComboBox<>(CarStatus.values());

        JButton saveBtn = new JButton("Αποθήκευση");

        saveBtn.addActionListener(e->{
            String plate=plateField.getText().trim();

            if (plate.isEmpty()){
                JOptionPane.showMessageDialog(this,"Παρακαλώ συμπληρώστε την πινακιδα.", "Σφάλμα",JOptionPane.WARNING_MESSAGE);
                return;
            }
            //Ελεγχος αν η πινακιδα υπαρχει ήδη
            if (service.plateExists(plate)){
                JOptionPane.showMessageDialog(this,"Η πινακίδα " + plate + " υπάρχει ήδη στο σύστημα!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if (yearField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Παρακαλώ συμπληρώστε το έτος.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int year = Integer.parseInt(yearField.getText().trim());

                int nextId = 0;

                for (Car c : service.searchCars(null, null, null, null, null)) {
                    if (c.getId() > nextId) {
                        nextId = c.getId();
                    }
                }
                nextId++;

                Car car = new Car(
                        nextId,
                        plate,
                        brandField.getText().trim(),
                        typeField.getText().trim(),
                        modelField.getText().trim(),
                        year,
                        colorField.getText().trim(),
                        (CarStatus) statusBox.getSelectedItem()
                );



                if (service.addCar(car)) {
                    JOptionPane.showMessageDialog(this, "Επιτυχής προσθήκη αυτοκινήτου!");
                    dispose();
                }
            }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(this, "Το ετος πρεπει να ειναι αριθμος!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Παρακαλώ ελέγξτε αν συμπληρώσατε όλα τα πεδία.","Σφάλμα",JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.add(new JLabel("Πινακίδα:")); panel.add(plateField);
        panel.add(new JLabel("Μάρκα:")); panel.add(brandField);
        panel.add(new JLabel("Τύπος:")); panel.add(typeField);
        panel.add(new JLabel("Μοντέλο:")); panel.add(modelField);
        panel.add(new JLabel("Έτος:")); panel.add(yearField);
        panel.add(new JLabel("Χρώμα:")); panel.add(colorField);
        panel.add(new JLabel("Κατάσταση:")); panel.add(statusBox);
        panel.add(new JLabel()); panel.add(saveBtn);

        add(panel);
        setVisible(true);
    }
}
