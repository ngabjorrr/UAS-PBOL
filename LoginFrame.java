package UAS_PBOL;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Login");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama dengan BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(68, 69, 198)); // Ganti warna dengan RGB yang dihasilkan
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Ikon profil
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon(new ImageIcon("path/to/icon.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(iconLabel);

        // Spasi
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Tambahkan logo username di atas field
        JLabel usernameLogoLabel = new JLabel();
        usernameLogoLabel.setIcon(new ImageIcon(
                new ImageIcon("D:/Download/Eastmotel666.png") // Ganti dengan path ke file gambar
                        .getImage().getScaledInstance(230, 110, Image.SCALE_SMOOTH) // Atur ukuran logo
        ));
        usernameLogoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Tengah
        mainPanel.add(usernameLogoLabel); // Tambahkan logo ke mainPanel
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spasi di bawah logo

        // GridBagLayout panel untuk username dan password field
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridBagLayout());
        fieldsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0); // spacing antara fields

        // Field username dengan placeholder
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        usernamePanel.setBackground(Color.WHITE);
        usernamePanel.setPreferredSize(new Dimension(300, 40));
        usernamePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel userIcon = new JLabel("\uD83D\uDC64"); // Unicode icon for user
        userIcon.setForeground(new Color(150, 150, 150));
        usernamePanel.add(userIcon);

        JTextField usernameField = new JTextField("Username", 20);
        usernameField.setBorder(null);
        usernameField.setForeground(new Color(150, 150, 150));
        usernamePanel.add(usernameField);

        // Placeholder functionality for username field
        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Username")) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setForeground(new Color(150, 150, 150));
                    usernameField.setText("Username");
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(usernamePanel, gbc);

        // Field password dengan placeholder
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.setPreferredSize(new Dimension(300, 40));
        passwordPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel lockIcon = new JLabel("\uD83D\uDD12"); // Unicode icon for lock
        lockIcon.setForeground(new Color(150, 150, 150));
        passwordPanel.add(lockIcon);

        JPasswordField passwordField = new JPasswordField("Password", 20);
        passwordField.setBorder(null);
        passwordField.setForeground(new Color(150, 150, 150));
        passwordField.setEchoChar((char) 0); // Display password as plain text initially
        passwordPanel.add(passwordField);

        // Placeholder functionality for password field
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals("Password")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('•'); // Hide password input
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setForeground(new Color(150, 150, 150));
                    passwordField.setText("Password");
                    passwordField.setEchoChar((char) 0); // Display placeholder text again
                }
            }
        });

        gbc.gridy = 1;
        fieldsPanel.add(passwordPanel, gbc);

        mainPanel.add(fieldsPanel);

        // Spasi
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Tombol login
        JButton loginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background color
                g2.setColor(new Color(64, 64, 64));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                // Font color and text alignment
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 16));
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

        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (validateLogin(username, password)) {
                new RentalMenuFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Menambahkan loginButton di tengah-tengah bawah mainPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        mainPanel.add(buttonPanel);

        // Text label for account creation link
        JLabel signupLabel = new JLabel("Belum memiliki akun? ");
        signupLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupLabel.setForeground(new Color(255, 255, 255));

        JLabel registerLabel = new JLabel("Daftar Sekarang");
        registerLabel.setForeground(new Color(255, 255, 0)); // Blue color
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hand cursor for link-like effect
        registerLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override   
            public void mouseClicked(MouseEvent e) {
                // Buka SignUpFrame dan sembunyikan LoginFrame
                new SignUpFrame().setVisible(true); // Membuka frame pendaftaran
                LoginFrame.this.setVisible(false); // Hanya sembunyikan, bukan dispose
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerLabel.setText("<html><u>Daftar Sekarang</u></html>"); // Efek hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerLabel.setText("Daftar Sekarang");
            }
        });

        JPanel signupPanel = new JPanel();
        signupPanel.setOpaque(false);
        signupPanel.add(signupLabel);
        signupPanel.add(registerLabel);

        mainPanel.add(signupPanel);

        // Tambahkan panel utama ke frame
        add(mainPanel);
    }

private boolean validateLogin(String username, String password) {
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    String dbUser = "hr";
    String dbPassword = "biliwatti";

    try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword)) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // Return true if user exists
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}
