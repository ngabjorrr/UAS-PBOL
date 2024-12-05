/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UAS_PBOL;

/**
 *
 * @author brifl
 */
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class RentalMenuFrame extends JFrame {

    private String[][] vehicles = {
        //Cars
        {"Daihatsu New Ayla", "250.000", "C:\\Users\\brifl\\Downloads\\Alya.png", "Car"},
        {"Toyota New Avanza", "350.000", "C:\\Users\\brifl\\Downloads\\Toyota.png", "Car"},
        {"Toyota Fortuner", "600.000", "C:\\Users\\brifl\\Downloads\\toyota-fortuner.png", "Car"},
        {"Suzuki Ertiga", "300.000", "C:\\Users\\brifl\\Downloads\\Ertiga.png", "Car"},
        {"Honda HR-V", "500.000", "C:\\Users\\brifl\\Downloads\\HRV.png", "Car"},
        {"Mazda CX-5", "700.000", "C:\\Users\\brifl\\Downloads\\MASZDA.png", "Car"},
        {"BMW X1", "900.000", "C:\\Users\\brifl\\Downloads\\BMW.png", "Car"},
        {"Kia Seltos", "470.000", "C:\\Users\\brifl\\Downloads\\Kia Seltos.png", "Car"},
        {"Nissan Kicks", "400.000", "C:\\Users\\brifl\\Downloads\\Nissan Kicks.png", "Car"},
        //Motorcycles
        {"Yamaha NMAX", "100.000", "C:\\Users\\brifl\\Downloads\\nmax-hijau.png", "Motorcycle"},
        {"Yamaha Aerox 155", "95.000", "C:\\Users\\brifl\\Downloads\\yamaha-aerox-155-connected-1.png", "Motorcycle"},
        {"Kawasaki Ninja 250", "350.000", "C:\\Users\\brifl\\Downloads\\f068387f-6daf-423b-9d0d-ca5e55d74277.png", "Motorcycle"},
        {"Vespa Primavera", "150.000", "C:\\Users\\brifl\\Downloads\\grey-materia-02.png", "Motorcycle"},
        {"Honda PCX 160", "130.000", "C:\\Users\\brifl\\Downloads\\Honda-PCX-160-ABS-Majestic-Matte-Red-2.png", "Motorcycle"},
        {"Yamaha XSR 155", "220.000", "C:\\Users\\brifl\\Downloads\\XSR-1-600x400.png", "Motorcycle"},
        {"Honda Beat 110", "80.000", "C:\\Users\\brifl\\Downloads\\Honda-Beat.png", "Motorcycle"},
        {"Honda Vario 125", "90.000", "C:\\Users\\brifl\\Downloads\\Vario125CBSISSBlue.png", "Motorcycle"}
    };

    public RentalMenuFrame() {
        setTitle("Vehicle Rental App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 700);  // Increased height for better scrolling
        setLayout(new BorderLayout());

        // Top panel for dropdown with modern styling
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(new Color(240, 240, 240));  // Light gray background
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 14));

        String[] categoryOptions = {"All", "Motorcycle", "Car"};
        JComboBox<String> categoryDropdown = new JComboBox<>(categoryOptions);
        categoryDropdown.setFont(new Font("Arial", Font.PLAIN, 12));

        topPanel.add(categoryLabel);
        topPanel.add(categoryDropdown);

        // Create a scrollable panel for vehicle display
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridBagLayout());  // More flexible layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);  // Add some padding

        // Scroll pane to enable vertical scrolling
        JScrollPane scrollPane = new JScrollPane(displayPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);  // Faster scrolling

        // Add all vehicles to display panel by default
        for (String[] vehicle : vehicles) {
            JPanel vehiclePanel = createVehiclePanel(vehicle[0], vehicle[1], vehicle[2]);
            displayPanel.add(vehiclePanel, gbc);
        }

        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Listener for category dropdown
        categoryDropdown.addActionListener(e -> {
            String selectedCategory = (String) categoryDropdown.getSelectedItem();
            displayPanel.removeAll();

            // Filter vehicles by category
            for (String[] vehicle : vehicles) {
                if (selectedCategory.equals("All") || selectedCategory.equals(vehicle[3])) {
                    JPanel vehiclePanel = createVehiclePanel(vehicle[0], vehicle[1], vehicle[2]);
                    displayPanel.add(vehiclePanel, gbc);
                }
            }

            displayPanel.revalidate();
            displayPanel.repaint();
        });

        setVisible(true);
    }

    // Fungsi untuk membuat panel kendaraan
    private JPanel createVehiclePanel(String name, String price, String imagePath) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(170, 200));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Label gambar
        JLabel imageLabel;
        File imageFile = new File(imagePath);

        if (imageFile.exists()) {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image scaledImage = imageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(scaledImage));
        } else {
            imageLabel = new JLabel("No Image");
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setOpaque(true);
            imageLabel.setBackground(Color.LIGHT_GRAY);
        }

        // Panel bawah untuk teks (nama dan harga)
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        JLabel nameLabel = new JLabel(name, JLabel.CENTER);
        JLabel priceLabel = new JLabel("RP. " + price, JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        textPanel.add(nameLabel);
        textPanel.add(priceLabel);

        // Tambahkan event listener untuk menampilkan detail
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showVehicleDetailsDialog(name, price, imagePath);
            }
        });

        // Tambahkan komponen ke panel
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(textPanel, BorderLayout.SOUTH);

        return panel;
    }

private void showVehicleDetailsDialog(String name, String price, String imagePath) {
    JDialog detailDialog = new JDialog(this, "Vehicle Details", true);
    detailDialog.setSize(400, 600);
    detailDialog.setLayout(new BorderLayout(15, 10));

    JLabel imageLabel;
    File imageFile = new File(imagePath);
    if (imageFile.exists()) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image scaledImage = imageIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        imageLabel = new JLabel(new ImageIcon(scaledImage));
    } else {
        imageLabel = new JLabel("No Image");
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setOpaque(true);
        imageLabel.setBackground(Color.LIGHT_GRAY);
    }
    detailDialog.add(imageLabel, BorderLayout.NORTH);

    JPanel detailPanel = new JPanel();
    detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));

    JLabel nameLabel = new JLabel("Vehicle: " + name);
    JLabel priceLabel = new JLabel("Daily Rate: RP. " + price);
    nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    detailPanel.add(nameLabel);
    detailPanel.add(priceLabel);

    JPanel rentalPanel = new JPanel();
    JLabel daysLabel = new JLabel("Rental Days:");
    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 30, 1);
    JSpinner daysSpinner = new JSpinner(spinnerModel);
    rentalPanel.add(daysLabel);
    rentalPanel.add(daysSpinner);

    JLabel totalPriceLabel = new JLabel("Total Price: RP. " + price);
    totalPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    daysSpinner.addChangeListener(e -> {
        int days = (int) daysSpinner.getValue();
        double dailyRate = Double.parseDouble(price);
        double totalPrice = dailyRate * days;
        totalPriceLabel.setText(String.format("Total Price: RP. %.2f", totalPrice));
    });

    JButton rentButton = new JButton("Rent");
    rentButton.addActionListener(e -> {
        int days = (int) daysSpinner.getValue();
        double dailyRate = Double.parseDouble(price);
        double totalPrice = dailyRate * days;

        int confirmation = JOptionPane.showConfirmDialog(
                detailDialog,
                String.format("Rent %s for %d days?\nTotal Price: RP. %.2f", name, days, totalPrice),
                "Confirm Rental",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            // Close all windows associated with RentalMenuFrame
            Window[] windows = Window.getWindows();
            for (Window window : windows) {
                if (window instanceof RentalMenuFrame) {
                    window.dispose();
                }
            }
            
            // Open RentalForm
            new RentalForm(name, price, days);
        }
    });

    detailPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    detailPanel.add(rentalPanel);
    detailPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    detailPanel.add(totalPriceLabel);
    detailPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    detailPanel.add(rentButton);

    detailDialog.add(detailPanel, BorderLayout.CENTER);
    detailDialog.setLocationRelativeTo(this);
    detailDialog.setVisible(true);
}
}
