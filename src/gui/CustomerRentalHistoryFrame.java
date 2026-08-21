package gui;

import api.Rental;
import api.CarRentalService;
import java.util.List;
import api.*;
import javax.swing.*;
import java.awt.*;

/**
 * Προβολή ιστορικού ενοικιάσεων για συγκεκριμένο πελάτη
 * * @author Ραφαέλα Σιαμπάνη
 */
public class CustomerRentalHistoryFrame extends JFrame {

    public CustomerRentalHistoryFrame(CarRentalService service) {
        setTitle("Ιστορικό Ενοικιάσεων Πελάτη");
        setSize(600, 450);
        setLocationRelativeTo(null);

        JTextField afmField = new JTextField(15);
        JButton searchBtn = new JButton("Προβολή Ιστορικού");
        JTextArea area = new JTextArea();
        area.setEditable(false);

        searchBtn.addActionListener(e -> {
            area.setText("");
            String afm = afmField.getText().trim();
            List<Rental> rentals = service.getRentalsByCustomerAfm(afm);

            if (rentals.isEmpty()) {
                area.setText("Δεν βρέθηκαν ενοικιάσεις για τον πελάτη με ΑΦΜ: " + afm);
            } else {
                for (Rental r : rentals) {
                    area.append(r.toString() + "\n----------------------------------\n");
                }
            }
        });

        JPanel top = new JPanel();
        top.add(new JLabel("ΑΦΜ Πελάτη:")); top.add(afmField); top.add(searchBtn);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(area), BorderLayout.CENTER);
        setVisible(true);
    }
}


