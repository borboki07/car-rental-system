package gui;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Φόρμα διαχείρισης χρηστών (προσθήκη / διαγραφή)
 *
 * @author Ραφαέλα Σιαμπάνη
 */
public class UserManagementFrame extends JFrame {

    public UserManagementFrame(CarRentalService service) {

        setTitle("Διαχείριση Χρηστών");
        setSize(500, 400);
        setLocationRelativeTo(null);

        JTextField nameField = new JTextField();
        JTextField surnameField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField passwordField = new JTextField();

        JTextArea usersArea = new JTextArea();
        usersArea.setEditable(false);

        JButton addBtn = new JButton("Προσθήκη Χρήστη");
        JButton deleteBtn = new JButton("Διαγραφή Χρήστη");

        // Εμφάνιση όλων των χρηστών
        Runnable refreshUsers = () -> {
            usersArea.setText("");
            List<Employee> employees = service.getEmployees();
            for (Employee e : employees) {
                usersArea.append(e.getUsername() + " - " + e.getFullName() + " - " + e.getEmail() + "\n");
            }
        };
        refreshUsers.run();

        addBtn.addActionListener(e-> {
            try {
                Employee emp = new Employee(
                        usernameField.getText().trim(),
                        passwordField.getText().trim(),
                        nameField.getText().trim() + " " + surnameField.getText().trim(),
                        emailField.getText().trim()
                );
                boolean success = service.addEmployee(emp);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Ο χρήστης προστέθηκε");
                    refreshUsers.run();
                } else {
                    JOptionPane.showMessageDialog(this, "Υπάρχει ήδη χρήστης με το ίδιο username ή email");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Λάθος στοιχεία");
            }
        });

        deleteBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Εισάγετε Username προς διαγραφή:");
            if (input == null || input.isEmpty()) return;

            String username = input.trim();

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Είστε σίγουρος οτι θέλετε να διαγράψετε τον χρήστη: " + username + ";",
                    "Επιβεβαίβση Διαγραφής",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if(confirm==JOptionPane.YES_OPTION){

                boolean success = service.deleteEmployee(username);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Ο χρήστης διαγράφηκε");
                    refreshUsers.run();
                } else {
                    JOptionPane.showMessageDialog(this, "Ο χρήστης δεν βρέθηκε", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.add(new JLabel("Όνομα:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Επώνυμο:"));
        formPanel.add(surnameField);
        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(addBtn);
        formPanel.add(deleteBtn);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(usersArea), BorderLayout.CENTER);

        setVisible(true);
    }
}

