package gui;

import api.CarRentalService;
import api.Customer;

import javax.swing.*;
import java.awt.*;

/**
 * Φόρμα αναζήτησης πελάτη
 *
 * @author Ραφαέλα Σιαμπάνη
 */
public class SearchCustomerFrame extends JFrame {

    public SearchCustomerFrame(CarRentalService service) {

        setTitle("Αναζήτηση Πελάτη");
        setSize(500, 350);
        setLocationRelativeTo(null);

        JTextField afmField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();

        JTextArea results = new JTextArea();
        results.setEditable(false);

        JButton searchBtn = new JButton("Αναζήτηση");
        searchBtn.addActionListener(e -> {
            results.setText("");
            String afm = afmField.getText().trim();
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();

            for (Customer c : service.searchCustomers(afm, name, phone)) {
                results.append(c + "\n");
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.add(new JLabel("ΑΦΜ:"));
        inputPanel.add(afmField);
        inputPanel.add(new JLabel("Ονοματεπώνυμο:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Τηλέφωνο:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel());
        inputPanel.add(searchBtn);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(results), BorderLayout.CENTER);

        setVisible(true);
    }
}

