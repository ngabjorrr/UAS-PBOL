/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UAS_PBOL;

/**
 *
 * @author brifl
 */
import javax.swing.*;
import java.awt.*;

public class RentalForm extends JFrame {
    private String vehicleName;
    private String price;
    private int rentalDays;

    public RentalForm(String vehicleName, String price, int rentalDays) {
        this.vehicleName = vehicleName;
        this.price = price;
        this.rentalDays = rentalDays;

        setTitle("Rental Information");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Vehicle Details
        formPanel.add(new JLabel("Vehicle:"));
        formPanel.add(new JLabel(vehicleName));
        formPanel.add(new JLabel("Rental Days:"));
        formPanel.add(new JLabel(String.valueOf(rentalDays)));
        formPanel.add(new JLabel("Total Price:"));
        formPanel.add(new JLabel("Rp. " + price));

        // User Details Inputs
        formPanel.add(new JLabel("Full Name:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("KTP Number:"));
        JTextField ktpField = new JTextField();
        formPanel.add(ktpField);

        formPanel.add(new JLabel("Phone Number:"));
        JTextField phoneField = new JTextField();
        formPanel.add(phoneField);

        // Continue Button
        JButton continueButton = new JButton("Continue to Payment");
        continueButton.addActionListener(e -> {
            // Validate inputs
            if (validateInputs(nameField.getText(), ktpField.getText(), phoneField.getText())) {
                // Open Payment Form with all details
                new PaymentForm(vehicleName, price, rentalDays, 
                                nameField.getText(), 
                                ktpField.getText(), 
                                phoneField.getText());
                dispose();
            }
        });

        add(formPanel, BorderLayout.CENTER);
        add(continueButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean validateInputs(String name, String ktp, String phone) {
        if (name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter full name", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (ktp.trim().isEmpty() || !ktp.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid KTP number", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (phone.trim().isEmpty() || !phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}