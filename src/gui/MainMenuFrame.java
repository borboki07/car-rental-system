package gui;

import api.CarRentalService;
import api.Employee;
import javax.swing.*;
import java.awt.*;

/**
 * Το Κεντρικό Μενού της εφαρμογής.
 * Παρέχει πρόσβαση σε όλες τις βασικές λειτουργίες (Αυτοκίνητα, Πελάτες, Ενοικιάσεις).
 * * @author Ραφαέλα Σιαμπάνη
 */
public class MainMenuFrame extends JFrame {

    private CarRentalService service;
    private Employee loggedEmployee;

    public MainMenuFrame(CarRentalService service, Employee loggedEmployee) {
        this.service = service;
        this.loggedEmployee = loggedEmployee;

        setTitle("Car Rental Service - Κεντρικό Μενού");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Πάνω μέρος: Καλωσόρισμα και Πληροφορίες χρήστη
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(230, 230, 230));
        JLabel welcomeLabel = new JLabel("  Συνδεδεμένος Χρήστης: " + loggedEmployee.getFullName());
        JButton logoutBtn = new JButton("Αποσύνδεση");
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(logoutBtn, BorderLayout.EAST);

        // Κεντρικό Πάνελ με κουμπιά (GridLayout)
        JPanel menuPanel = new JPanel(new GridLayout(4, 3, 15, 15));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // --- ΕΝΟΤΗΤΑ ΑΥΤΟΚΙΝΗΤΑ ---
        JButton addCarBtn = new JButton("Προσθήκη Αυτοκινήτου");
        JButton searchCarBtn = new JButton("Αναζήτηση Αυτοκινήτου");
        JButton carHistoryBtn = new JButton("Ιστορικό Αυτοκινήτου");

        // --- ΕΝΟΤΗΤΑ ΠΕΛΑΤΕΣ ---
        JButton addCustBtn = new JButton("Προσθήκη Πελάτη");
        JButton searchCustBtn = new JButton("Αναζήτηση Πελάτη");
        JButton custHistoryBtn = new JButton("Ιστορικό Πελάτη");

        // --- ΕΝΟΤΗΤΑ ΕΝΟΙΚΙΑΣΕΙΣ ---
        JButton rentBtn = new JButton("Νέα Ενοικίαση");
        JButton returnBtn = new JButton("Επιστροφή Αυτοκινήτου");
        JButton userMgmtBtn = new JButton("Διαχείριση Χρηστών");

        // Προσθήκη κουμπιών στο πάνελ
        menuPanel.add(addCarBtn); menuPanel.add(searchCarBtn); menuPanel.add(carHistoryBtn);
        menuPanel.add(addCustBtn); menuPanel.add(searchCustBtn); menuPanel.add(custHistoryBtn);
        menuPanel.add(rentBtn);    menuPanel.add(returnBtn);    menuPanel.add(userMgmtBtn);

        // --- ΛΕΙΤΟΥΡΓΙΕΣ ΚΟΥΜΠΙΩΝ ---

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame(service).setVisible(true);
        });

        addCarBtn.addActionListener(e -> new AddCarFrame(service).setVisible(true));
        searchCarBtn.addActionListener(e -> new SearchCarAdvancedFrame(service).setVisible(true));
        carHistoryBtn.addActionListener(e -> new CarRentalHistoryFrame(service).setVisible(true));

        addCustBtn.addActionListener(e -> new AddCustomerFrame(service).setVisible(true));
        searchCustBtn.addActionListener(e -> new SearchCustomerFrame(service).setVisible(true));
        custHistoryBtn.addActionListener(e -> new CustomerRentalHistoryFrame(service).setVisible(true));

        rentBtn.addActionListener(e -> new RentalFrame(service, loggedEmployee).setVisible(true));
        returnBtn.addActionListener(e -> new ReturnCarFrame(service).setVisible(true));
        userMgmtBtn.addActionListener(e -> new UserManagementFrame(service).setVisible(true));

        // Τοποθέτηση στο JFrame
        add(headerPanel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.CENTER);
    }
}

