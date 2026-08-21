package gui;

import api.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Φόρμα προχωρημένης αναζήτησης αυτοκινήτων.
 * Επιτρέπει το φιλτράρισμα των οχημάτων με πολλαπλά κριτήρια.
 * * @author Ραφαέλα Σιαμπάνη
 */
public class SearchCarAdvancedFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public SearchCarAdvancedFrame(CarRentalService service) {
        setTitle("Προχωρημένη Αναζήτηση Αυτοκινήτων");
        setSize(800, 500);
        setLocationRelativeTo(null);

        // Πεδία φίλτρων
        JTextField plateField = new JTextField(10);
        JTextField brandField = new JTextField(10);
        JTextField modelField = new JTextField(10);
        JTextField colorField = new JTextField(10);
        JComboBox<Object> statusBox = new JComboBox<>(new Object[]{"Όλα", CarStatus.AVAILABLE, CarStatus.RENTED});
        JButton searchBtn = new JButton("Αναζήτηση");

        // Πάνελ φίλτρων
        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Πινακίδα:")); filterPanel.add(plateField);
        filterPanel.add(new JLabel("Μάρκα:")); filterPanel.add(brandField);
        filterPanel.add(new JLabel("Μοντέλο:")); filterPanel.add(modelField);
        filterPanel.add(new JLabel("Χρώμα:")); filterPanel.add(colorField);
        filterPanel.add(new JLabel("Κατάσταση:")); filterPanel.add(statusBox);
        filterPanel.add(searchBtn);

        // Πίνακας αποτελεσμάτων
        String[] columns = {"ID", "Πινακίδα", "Μάρκα", "Μοντέλο", "Έτος", "Χρώμα", "Κατάσταση"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Λογική Αναζήτησης
        searchBtn.addActionListener(e -> {
            String plate = plateField.getText().trim();
            String brand = brandField.getText().trim();
            String model = modelField.getText().trim();
            String color = colorField.getText().trim();
            CarStatus status = (statusBox.getSelectedItem() instanceof CarStatus) ? (CarStatus) statusBox.getSelectedItem() : null;

            // Κλήση της μεθόδου αναζήτησης
            ArrayList<Car> results = service.searchCars(brand, model, plate, color, status);

            // Ενημέρωση πίνακα
            tableModel.setRowCount(0);
            for (Car car : results) {
                tableModel.addRow(new Object[]{
                        car.getId(), car.getLicensePlate(), car.getBrand(),
                        car.getModel(), car.getYear(), car.getColor(), car.getStatus()
                });
            }
        });

        add(filterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Αρχική εμφάνιση όλων των αυτοκινήτων
        searchBtn.doClick();

        setVisible(true);
    }
}