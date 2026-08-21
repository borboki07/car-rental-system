package gui;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Φόρμα επιστροφής αυτοκινήτου
 *
 * @author Ραφαέλα Σιαμπάνη
 */
public class ReturnCarFrame extends JFrame {

    public ReturnCarFrame(CarRentalService service) {

        setTitle("Επιστροφή Αυτοκινήτου");
        setSize(500, 300);
        setLocationRelativeTo(null);

        JTextField plateField = new JTextField();
        JTextField afmField = new JTextField();
        JTextArea rentalsArea = new JTextArea();
        rentalsArea.setEditable(false);

        JButton findBtn = new JButton("Βρες Ενοικιάσεις");
        JButton returnBtn = new JButton("Επιστροφή");

        findBtn.addActionListener(e -> {
            rentalsArea.setText("");
            String plate = plateField.getText().trim();
            String afm = afmField.getText().trim();

            List<Rental> rentals;
            if (!plate.isEmpty()) {
                rentals = service.getActiveRentalsByCar(plate);
            } else if (!afm.isEmpty()) {
                rentals = service.getActiveRentalsByCustomer(afm);
            } else {
                JOptionPane.showMessageDialog(this, "Συμπληρώστε πινακίδα ή ΑΦΜ");
                return;
            }

            if (rentals.isEmpty()) {
                rentalsArea.setText("Δεν βρέθηκαν ενεργές ενοικιάσεις");
            } else {
                for (Rental r : rentals) {
                    rentalsArea.append(r + "\n");
                }
            }
        });

        returnBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Εισάγετε Κωδικό Ενοικίασης προς επιστροφή:");
            if (input == null || input.isEmpty()) return;

            try {
                int rentalId = Integer.parseInt(input);
                Rental rental = service.findRentalById(rentalId);

                if (rental == null || rental.isReturned()) {
                    JOptionPane.showMessageDialog(this, "Μη έγκυρη ή ήδη επιστραμμένη ενοικίαση");
                    return;
                }

                service.returnCar(rental.getRentalId());
                JOptionPane.showMessageDialog(this, "Το αυτοκίνητο επιστράφηκε επιτυχώς");
                rentalsArea.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Μη έγκυρος κωδικός");
            }
        });

        JPanel topPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        topPanel.add(new JLabel("Πινακίδα:"));
        topPanel.add(plateField);
        topPanel.add(new JLabel("ΑΦΜ Πελάτη:"));
        topPanel.add(afmField);
        topPanel.add(findBtn);
        topPanel.add(returnBtn);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(rentalsArea), BorderLayout.CENTER);

        setVisible(true);
    }
}
