package gui;

import api.Car;
import api.CarRentalService;
import api.CarStatus;

import javax.swing.*;
import java.awt.*;

/**
 * Φόρμα επεξεργασίας αυτοκινήτου
 *
 * @author Ραφαέλα Σιαμπάνη
 */
public class EditCarFrame extends JFrame {

    public EditCarFrame(CarRentalService service, Car car) {

        setTitle("Επεξεργασία Αυτοκινήτου");
        setSize(400, 350);
        setLocationRelativeTo(null);

        JTextField brandField = new JTextField(car.getBrand());
        JTextField modelField = new JTextField(car.getModel());
        JTextField yearField = new JTextField(String.valueOf(car.getYear()));
        JTextField colorField = new JTextField(car.getColor());

        JComboBox<CarStatus> statusBox =
                new JComboBox<>(CarStatus.values());
        statusBox.setSelectedItem(car.getStatus());

        JButton saveBtn = new JButton("Αποθήκευση");

        saveBtn.addActionListener(e -> {
            try {
                car.setBrand(brandField.getText());
                car.setModel(modelField.getText());
                car.setYear(Integer.parseInt(yearField.getText()));
                car.setColor(colorField.getText());
                car.setStatus((CarStatus) statusBox.getSelectedItem());

                service.saveData();
                JOptionPane.showMessageDialog(this, "Επιτυχής ενημέρωση");
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Λάθος στοιχεία",
                        "Σφάλμα",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.add(new JLabel("Μάρκα:"));
        panel.add(brandField);
        panel.add(new JLabel("Μοντέλο:"));
        panel.add(modelField);
        panel.add(new JLabel("Έτος:"));
        panel.add(yearField);
        panel.add(new JLabel("Χρώμα:"));
        panel.add(colorField);
        panel.add(new JLabel("Κατάσταση:"));
        panel.add(statusBox);
        panel.add(new JLabel());
        panel.add(saveBtn);

        add(panel);
        setVisible(true);
    }
}



