package gui;

import api.CarRentalService;
import api.Customer;
import javax.swing.*;
import java.awt.*;

/**
 * Φόρμα προσθήκης νέου πελάτη
 * * @author Ραφαέλα Σιαμπάνη
 */
public class AddCustomerFrame extends JFrame {

    public AddCustomerFrame(CarRentalService service) {
        setTitle("Προσθήκη Πελάτη");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JTextField nameField = new JTextField();
        JTextField afmField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();

        JButton saveBtn = new JButton("Καταχώρηση");

        saveBtn.addActionListener(e -> {

            String afm = afmField.getText().trim();
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();

            if (afm.isEmpty() || name.isEmpty()){
                JOptionPane.showMessageDialog(this, "Το ΑΦΜ και το Όνομα είναι υποχρεωτικά");
                return;
            }

            Customer customer = new Customer(afm, name, phone, email);

            if (service.addCustomer(customer)) {
                JOptionPane.showMessageDialog(this, "Επιτυχής καταχώρηση πελάτη");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Ο πελάτης με ΑΦΜ" +afm+ "υπάρχει ήδη!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.add(new JLabel("Ονοματεπώνυμο:")); panel.add(nameField);
        panel.add(new JLabel("ΑΦΜ:")); panel.add(afmField);
        panel.add(new JLabel("Τηλέφωνο:")); panel.add(phoneField);
        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(new JLabel()); panel.add(saveBtn);

        add(panel);
        setVisible(true);
    }
}


