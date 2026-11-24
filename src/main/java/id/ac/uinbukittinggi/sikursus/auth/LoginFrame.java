
package id.ac.uinbukittinggi.sikursus.auth;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnExit;
    private JCheckBox chkShowPassword;

    private final Color NAVY = new Color(25, 35, 55);
    private final Color ORANGE = new Color(244, 81, 30);
    private final Color BACKGROUND = new Color(245, 247, 251);
    private final Color TEXT = new Color(45, 55, 72);

    public LoginFrame() {

        setTitle("SIKURSUS - Login");
        setSize(850, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new GridLayout(1, 2));

        buatPanelBranding();
        buatPanelLogin();

    }

    // PANEL BRANDING
    private void buatPanelBranding() {

        JPanel branding = new JPanel();
        branding.setBackground(NAVY);
        branding.setLayout(new BoxLayout(branding, BoxLayout.Y_AXIS));
        branding.setBorder(
            new EmptyBorder(65, 40, 50, 35)
        );

        JLabel logo = new JLabel("SIKURSUS");
        logo.setFont(new Font("Arial", Font.BOLD, 32));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel garis = new JLabel("━━━━━━━━━━━━━━━━━━");
        garis.setForeground(ORANGE);
        garis.setFont(new Font("Arial", Font.BOLD, 16));
        garis.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel(
            "COURSE MANAGEMENT SYSTEM"
        );
        subtitle.setFont(new Font("Arial", Font.BOLD, 11));
        subtitle.setForeground(new Color(180, 190, 210));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel welcome = new JLabel(
            "<html>Kelola Kursus<br>Lebih Mudah.</html>"
        );
        welcome.setFont(new Font("Arial", Font.BOLD, 30));
        welcome.setForeground(Color.WHITE);
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel description = new JLabel(
            "<html><div style='width:270px;'>"
            + "Sistem informasi manajemen kursus "
            + "untuk membantu pengelolaan data peserta, "
            + "program kursus, dan pendaftaran."
            + "</div></html>"
        );

        description.setFont(new Font("Arial", Font.PLAIN, 13));
        description.setForeground(new Color(190, 200, 215));
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel footer = new JLabel(
            "SIKURSUS DESKTOP APPLICATION"
        );
        footer.setFont(new Font("Arial", Font.BOLD, 10));
        footer.setForeground(new Color(140, 155, 180));
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);

        branding.add(logo);
        branding.add(Box.createVerticalStrut(12));
        branding.add(garis);
        branding.add(Box.createVerticalStrut(15));
        branding.add(subtitle);
        branding.add(Box.createVerticalStrut(85));
        branding.add(welcome);
        branding.add(Box.createVerticalStrut(18));
        branding.add(description);
        branding.add(Box.createVerticalGlue());
        branding.add(footer);

        add(branding);
    }

    // PANEL LOGIN
    private void buatPanelLogin() {

        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(
            new EmptyBorder(75, 45, 50, 45)
        );

        JLabel title = new JLabel("Selamat Datang!");
        title.setFont(new Font("Arial", Font.BOLD, 27));
        title.setForeground(TEXT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel(
            "Silakan masuk ke akun SIKURSUS Anda."
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitle.setForeground(new Color(120, 130, 145));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblUsername = new JLabel("Username");
        aturLabel(lblUsername);

        txtUsername = new JTextField();
        aturInput(txtUsername);
        txtUsername.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 43)
        );

        JLabel lblPassword = new JLabel("Password");
        aturLabel(lblPassword);

        txtPassword = new JPasswordField();
        aturInput(txtPassword);
        txtPassword.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 43)
        );

        chkShowPassword = new JCheckBox("Tampilkan password");
        chkShowPassword.setBackground(BACKGROUND);
        chkShowPassword.setFont(
            new Font("Arial", Font.PLAIN, 11)
        );
        chkShowPassword.setForeground(
            new Color(100, 110, 125)
        );
        chkShowPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        chkShowPassword.addActionListener(e -> {
            if (chkShowPassword.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('•');
            }
        });

        btnLogin = new JButton("MASUK");
        btnLogin.setFont(
            new Font("Arial", Font.BOLD, 13)
        );
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(ORANGE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );
        btnLogin.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 45)
        );
        btnLogin.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnExit = new JButton("KELUAR");
        btnExit.setFont(
            new Font("Arial", Font.BOLD, 12)
        );
        btnExit.setForeground(TEXT);
        btnExit.setBackground(Color.WHITE);
        btnExit.setFocusPainted(false);
        btnExit.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );
        btnExit.setMaximumSize(
            new Dimension(Integer.MAX_VALUE, 42)
        );
        btnExit.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel footer = new JLabel(
            "© SIKURSUS | Sistem Informasi Manajemen Kursus"
        );
        footer.setFont(
            new Font("Arial", Font.PLAIN, 10)
        );
        footer.setForeground(
            new Color(145, 150, 165)
        );
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);

        // SUSUNAN PANEL
        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(subtitle);

        panel.add(Box.createVerticalStrut(40));

        panel.add(lblUsername);
        panel.add(Box.createVerticalStrut(8));
        panel.add(txtUsername);

        panel.add(Box.createVerticalStrut(22));

        panel.add(lblPassword);
        panel.add(Box.createVerticalStrut(8));
        panel.add(txtPassword);

        panel.add(Box.createVerticalStrut(10));
        panel.add(chkShowPassword);

        panel.add(Box.createVerticalStrut(28));
        panel.add(btnLogin);

        panel.add(Box.createVerticalStrut(12));
        panel.add(btnExit);

        panel.add(Box.createVerticalGlue());
        panel.add(footer);

        add(panel);

        // AKSI TOMBOL
        btnLogin.addActionListener(e -> prosesLogin());

        btnExit.addActionListener(e -> {
            int pilihan = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin keluar?",
                "Konfirmasi Keluar",
                JOptionPane.YES_NO_OPTION
            );

            if (pilihan == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        txtPassword.addActionListener(e -> prosesLogin());
    }

    // FORMAT LABEL
    private void aturLabel(JLabel label) {

        label.setFont(
            new Font("Arial", Font.BOLD, 12)
        );
        label.setForeground(TEXT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // FORMAT INPUT
    private void aturInput(JTextField field) {

        field.setFont(
            new Font("Arial", Font.PLAIN, 13)
        );
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT);
        field.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(215, 220, 230)
                ),
                new EmptyBorder(10, 12, 10, 12)
            )
        );
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // PROSES LOGIN
    private void prosesLogin() {

        String username = txtUsername.getText().trim();
        String password = new String(
            txtPassword.getPassword()
        );

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Username dan password harus diisi!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        UserDAO dao = new UserDAO();
        User user = dao.login(username, password);

        if (user != null) {

            UserSession.setCurrentUser(user);

            JOptionPane.showMessageDialog(
                this,
                "Login berhasil! Selamat datang, "
                + user.getUsername(),
                "Login Berhasil",
                JOptionPane.INFORMATION_MESSAGE
            );

            new DashboardFrame().setVisible(true);
            dispose();

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Username atau password salah!",
                "Login Gagal",
                JOptionPane.ERROR_MESSAGE
            );

            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }
}
