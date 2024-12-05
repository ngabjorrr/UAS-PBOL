package EastRent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentForm extends JFrame {

    private JTextField vehicleNameField;
    private JTextField priceField;
    private JTextField rentalDaysField;
    private JTextField nameField;
    private JTextField ktpField;
    private JTextField phoneField;
    private JRadioButton cashPayment;
    private JRadioButton transferPayment;

    public PaymentForm() {
        setTitle("Pilihan Pembayaran");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(85, 85, 220));
        add(mainPanel);

        // Header
        JLabel headerLabel = new JLabel("Pilihan Pembayaran", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        headerLabel.setForeground(new Color(255, 200, 175));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Input Fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(85, 85, 220));

        vehicleNameField = new JTextField();
        formPanel.add(createFieldPanel("Nama Kendaraan", vehicleNameField));
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        priceField = new JTextField();
        formPanel.add(createFieldPanel("Harga (Per Hari)", priceField));
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        rentalDaysField = new JTextField();
        formPanel.add(createFieldPanel("Jumlah Hari Sewa", rentalDaysField));
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        nameField = new JTextField();
        formPanel.add(createFieldPanel("Nama Anda", nameField));
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        ktpField = new JTextField();
        formPanel.add(createFieldPanel("Nomor KTP", ktpField));
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        phoneField = new JTextField();
        formPanel.add(createFieldPanel("Nomor Telepon", phoneField));
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Payment Options
        JPanel paymentPanel = new JPanel();
        paymentPanel.setBackground(new Color(85, 85, 220));
        paymentPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(85, 85, 220)),
                "Metode Pembayaran :",
                0, 0,
                new Font("Arial", Font.BOLD, 12),
                Color.YELLOW));
        paymentPanel.setLayout(new GridLayout(2, 1, 10, 5));

        cashPayment = new JRadioButton("Bayar di Lokasi");
        transferPayment = new JRadioButton("Transfer Bank");
        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(cashPayment);
        paymentGroup.add(transferPayment);

        cashPayment.setForeground(Color.WHITE);
        cashPayment.setBackground(new Color(85, 85, 220));
        transferPayment.setForeground(Color.WHITE);
        transferPayment.setBackground(new Color(85, 85, 220));

        paymentPanel.add(cashPayment);
        paymentPanel.add(transferPayment);

        // Event listener untuk transferPayment
        transferPayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Menampilkan daftar rekening saat transfer dipilih
                if (transferPayment.isSelected()) {
                    String[] bankAccounts = {
                            "Bank BCA - 1234567890 a.n. EastRent",
                            "Bank Mandiri - 0987654321 a.n. EastRent",
                            "Bank BRI - 1122334455 a.n. EastRent"
                    };

                    StringBuilder message = new StringBuilder("Silakan transfer ke salah satu rekening berikut:\n");
                    for (String account : bankAccounts) {
                        message.append(account).append("\n");
                    }

                    JOptionPane.showMessageDialog(
                            PaymentForm.this,
                            message.toString(),
                            "Informasi Rekening Bank",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        formPanel.add(paymentPanel);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Confirm Button
        JButton confirmButton = new JButton("  Konfirmasi Pemesanan  ") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(64, 64, 64));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 13));
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 4;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }

            @Override
            public boolean isContentAreaFilled() {
                return false;
            }
        };
        confirmButton.setPreferredSize(new Dimension(160, 40));
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);

        confirmButton.addActionListener(e -> {
            if (validateInput()) {
                JOptionPane.showMessageDialog(this, "Pemesanan Berhasil Dikonfirmasi!");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        buttonPanel.add(confirmButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createFieldPanel(String placeholder, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true));

        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.setPreferredSize(new Dimension(280, 25));
        textField.setBorder(null);

        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });

        panel.add(textField);
        return panel;
    }

    private boolean validateInput() {
        if (vehicleNameField.getText().isEmpty() || vehicleNameField.getText().equals("Nama Kendaraan") ||
                priceField.getText().isEmpty() || priceField.getText().equals("Harga (Per Hari)") ||
                rentalDaysField.getText().isEmpty() || rentalDaysField.getText().equals("Jumlah Hari Sewa") ||
                nameField.getText().isEmpty() || nameField.getText().equals("Nama Anda") ||
                ktpField.getText().isEmpty() || ktpField.getText().equals("Nomor KTP") ||
                phoneField.getText().isEmpty() || phoneField.getText().equals("Nomor Telepon")) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Integer.parseInt(priceField.getText());
            Integer.parseInt(rentalDaysField.getText());
            Long.parseLong(ktpField.getText());
            Long.parseLong(phoneField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Kolom Harga, Jumlah Hari Sewa, Nomor KTP, dan Nomor Telepon harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!cashPayment.isSelected() && !transferPayment.isSelected()) {
            JOptionPane.showMessageDialog(this, "Silakan pilih metode pembayaran!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PaymentForm payment = new PaymentForm();
            payment.setVisible(true);
        });
    }
}
