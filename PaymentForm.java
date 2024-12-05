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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PaymentForm extends JFrame {
    private String vehicleName;
    private String price;
    private int rentalDays;
    private String name;
    private String ktp;
    private String phone;

    public PaymentForm(String vehicleName, String price, int rentalDays, 
                       String name, String ktp, String phone) {
        this.vehicleName = vehicleName;
        this.price = price;
        this.rentalDays = rentalDays;
        this.name = name;
        this.ktp = ktp;
        this.phone = phone;

        setTitle("Payment Options");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel detailsPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Display Rental and User Details
        detailsPanel.add(new JLabel("Vehicle:"));
        detailsPanel.add(new JLabel(vehicleName));
        detailsPanel.add(new JLabel("Rental Days:"));
        detailsPanel.add(new JLabel(String.valueOf(rentalDays)));
        detailsPanel.add(new JLabel("Total Price:"));
        detailsPanel.add(new JLabel("Rp. " + price));
        detailsPanel.add(new JLabel("Name:"));
        detailsPanel.add(new JLabel(name));
        detailsPanel.add(new JLabel("KTP:"));
        detailsPanel.add(new JLabel(ktp));
        detailsPanel.add(new JLabel("Phone:"));
        detailsPanel.add(new JLabel(phone));

        // Payment Options
        JPanel paymentPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Select Payment Method"));

        JRadioButton cashPayment = new JRadioButton("Pay at Location");
        JRadioButton transferPayment = new JRadioButton("Bank Transfer");
        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(cashPayment);
        paymentGroup.add(transferPayment);

        paymentPanel.add(cashPayment);
        paymentPanel.add(transferPayment);

        // Confirm Button
        JButton confirmButton = new JButton("Confirm Booking");
        confirmButton.addActionListener(e -> {
            if (!cashPayment.isSelected() && !transferPayment.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please select a payment method", "Payment Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String paymentMethod = cashPayment.isSelected() ? "Cash" : "Transfer";
            
            // Save to database
            if (saveRentalToDatabase(paymentMethod)) {
                JOptionPane.showMessageDialog(this, 
                    "Booking Confirmed!\nPayment Method: " + paymentMethod, 
                    "Booking Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                dispose();
            }
        });

        add(detailsPanel, BorderLayout.NORTH);
        add(paymentPanel, BorderLayout.CENTER);
        add(confirmButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean saveRentalToDatabase(String paymentMethod) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "hr";
        String password = "biliwatti";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                String query = "INSERT INTO rentals (rental_id, vehicle_name, price, rental_days, " +
                               "customer_name, ktp_number, phone_number, payment_method, total_price, rental_date) " +
                               "VALUES (rental_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    double dailyRate = Double.parseDouble(price.replaceAll("[^0-9.]", ""));
                    double totalPrice = dailyRate * rentalDays;

                    statement.setString(1, vehicleName);
                    statement.setString(2, price);
                    statement.setInt(3, rentalDays);
                    statement.setString(4, name);
                    statement.setString(5, ktp);
                    statement.setString(6, phone);
                    statement.setString(7, paymentMethod);
                    statement.setDouble(8, totalPrice);

                    statement.executeUpdate();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), 
                                          "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}