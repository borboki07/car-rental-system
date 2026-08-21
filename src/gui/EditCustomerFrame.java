package gui;

import api.CarRentalService;
import api.Customer;

import javax.swing.*;
import java.awt.*;

/**
 * Φόρμα επεξεργασίας πελάτη
 *
 * @author Ραφαέλα Σιαμπάνη
 */
public class EditCustomerFrame extends JFrame {

    public EditCustomerFrame(CarRentalService service, Customer customer) {

        setTitle("Επεξεργασία Πελάτη");
        setSize(400, 250);
        setLocationRelativeTo(null);

        JTextField nameField = new JTextField(customer.getFullName());
        JTextField afmField = new JTextField(customer.getAfm());
        JTextField phoneField = new JTextField(customer.getPhoneNumber());
        JTextField emailField = new JTextField(customer.getEmail());

        JButton saveBtn = new JButton("Αποθήκευση");

        saveBtn.addActionListener(e -> {
            try {
                customer.setFullName(nameField.getText());
                customer.setAfm(afmField.getText());
                customer.setPhoneNumber(phoneField.getText());
                customer.setEmail(emailField.getText());

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

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.add(new JLabel("Ονοματεπώνυμο:"));
        panel.add(nameField);
        panel.add(new JLabel("ΑΦΜ:"));
        panel.add(afmField);
        panel.add(new JLabel("Τηλέφωνο:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel());
        panel.add(saveBtn);

        add(panel);
        setVisible(true);
    }
}

