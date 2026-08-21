package gui;

import api.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Προβολή ιστορικού ενοικιάσεων για συγκεκριμένο αυτοκίνητο
 * * @author Ραφαέλα Σιαμπάνη
 */
public class CarRentalHistoryFrame extends JFrame {

    public CarRentalHistoryFrame(CarRentalService service) {
        setTitle("Ιστορικό Ενοικιάσεων Αυτοκινήτου");
        setSize(600, 450);
        setLocationRelativeTo(null);

        JTextField plateField = new JTextField(15);
        JButton searchBtn = new JButton("Προβολή Ιστορικού");
        JTextArea area = new JTextArea();
        area.setEditable(false);

        searchBtn.addActionListener(e -> {
            area.setText("");
            String plate = plateField.getText().trim();
            List<Rental> rentals = service.getRentalsByCarLicensePlate(plate);

            if (rentals.isEmpty()) {
                area.setText("Δεν βρέθηκαν ενοικιάσεις για το αυτοκίνητο: " + plate);
            } else {
                for (Rental r : rentals) {
                    area.append(r.toString() + "\n----------------------------------\n");
                }
            }
        });

        JPanel top = new JPanel();
        top.add(new JLabel("Πινακίδα:")); top.add(plateField); top.add(searchBtn);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(area), BorderLayout.CENTER);
        setVisible(true);
    }
}

