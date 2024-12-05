/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UAS_PBOL;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SignUpFrame extends JFrame {

    public SignUpFrame() {
        setTitle("Pendaftaran");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(68, 69, 198));

        // Header Label
        JLabel headerLabel = new JLabel("Daftar");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerLabel.setForeground(new Color(255, 200, 175));
        mainPanel.add(headerLabel);

        JLabel subheaderLabel = new JLabel("Silahkan membuat akun baru anda!");
        subheaderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subheaderLabel.setForeground(Color.WHITE);
        mainPanel.add(subheaderLabel);

        // Jarak antara header dan field pertama
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Username Field
        JPanel usernamePanel = createFieldPanel("Masukkan Username");
        mainPanel.add(usernamePanel);

        // Tambahkan jarak antar panel
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Nama Lengkap Field
        JPanel fullNamePanel = createFieldPanel("Masukkan nama anda");
        mainPanel.add(fullNamePanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Email Field
        JPanel emailPanel = createFieldPanel("Masukkan Email ");
        mainPanel.add(emailPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Kata Sandi Field
        JPanel passwordPanel = createPasswordPanel("Kata sandi minimal 8 digit karakter");
        mainPanel.add(passwordPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Konfirmasi Kata Sandi Field
        JPanel confirmPasswordPanel = createPasswordPanel("Masukkan ulang kata sandi anda");
        mainPanel.add(confirmPasswordPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Terms and Conditions checkbox
        JCheckBox termsCheckbox = new JCheckBox("Saya setuju dengan syarat dan ketentuan");
        termsCheckbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        termsCheckbox.setForeground(Color.WHITE);
        termsCheckbox.setBackground(new Color(68, 69, 198));
        mainPanel.add(termsCheckbox);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Register button
        JButton registerButton = new JButton("   Daftar   ") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(64, 64, 64));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
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

        registerButton.setPreferredSize(new Dimension(170, 35));
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerButton.addActionListener(e -> {
            String username = ((JTextField) usernamePanel.getComponent(0)).getText();
            String fullName = ((JTextField) fullNamePanel.getComponent(0)).getText();
            String email = ((JTextField) emailPanel.getComponent(0)).getText();
            String password = new String(((JPasswordField) passwordPanel.getComponent(0)).getPassword());
            String confirmPassword = new String(((JPasswordField) confirmPasswordPanel.getComponent(0)).getPassword());

            if (!termsCheckbox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Anda harus menyetujui syarat dan ketentuan untuk melanjutkan.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (password.length() < 8) {
                JOptionPane.showMessageDialog(this, "Kata sandi harus memiliki minimal 8 karakter.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Kata sandi dan konfirmasi kata sandi tidak cocok.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                if (registerUser(username, fullName, email, password)) {
                    JOptionPane.showMessageDialog(this, "Registrasi Berhasil!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    new LoginFrame().setVisible(true); // Open LoginFrame
                    dispose(); // Close SignUpFrame
                } else {
                    JOptionPane.showMessageDialog(this, "Registrasi Gagal. Silakan coba lagi.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mainPanel.add(registerButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Login link
        JLabel validasiLabel = new JLabel("Sudah punya akun? ");
        validasiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        validasiLabel.setForeground(Color.WHITE);

        JLabel loginLabel = new JLabel("Masuk");
        loginLabel.setForeground(new Color(255, 255, 0));
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new LoginFrame().setVisible(true);
                SignUpFrame.this.dispose();
            }

            public void mouseEntered(MouseEvent e) {
                loginLabel.setText("<html><u>Masuk</u></html>");
            }
        });
        JPanel daftarPanel = new JPanel();
        daftarPanel.setOpaque(false);
        daftarPanel.add(validasiLabel);
        daftarPanel.add(loginLabel);

        mainPanel.add(daftarPanel);

        // Add main panel to frame
        add(mainPanel);
    }

    private boolean registerUser(String username, String fullName, String email, String password) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE"; // Ganti dengan URL database Anda
        String dbUser = "hr"; // Ganti dengan username database Anda
        String dbPassword = "biliwatti"; // Ganti dengan password database Anda

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword)) {
            String sql = "INSERT INTO users (id, username, full_name, email, password) VALUES (users_seq.NEXTVAL, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, fullName);
                statement.setString(3, email);
                statement.setString(4, password); // Pastikan untuk mengenkripsi password sebelum menyimpannya
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private JPanel createFieldPanel(String placeholder) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(320, 36));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 10, 5, 10)
        ));

        JTextField textField = new JTextField(placeholder, 20);
        textField.setForeground(Color.GRAY);
        textField.setBorder(null);
        textField.setPreferredSize(new Dimension(280, 24));
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

    private JPanel createPasswordPanel(String placeholder) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(320, 36));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 10, 5, 10)
        ));

        JPasswordField passwordField = new JPasswordField(placeholder, 20);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);
        passwordField.setBorder(null);
        passwordField.setPreferredSize(new Dimension(280, 24));

        passwordField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('•');
                }
            }

            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText(placeholder);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });

        panel.add(passwordField);
        return panel;
    }
}
