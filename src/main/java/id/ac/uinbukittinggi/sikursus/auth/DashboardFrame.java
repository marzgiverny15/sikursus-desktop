
package id.ac.uinbukittinggi.sikursus.auth;

import config.Database;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import view.FormInputPeserta;
import view.FormPendaftaranKursus;
import view.FormDataKursus;
import view.FormDataInstruktur;
import view.FormDataKelas;

public class DashboardFrame extends JFrame {

    private final Color SIDEBAR = new Color(25, 35, 55);
    private final Color BACKGROUND = new Color(245, 247, 251);
    private final Color ACCENT = new Color(244, 81, 30);
    private final Color TEXT = new Color(45, 55, 72);
    private final Color MUTED = new Color(120, 130, 148);

    private final Map<String, JFrame> openForms =
            new HashMap<String, JFrame>();

    private final String username;
    private final String role;
    private final boolean isAdmin;

    public DashboardFrame() {

        User user = UserSession.getCurrentUser();

        username = user != null
                ? user.getUsername() : "Pengguna";

        role = user != null
                ? user.getRole() : "UNKNOWN";

        isAdmin = "ADMIN".equalsIgnoreCase(role);

        setTitle("SIKURSUS - Dashboard " + role);
        setSize(1200, 760);
        setMinimumSize(new Dimension(950, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buatSidebar(), BorderLayout.WEST);
        add(buatMainPanel(), BorderLayout.CENTER);
    }

    // =========================================
    // SIDEBAR
    // =========================================

    private JPanel buatSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR);
        sidebar.setPreferredSize(new Dimension(245, 760));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(28, 20, 22, 20));

        JLabel logo = new JLabel("SIKURSUS");
        logo.setFont(new Font("Arial", Font.BOLD, 25));
        logo.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("COURSE MANAGEMENT");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 10));
        subtitle.setForeground(new Color(170, 180, 200));

        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(subtitle);
        sidebar.add(Box.createVerticalStrut(35));

        JPanel rolePanel = new JPanel();
        rolePanel.setLayout(new BoxLayout(rolePanel, BoxLayout.Y_AXIS));

        rolePanel.setBackground(
                isAdmin
                ? new Color(65, 48, 52)
                : new Color(37, 63, 78));

        rolePanel.setBorder(new EmptyBorder(13, 13, 13, 13));
        rolePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rolePanel.setMaximumSize(new Dimension(220, 75));

        JLabel roleTitle = new JLabel(
                isAdmin ? "ADMINISTRATOR" : "OPERATIONAL USER");

        roleTitle.setFont(new Font("Arial", Font.BOLD, 11));
        roleTitle.setForeground(
                isAdmin ? new Color(255, 170, 140)
                        : new Color(120, 210, 220));

        JLabel roleName = new JLabel(username);
        roleName.setFont(new Font("Arial", Font.BOLD, 13));
        roleName.setForeground(Color.WHITE);

        rolePanel.add(roleTitle);
        rolePanel.add(Box.createVerticalStrut(7));
        rolePanel.add(roleName);

        sidebar.add(rolePanel);
        sidebar.add(Box.createVerticalStrut(35));

        JLabel menuLabel = new JLabel(
                isAdmin ? "ADMIN MENU" : "OPERATIONAL MENU");

        menuLabel.setFont(new Font("Arial", Font.BOLD, 11));
        menuLabel.setForeground(new Color(150, 165, 190));
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(menuLabel);
        sidebar.add(Box.createVerticalStrut(15));

        JButton btnDashboard = navButton("Dashboard");
        JButton btnPeserta = navButton("Data Peserta");
        JButton btnKursus = navButton("Data Kursus");
        JButton btnPendaftaran = navButton("Pendaftaran Kursus");
        JButton btnInstruktur = navButton("Data Instruktur");
        JButton btnKelas = navButton("Data Kelas");
        JButton btnLaporan = navButton("Laporan Peserta");

        tambahMenu(sidebar, btnDashboard);
        tambahMenu(sidebar, btnPeserta);
        tambahMenu(sidebar, btnKursus);
        tambahMenu(sidebar, btnPendaftaran);
        tambahMenu(sidebar, btnInstruktur);
        tambahMenu(sidebar, btnKelas);

        if (isAdmin) {
            sidebar.add(Box.createVerticalStrut(8));
            tambahMenu(sidebar, btnLaporan);
        }

        sidebar.add(Box.createVerticalGlue());

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(75, 85, 105));

        sidebar.add(separator);
        sidebar.add(Box.createVerticalStrut(18));

        JLabel info = new JLabel(username + " | " + role);
        info.setForeground(Color.WHITE);
        info.setFont(new Font("Arial", Font.BOLD, 11));
        info.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(info);
        sidebar.add(Box.createVerticalStrut(12));

        JButton btnLogout = navButton("Keluar / Logout");
        btnLogout.setForeground(new Color(255, 140, 120));

        sidebar.add(btnLogout);

        btnDashboard.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Anda sedang berada di Dashboard."
                )
        );

        btnPeserta.addActionListener(e ->
                bukaForm("peserta", FormInputPeserta::new)
        );

        btnKursus.addActionListener(e ->
                bukaForm("kursus", FormDataKursus::new)
        );

        btnPendaftaran.addActionListener(e ->
                bukaForm("pendaftaran", FormPendaftaranKursus::new)
        );

        btnInstruktur.addActionListener(e ->
                bukaForm("instruktur", FormDataInstruktur::new)
        );

        btnKelas.addActionListener(e ->
                bukaForm("kelas", FormDataKelas::new)
        );

        btnLaporan.addActionListener(e -> {

            if (!isAdmin) {
                JOptionPane.showMessageDialog(
                        this,
                        "Akses ditolak! Hanya ADMIN yang dapat membuka laporan.",
                        "Akses Ditolak",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Modul laporan akan diintegrasikan pada Pertemuan 12.",
                    "Laporan Peserta",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        btnLogout.addActionListener(e -> prosesLogout());

        return sidebar;
    }

    private void tambahMenu(JPanel panel, JButton button) {
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(button);
        panel.add(Box.createVerticalStrut(8));
    }

    // =========================================
    // MAIN PANEL
    // =========================================

    private JPanel buatMainPanel() {

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BACKGROUND);

        main.add(buatHeader(), BorderLayout.NORTH);

        JPanel content = isAdmin
                ? buatKontenAdmin()
                : buatKontenOperator();

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        main.add(scroll, BorderLayout.CENTER);

        return main;
    }

    // =========================================
    // HEADER + POPUP BUTTON
    // =========================================

    private JPanel buatHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(22, 30, 22, 30));

        JPanel kiri = new JPanel();
        kiri.setLayout(new BoxLayout(kiri, BoxLayout.Y_AXIS));
        kiri.setOpaque(false);

        JLabel title = new JLabel(
                isAdmin ? "Dashboard Administrator"
                        : "Dashboard Operator");

        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                isAdmin
                ? "Pusat kendali sistem manajemen kursus"
                : "Pusat aktivitas operasional kursus");

        subtitle.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitle.setForeground(MUTED);

        kiri.add(title);
        kiri.add(Box.createVerticalStrut(5));
        kiri.add(subtitle);

        JLabel badge = new JLabel(
                isAdmin ? "  ADMIN  " : "  OPERATOR  ");

        badge.setOpaque(true);
        badge.setBackground(
                isAdmin ? new Color(255, 235, 225)
                        : new Color(225, 242, 248));

        badge.setForeground(
                isAdmin ? ACCENT : new Color(30, 120, 150));

        badge.setFont(new Font("Arial", Font.BOLD, 12));
        badge.setBorder(new EmptyBorder(9, 12, 9, 12));

        JButton btnPopup = new JButton("⋮");
        btnPopup.setFont(new Font("Arial", Font.BOLD, 25));
        btnPopup.setForeground(TEXT);
        btnPopup.setBackground(Color.WHITE);
        btnPopup.setBorder(new EmptyBorder(5, 15, 5, 15));
        btnPopup.setFocusPainted(false);
        btnPopup.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel kanan = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 12, 0));

        kanan.setOpaque(false);
        kanan.add(badge);
        kanan.add(btnPopup);

        JPopupMenu popup = buatPopupMenu();

        btnPopup.addActionListener(e ->
                popup.show(btnPopup, 0, btnPopup.getHeight())
        );

        header.add(kiri, BorderLayout.WEST);
        header.add(kanan, BorderLayout.EAST);

        return header;
    }

    // =========================================
    // POPUP MENU
    // =========================================

    private JPopupMenu buatPopupMenu() {

        JPopupMenu popup = new JPopupMenu();

        JMenuItem profil = new JMenuItem("Profil Pengguna");
        JMenuItem pengaturan = new JMenuItem("Pengaturan");
        JMenuItem laporan = new JMenuItem("Laporan Peserta");
        JMenuItem logout = new JMenuItem("Logout");

        profil.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Username: " + username
                        + "\nRole: " + role,
                        "Profil Pengguna",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        pengaturan.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Pengaturan SIKURSUS",
                        "Pengaturan",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        if (isAdmin) {

            laporan.addActionListener(e ->
                    JOptionPane.showMessageDialog(
                            this,
                            "Modul laporan akan diintegrasikan "
                            + "pada Pertemuan 12.",
                            "Laporan Peserta",
                            JOptionPane.INFORMATION_MESSAGE
                    )
            );

            popup.add(laporan);
            popup.addSeparator();
        }

        logout.addActionListener(e -> prosesLogout());

        popup.add(profil);
        popup.add(pengaturan);
        popup.addSeparator();
        popup.add(logout);

        return popup;
    }

    // =========================================
    // DASHBOARD ADMIN
    // =========================================

    private JPanel buatKontenAdmin() {

        JPanel content = buatPanelKonten();

        content.add(buatWelcomePanel(
                "Selamat Datang, " + username + "!",
                "Anda memiliki akses penuh untuk mengelola SIKURSUS."
        ));

        content.add(Box.createVerticalStrut(25));

        content.add(judulBagian(
                "Ringkasan Sistem",
                "Statistik data yang tersimpan di database"
        ));

        content.add(Box.createVerticalStrut(15));

        JPanel statistik = new JPanel(
                new GridLayout(2, 3, 15, 15));

        statistik.setBackground(BACKGROUND);
        statistik.setAlignmentX(Component.LEFT_ALIGNMENT);

        statistik.add(buatStatistik(
                "TOTAL PESERTA",
                hitungData("peserta"),
                new Color(244, 81, 30)));

        statistik.add(buatStatistik(
                "TOTAL KURSUS",
                hitungData("kursus"),
                new Color(52, 120, 210)));

        statistik.add(buatStatistik(
                "TOTAL INSTRUKTUR",
                hitungData("instruktur"),
                new Color(35, 160, 125)));

        statistik.add(buatStatistik(
                "TOTAL KELAS",
                hitungData("kelas"),
                new Color(220, 145, 45)));

        statistik.add(buatStatistik(
                "TOTAL PENDAFTARAN",
                hitungData("pendaftaran"),
                new Color(130, 95, 190)));

        content.add(statistik);
        content.add(Box.createVerticalStrut(30));

        content.add(judulBagian(
                "Manajemen Sistem",
                "Akses seluruh modul pengelolaan SIKURSUS"
        ));

        content.add(Box.createVerticalStrut(15));

        JPanel cards = new JPanel(new GridLayout(0, 2, 15, 15));
        cards.setBackground(BACKGROUND);
        cards.setAlignmentX(Component.LEFT_ALIGNMENT);

        cards.add(createCard("01", "Data Peserta",
                "Kelola informasi peserta kursus",
                new Color(244, 81, 30)));

        cards.add(createCard("02", "Data Kursus",
                "Kelola program kursus",
                new Color(52, 120, 210)));

        cards.add(createCard("03", "Pendaftaran",
                "Kelola transaksi pendaftaran",
                new Color(35, 160, 125)));

        cards.add(createCard("04", "Data Instruktur",
                "Kelola informasi pengajar",
                new Color(220, 145, 45)));

        cards.add(createCard("05", "Data Kelas",
                "Kelola kapasitas dan jadwal kelas",
                new Color(35, 145, 170)));

        cards.add(createCard("06", "Laporan Peserta",
                "Akses laporan dan rekapitulasi data",
                new Color(130, 95, 190)));

        content.add(cards);
        content.add(Box.createVerticalStrut(25));
        content.add(buatFooter());

        return content;
    }

    // =========================================
    // DASHBOARD OPERATOR
    // =========================================

    private JPanel buatKontenOperator() {

        JPanel content = buatPanelKonten();

        content.add(buatWelcomePanel(
                "Halo, " + username + "!",
                "Selamat bekerja. Kelola data dan transaksi pendaftaran kursus melalui menu operasional."
        ));

        content.add(Box.createVerticalStrut(25));

        content.add(judulBagian(
                "Aktivitas Operasional",
                "Modul yang dapat digunakan oleh Operator"
        ));

        content.add(Box.createVerticalStrut(15));

        JPanel cards = new JPanel(new GridLayout(0, 2, 16, 16));
        cards.setBackground(BACKGROUND);
        cards.setAlignmentX(Component.LEFT_ALIGNMENT);

        cards.add(createCard("01", "Pendaftaran Kursus",
                "Input dan proses pendaftaran peserta",
                new Color(35, 160, 125)));

        cards.add(createCard("02", "Data Peserta",
                "Kelola informasi peserta kursus",
                new Color(244, 81, 30)));

        cards.add(createCard("03", "Data Kursus",
                "Lihat dan kelola program kursus",
                new Color(52, 120, 210)));

        cards.add(createCard("04", "Data Instruktur",
                "Kelola informasi tenaga pengajar",
                new Color(220, 145, 45)));

        cards.add(createCard("05", "Data Kelas",
                "Kelola kelas dan jadwal kursus",
                new Color(35, 145, 170)));

        content.add(cards);
        content.add(Box.createVerticalStrut(25));

        JPanel catatan = new JPanel();
        catatan.setLayout(new BoxLayout(catatan, BoxLayout.Y_AXIS));
        catatan.setBackground(new Color(230, 242, 249));
        catatan.setBorder(new EmptyBorder(20, 20, 20, 20));
        catatan.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel catatanTitle = new JLabel("INFORMASI OPERATOR");
        catatanTitle.setFont(new Font("Arial", Font.BOLD, 12));
        catatanTitle.setForeground(new Color(30, 100, 135));

        JLabel catatanIsi = new JLabel(
                "<html>Gunakan menu operasional untuk mengelola data "
                + "dan memproses pendaftaran peserta. "
                + "Akses laporan hanya tersedia untuk Administrator.</html>"
        );

        catatanIsi.setFont(new Font("Arial", Font.PLAIN, 12));
        catatanIsi.setForeground(TEXT);

        catatan.add(catatanTitle);
        catatan.add(Box.createVerticalStrut(9));
        catatan.add(catatanIsi);

        content.add(catatan);
        content.add(Box.createVerticalStrut(25));
        content.add(buatFooter());

        return content;
    }

    // =========================================
    // KOMPONEN KONTEN
    // =========================================

    private JPanel buatPanelKonten() {

        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(28, 30, 28, 30));

        return panel;
    }

    private JPanel buatWelcomePanel(
            String judul,
            String deskripsi) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setBackground(
                isAdmin
                ? new Color(255, 245, 239)
                : new Color(235, 245, 250));

        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel(judul);
        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setForeground(TEXT);

        JLabel desc = new JLabel(deskripsi);
        desc.setFont(new Font("Arial", Font.PLAIN, 13));
        desc.setForeground(MUTED);

        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(desc);

        return panel;
    }

    private JPanel judulBagian(
            String judul,
            String deskripsi) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel(judul);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(TEXT);

        JLabel desc = new JLabel(deskripsi);
        desc.setFont(new Font("Arial", Font.PLAIN, 12));
        desc.setForeground(MUTED);

        panel.add(title);
        panel.add(Box.createVerticalStrut(5));
        panel.add(desc);

        return panel;
    }

    private JPanel buatStatistik(
            String judul,
            String nilai,
            Color warna) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(20, 18, 20, 18));

        JLabel label = new JLabel(judul);
        label.setFont(new Font("Arial", Font.BOLD, 10));
        label.setForeground(MUTED);

        JLabel angka = new JLabel(nilai);
        angka.setFont(new Font("Arial", Font.BOLD, 29));
        angka.setForeground(warna);

        JLabel keterangan = new JLabel("Data database");
        keterangan.setFont(new Font("Arial", Font.PLAIN, 10));
        keterangan.setForeground(MUTED);

        card.add(label);
        card.add(Box.createVerticalStrut(13));
        card.add(angka);
        card.add(Box.createVerticalStrut(5));
        card.add(keterangan);

        return card;
    }

    private String hitungData(String namaTabel) {

        String sql = "SELECT COUNT(*) AS total FROM " + namaTabel;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return String.valueOf(rs.getInt("total"));
            }

        } catch (SQLException e) {
            System.err.println("Gagal menghitung tabel " + namaTabel);
            e.printStackTrace();
        }

        return "-";
    }

    private JPanel createCard(
            String nomor,
            String judul,
            String deskripsi,
            Color warna) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel number = new JLabel(nomor);
        number.setFont(new Font("Arial", Font.BOLD, 13));
        number.setForeground(warna);

        JLabel title = new JLabel(judul);
        title.setFont(new Font("Arial", Font.BOLD, 17));
        title.setForeground(TEXT);

        JLabel desc = new JLabel(
                "<html><div style='width:300px'>"
                + deskripsi
                + "</div></html>");

        desc.setFont(new Font("Arial", Font.PLAIN, 11));
        desc.setForeground(MUTED);

        card.add(number);
        card.add(Box.createVerticalStrut(12));
        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(desc);

        return card;
    }

    private JLabel buatFooter() {

        JLabel footer = new JLabel(
                "SIKURSUS Desktop Application | Sistem Informasi Manajemen Kursus"
        );

        footer.setFont(new Font("Arial", Font.PLAIN, 11));
        footer.setForeground(new Color(140, 145, 155));
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);

        return footer;
    }

    // =========================================
    // NAVIGATION BUTTON
    // =========================================

    private JButton navButton(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(new Color(220, 225, 235));
        button.setBackground(SIDEBAR);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(210, 42));
        button.setPreferredSize(new Dimension(210, 42));
        button.setBorder(new EmptyBorder(10, 12, 10, 10));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    // =========================================
    // MENCEGAH FORM TERBUKA BERULANG
    // =========================================

    private void bukaForm(
            String key,
            Supplier<? extends JFrame> pembuat) {

        JFrame formLama = openForms.get(key);

        if (formLama != null && formLama.isDisplayable()) {

            if (!formLama.isVisible()) {
                formLama.setVisible(true);
            }

            formLama.setState(JFrame.NORMAL);
            formLama.toFront();
            formLama.requestFocus();

            return;
        }

        final JFrame formBaru = pembuat.get();

        openForms.put(key, formBaru);

        formBaru.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {

                if (openForms.get(key) == formBaru) {
                    openForms.remove(key);
                }
            }
        });

        formBaru.setLocationRelativeTo(this);
        formBaru.setVisible(true);
    }

    // =========================================
    // LOGOUT
    // =========================================

    private void prosesLogout() {

        int pilihan = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin keluar dari SIKURSUS?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (pilihan == JOptionPane.YES_OPTION) {

            UserSession.logout();

            new LoginFrame().setVisible(true);

            dispose();
        }
    }
}
