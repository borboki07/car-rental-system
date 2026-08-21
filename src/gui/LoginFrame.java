package gui;

import api.CarRentalService;
import api.Employee;

import javax.swing.*;
import java.awt.*;

/**
 * Φόρμα σύνδεσης χρηστών (Login)
 * Διαχειρίζεται την ταυτοποίηση των υπαλλήλων για την είσοδο στο σύστημα.
 * * @author Ραφαέλα Σιαμπάνη & @author Ελευθερία Μπορμπόκη
 */
public class LoginFrame extends JFrame {

    private CarRentalService service;

    public LoginFrame(CarRentalService service) {
        this.service = service;

        setTitle("Σύνδεση Χρήστη - Car Rental");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Στοιχεία φόρμας
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginBtn = new JButton("Σύνδεση");

        // Λειτουργία κουμπιού σύνδεσης
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            // Έλεγχος στοιχείων μέσω του service
            Employee emp = service.login(username, password);

            if (emp != null) {
                JOptionPane.showMessageDialog(this, "Επιτυχής Σύνδεση! Καλώς ήρθατε, " + emp.getFullName());
                dispose(); // Κλείσιμο του παραθύρου Login

                // Άνοιγμα του κεντρικού μενού και μεταβίβαση του service και του υπαλλήλου
                new MainMenuFrame(service, emp).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Λάθος username ή password", "Σφάλμα Σύνδεσης", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Σχεδίαση παραθύρου
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Περιθώρια γύρω από τα στοιχεία

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginBtn);

        add(panel);
    }
}
