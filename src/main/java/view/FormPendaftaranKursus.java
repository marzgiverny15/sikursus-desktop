
package view;

import config.Database;
import dao.PendaftaranDAO;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FormPendaftaranKursus extends JFrame {

    private final Color NAVY = new Color(24, 35, 56);
    private final Color ORANGE = new Color(244, 91, 42);
    private final Color BG = new Color(242, 245, 250);
    private final Color TEXT = new Color(45, 55, 72);
    private final Color MUTED = new Color(120, 132, 150);
    private final Color BORDER = new Color(226, 231, 239);

    private JComboBox<PesertaItem> cmbPeserta;
    private JComboBox<String> cmbKursus;
    private JPasswordField txtKodeAkses;

    private JRadioButton rbMahasiswa;
    private JRadioButton rbUmum;
    private ButtonGroup groupJenis;

    private JPanel panelFasilitas;
    private JPanel panelUtama;

    private JTextArea txtRingkasan;
    private JLabel lblStatus;
    private JLabel lblPesertaPreview;
    private JLabel lblKursusPreview;
    private JLabel lblJenisPreview;
    private JLabel lblFasilitasPreview;

    private JButton btnProses;
    private JButton btnReset;

    private final List<JCheckBox> daftarCheckboxFasilitas =
            new ArrayList<JCheckBox>();

    private final PendaftaranDAO dao = new PendaftaranDAO();

    public FormPendaftaranKursus() {

        setTitle("SIKURSUS Desktop | Pendaftaran Kursus");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1120, 760);
        setMinimumSize(new Dimension(900, 650));
        setLocationRelativeTo(null);

        initComponents();

        muatPeserta();
        muatKursus();
        muatFasilitas();

        updatePreview();
    }

    private void initComponents() {

        panelUtama = new JPanel(new BorderLayout());
        panelUtama.setBackground(BG);

        panelUtama.add(buatHeader(), BorderLayout.NORTH);

        JPanel konten = new JPanel(new GridBagLayout());
        konten.setBackground(BG);
        konten.setBorder(new EmptyBorder(25, 28, 25, 28));

        JPanel cardForm = buatCardForm();
        JPanel cardPreview = buatCardPreview();

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.62;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 18);
        konten.add(cardForm, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.38;
        gbc.insets = new Insets(0, 0, 0, 0);
        konten.add(cardPreview, gbc);

        panelUtama.add(konten, BorderLayout.CENTER);
        setContentPane(panelUtama);
    }

    // HEADER
    private JPanel buatHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NAVY);
        header.setBorder(new EmptyBorder(22, 30, 22, 30));

        JPanel kiri = new JPanel(new BorderLayout(15, 0));
        kiri.setOpaque(false);

        JLabel logo = new JLabel("S");
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 25));
        logo.setForeground(Color.WHITE);
        logo.setOpaque(true);
        logo.setBackground(ORANGE);
        logo.setPreferredSize(new Dimension(52, 52));

        JPanel informasi = new JPanel(new GridLayout(2, 1, 0, 4));
        informasi.setOpaque(false);

        JLabel nama = new JLabel("SIKURSUS");
        nama.setFont(new Font("Segoe UI", Font.BOLD, 20));
        nama.setForeground(Color.WHITE);

        JLabel deskripsi = new JLabel(
                "Sistem Informasi Manajemen Kursus");
        deskripsi.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        deskripsi.setForeground(new Color(190, 200, 215));

        informasi.add(nama);
        informasi.add(deskripsi);

        kiri.add(logo, BorderLayout.WEST);
        kiri.add(informasi, BorderLayout.CENTER);

        JPanel kanan = new JPanel(new GridLayout(2, 1, 0, 4));
        kanan.setOpaque(false);

        JLabel halaman = new JLabel("TRANSAKSI");
        halaman.setFont(new Font("Segoe UI", Font.BOLD, 12));
        halaman.setForeground(new Color(255, 174, 140));
        halaman.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel judul = new JLabel("Pendaftaran Kursus");
        judul.setFont(new Font("Segoe UI", Font.BOLD, 17));
        judul.setForeground(Color.WHITE);
        judul.setHorizontalAlignment(SwingConstants.RIGHT);

        kanan.add(halaman);
        kanan.add(judul);

        header.add(kiri, BorderLayout.WEST);
        header.add(kanan, BorderLayout.EAST);

        return header;
    }

    // CARD FORM
    private JPanel buatCardForm() {

        JPanel card = buatPanelCard();
        card.setLayout(new BorderLayout(0, 20));
        card.setBorder(new EmptyBorder(24, 24, 24, 24));

        JPanel heading = new JPanel(new GridLayout(2, 1, 0, 5));
        heading.setOpaque(false);

        JLabel judul = new JLabel("Informasi Pendaftaran");
        judul.setFont(new Font("Segoe UI", Font.BOLD, 19));
        judul.setForeground(NAVY);

        JLabel sub = new JLabel(
                "Lengkapi data peserta dan pilih program kursus");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(MUTED);

        heading.add(judul);
        heading.add(sub);

        JPanel isi = new JPanel();
        isi.setOpaque(false);
        isi.setLayout(new BoxLayout(isi, BoxLayout.Y_AXIS));

        cmbPeserta = new JComboBox<PesertaItem>();
        cmbKursus = new JComboBox<String>();
        txtKodeAkses = new JPasswordField();

        rbMahasiswa = new JRadioButton("Mahasiswa");
        rbUmum = new JRadioButton("Umum");

        rbMahasiswa.setBackground(Color.WHITE);
        rbUmum.setBackground(Color.WHITE);
        rbMahasiswa.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rbUmum.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        groupJenis = new ButtonGroup();
        groupJenis.add(rbMahasiswa);
        groupJenis.add(rbUmum);

        aturInput(cmbPeserta);
        aturInput(cmbKursus);
        aturInput(txtKodeAkses);

        tambahField(isi, "Pilih Peserta", cmbPeserta);
        tambahField(isi, "Kode Akses", txtKodeAkses);

        JPanel jenis = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 15, 0));
        jenis.setOpaque(false);
        jenis.add(rbMahasiswa);
        jenis.add(rbUmum);

        tambahField(isi, "Jenis Peserta", jenis);
        tambahField(isi, "Program Kursus", cmbKursus);

        JLabel labelFasilitas = buatLabel("Fasilitas Tambahan");
        labelFasilitas.setAlignmentX(Component.LEFT_ALIGNMENT);
        isi.add(labelFasilitas);
        isi.add(Box.createVerticalStrut(9));

        panelFasilitas = new JPanel();
        panelFasilitas.setLayout(new BoxLayout(
                panelFasilitas, BoxLayout.Y_AXIS));
        panelFasilitas.setBackground(new Color(248, 249, 252));
        panelFasilitas.setBorder(new EmptyBorder(12, 14, 12, 14));
        panelFasilitas.setAlignmentX(Component.LEFT_ALIGNMENT);
        isi.add(panelFasilitas);

        isi.add(Box.createVerticalStrut(22));

        JPanel tombol = new JPanel(new GridLayout(1, 2, 12, 0));
        tombol.setOpaque(false);
        tombol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        tombol.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnReset = buatTombol(
                "Reset Form", new Color(235, 238, 244), NAVY);

        btnProses = buatTombol(
                "Simpan Pendaftaran", ORANGE, Color.WHITE);

        tombol.add(btnReset);
        tombol.add(btnProses);

        isi.add(tombol);

        btnReset.addActionListener(e -> resetForm());
        btnProses.addActionListener(e -> prosesPendaftaran());

        cmbPeserta.addActionListener(e -> updatePreview());
        cmbKursus.addActionListener(e -> updatePreview());
        rbMahasiswa.addActionListener(e -> updatePreview());
        rbUmum.addActionListener(e -> updatePreview());

        card.add(heading, BorderLayout.NORTH);
        card.add(isi, BorderLayout.CENTER);

        return card;
    }

    // CARD PREVIEW
    private JPanel buatCardPreview() {

        JPanel card = buatPanelCard();
        card.setLayout(new BorderLayout(0, 15));
        card.setBorder(new EmptyBorder(24, 22, 24, 22));

        JPanel atas = new JPanel(new GridLayout(2, 1, 0, 5));
        atas.setOpaque(false);

        JLabel judul = new JLabel("Ringkasan Transaksi");
        judul.setFont(new Font("Segoe UI", Font.BOLD, 19));
        judul.setForeground(NAVY);

        JLabel sub = new JLabel("Periksa kembali data pendaftaran");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(MUTED);

        atas.add(judul);
        atas.add(sub);

        JPanel informasi = new JPanel();
        informasi.setLayout(new BoxLayout(informasi, BoxLayout.Y_AXIS));
        informasi.setOpaque(false);

        JPanel status = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 0, 0));
        status.setOpaque(false);

        lblStatus = new JLabel("  MENUNGGU PENDAFTARAN  ");
        lblStatus.setOpaque(true);
        lblStatus.setBackground(new Color(255, 244, 230));
        lblStatus.setForeground(new Color(180, 105, 20));
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblStatus.setBorder(new EmptyBorder(7, 8, 7, 8));

        status.add(lblStatus);

        informasi.add(status);
        informasi.add(Box.createVerticalStrut(22));

        lblPesertaPreview = buatNilaiPreview();
        lblKursusPreview = buatNilaiPreview();
        lblJenisPreview = buatNilaiPreview();
        lblFasilitasPreview = buatNilaiPreview();

        tambahPreview(informasi, "PESERTA", lblPesertaPreview);
        tambahPreview(informasi, "PROGRAM KURSUS", lblKursusPreview);
        tambahPreview(informasi, "JENIS PESERTA", lblJenisPreview);
        tambahPreview(informasi, "FASILITAS", lblFasilitasPreview);

        JSeparator garis = new JSeparator();
        garis.setForeground(BORDER);
        informasi.add(garis);
        informasi.add(Box.createVerticalStrut(18));

        JLabel catatan = new JLabel(
                "<html><div style='width:230px'>"
                + "Pastikan seluruh informasi sudah benar sebelum "
                + "menyimpan transaksi pendaftaran."
                + "</div></html>");
        catatan.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        catatan.setForeground(MUTED);

        informasi.add(catatan);

        txtRingkasan = new JTextArea();
        txtRingkasan.setEditable(false);
        txtRingkasan.setLineWrap(true);
        txtRingkasan.setWrapStyleWord(true);
        txtRingkasan.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtRingkasan.setForeground(TEXT);
        txtRingkasan.setBackground(new Color(248, 249, 252));
        txtRingkasan.setBorder(new EmptyBorder(12, 12, 12, 12));
        txtRingkasan.setText("Belum ada transaksi yang disimpan.");

        JScrollPane scroll = new JScrollPane(txtRingkasan);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));
        scroll.setPreferredSize(new Dimension(250, 155));

        JPanel bawah = new JPanel(new BorderLayout(0, 8));
        bawah.setOpaque(false);

        JLabel labelHasil = new JLabel("HASIL PENDAFTARAN");
        labelHasil.setFont(new Font("Segoe UI", Font.BOLD, 11));
        labelHasil.setForeground(MUTED);

        bawah.add(labelHasil, BorderLayout.NORTH);
        bawah.add(scroll, BorderLayout.CENTER);

        card.add(atas, BorderLayout.NORTH);
        card.add(informasi, BorderLayout.CENTER);
        card.add(bawah, BorderLayout.SOUTH);

        return card;
    }

    private JPanel buatPanelCard() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(1, 1, 1, 1)));
        return panel;
    }

    private JButton buatTombol(String teks, Color bg, Color fg) {
        JButton tombol = new JButton(teks);
        tombol.setBackground(bg);
        tombol.setForeground(fg);
        tombol.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tombol.setFocusPainted(false);
        tombol.setBorderPainted(false);
        tombol.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return tombol;
    }

    private JLabel buatLabel(String teks) {
        JLabel label = new JLabel(teks);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(TEXT);
        return label;
    }

    private JLabel buatNilaiPreview() {
        JLabel label = new JLabel("-");
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(NAVY);
        return label;
    }

    private void tambahPreview(
            JPanel panel, String judul, JLabel nilai) {

        JLabel label = new JLabel(judul);
        label.setFont(new Font("Segoe UI", Font.BOLD, 10));
        label.setForeground(MUTED);

        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        nilai.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(nilai);
        panel.add(Box.createVerticalStrut(18));
    }

    private void tambahField(
            JPanel panel, String judul, Component input) {

        JLabel label = buatLabel(judul);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        input.setMaximumSize(new Dimension(
                Integer.MAX_VALUE, 39));

        panel.add(label);
        panel.add(Box.createVerticalStrut(8));
        panel.add(input);
        panel.add(Box.createVerticalStrut(17));
    }

    private void aturInput(JComponent input) {
        input.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        input.setPreferredSize(new Dimension(350, 39));
        input.setMaximumSize(new Dimension(Integer.MAX_VALUE, 39));
    }

    // MODEL PESERTA
    private static class PesertaItem {

        private final String nim;
        private final String nama;

        public PesertaItem(String nim, String nama) {
            this.nim = nim;
            this.nama = nama;
        }

        public String getNim() {
            return nim;
        }

        @Override
        public String toString() {
            return nim + " - " + nama;
        }
    }

    // LOAD PESERTA
    private void muatPeserta() {

        cmbPeserta.removeAllItems();

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT nim, nama FROM peserta ORDER BY nama");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cmbPeserta.addItem(new PesertaItem(
                        rs.getString("nim"),
                        rs.getString("nama")));
            }

        } catch (SQLException e) {
            tampilkanError("Gagal memuat peserta.", e);
        }

        if (cmbPeserta.getItemCount() == 0) {
            cmbPeserta.addItem(
                    new PesertaItem("", "-- Belum ada peserta --"));
        }
    }

    // LOAD KURSUS
    private void muatKursus() {

        cmbKursus.removeAllItems();
        cmbKursus.addItem("-- Pilih Kursus --");

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT nama_kursus FROM kursus ORDER BY nama_kursus");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cmbKursus.addItem(rs.getString("nama_kursus"));
            }

        } catch (SQLException e) {
            tampilkanError("Gagal memuat kursus.", e);
        }
    }

    // LOAD FASILITAS
    private void muatFasilitas() {

        panelFasilitas.removeAll();
        daftarCheckboxFasilitas.clear();

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT nama_fasilitas FROM fasilitas ORDER BY nama_fasilitas");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                JCheckBox cb = new JCheckBox(
                        rs.getString("nama_fasilitas"));

                cb.setBackground(new Color(248, 249, 252));
                cb.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                cb.setFocusPainted(false);

                cb.addActionListener(e -> updatePreview());

                daftarCheckboxFasilitas.add(cb);
                panelFasilitas.add(cb);
            }

        } catch (SQLException e) {
            tampilkanError("Gagal memuat fasilitas.", e);
        }

        if (daftarCheckboxFasilitas.isEmpty()) {
            panelFasilitas.add(new JLabel("Belum ada fasilitas."));
        }

        panelFasilitas.revalidate();
        panelFasilitas.repaint();
    }

    // UPDATE PREVIEW
    private void updatePreview() {

        if (cmbPeserta == null || lblPesertaPreview == null) {
            return;
        }

        PesertaItem peserta =
                (PesertaItem) cmbPeserta.getSelectedItem();

        lblPesertaPreview.setText(
                peserta == null ? "-" : peserta.toString());

        lblKursusPreview.setText(
                cmbKursus.getSelectedIndex() <= 0
                ? "Belum dipilih"
                : cmbKursus.getSelectedItem().toString());

        if (rbMahasiswa.isSelected()) {
            lblJenisPreview.setText("Mahasiswa");
        } else if (rbUmum.isSelected()) {
            lblJenisPreview.setText("Umum");
        } else {
            lblJenisPreview.setText("Belum dipilih");
        }

        StringBuilder fasilitas = new StringBuilder();

        for (JCheckBox cb : daftarCheckboxFasilitas) {
            if (cb.isSelected()) {
                if (fasilitas.length() > 0) {
                    fasilitas.append(", ");
                }

                fasilitas.append(cb.getText());
            }
        }

        lblFasilitasPreview.setText(
                fasilitas.length() == 0
                ? "Tidak ada"
                : "<html><div style='width:220px'>"
                + fasilitas.toString()
                + "</div></html>");
    }

    // VALIDASI
    private boolean validasiInput() {

        PesertaItem peserta =
                (PesertaItem) cmbPeserta.getSelectedItem();

        if (peserta == null || peserta.getNim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Pilih peserta terlebih dahulu.",
                    "Validasi",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String kode = new String(txtKodeAkses.getPassword());

        if (!kode.equals("PBO2026")) {
            JOptionPane.showMessageDialog(this,
                    "Kode akses demo salah.",
                    "Validasi",
                    JOptionPane.WARNING_MESSAGE);
            txtKodeAkses.requestFocus();
            return false;
        }

        if (!rbMahasiswa.isSelected() && !rbUmum.isSelected()) {
            JOptionPane.showMessageDialog(this,
                    "Pilih jenis peserta.",
                    "Validasi",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (cmbKursus.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Pilih program kursus.",
                    "Validasi",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    // SIMPAN PENDAFTARAN
    private void prosesPendaftaran() {

        if (!validasiInput()) {
            return;
        }

        PesertaItem peserta =
                (PesertaItem) cmbPeserta.getSelectedItem();

        String kursus = cmbKursus.getSelectedItem().toString();

        List<String> fasilitasDipilih =
                new ArrayList<String>();

        // Ambil semua checkbox yang dicentang
        for (JCheckBox cb : daftarCheckboxFasilitas) {
            if (cb.isSelected()) {
                fasilitasDipilih.add(cb.getText());
            }
        }

        // DEBUG: tampilkan fasilitas yang dipilih di Output NetBeans
        System.out.println("================================");
        System.out.println("DEBUG PENDAFTARAN KURSUS");
        System.out.println("Peserta : " + peserta.toString());
        System.out.println("Kursus  : " + kursus);
        System.out.println("FASILITAS TERPILIH:");

        for (String fasilitas : fasilitasDipilih) {
            System.out.println("- " + fasilitas);
        }

        System.out.println("TOTAL FASILITAS: "
                + fasilitasDipilih.size());
        System.out.println("================================");

        int jawab = JOptionPane.showConfirmDialog(
                this,
                "Simpan transaksi pendaftaran ini?\n\n"
                + "Peserta: " + peserta.toString()
                + "\nKursus: " + kursus
                + "\nJumlah fasilitas: " + fasilitasDipilih.size(),
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

        if (jawab != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            dao.daftarKursus(
                    peserta.getNim(),
                    kursus,
                    fasilitasDipilih);

            StringBuilder hasil = new StringBuilder();

            hasil.append("PENDAFTARAN BERHASIL\n");
            hasil.append("============================\n");
            hasil.append("Peserta : ")
                    .append(peserta.toString()).append("\n");
            hasil.append("Kursus  : ").append(kursus).append("\n");
            hasil.append("Jenis   : ")
                    .append(rbMahasiswa.isSelected()
                            ? "Mahasiswa" : "Umum")
                    .append("\n\n");

            hasil.append("FASILITAS\n");

            if (fasilitasDipilih.isEmpty()) {
                hasil.append("- Tidak ada fasilitas\n");
            } else {
                for (String fasilitas : fasilitasDipilih) {
                    hasil.append("- ").append(fasilitas).append("\n");
                }
            }

            hasil.append("\nStatus: Tersimpan ke database.");

            txtRingkasan.setText(hasil.toString());
            txtRingkasan.setCaretPosition(0);

            lblStatus.setText("  BERHASIL DISIMPAN  ");
            lblStatus.setBackground(new Color(225, 245, 233));
            lblStatus.setForeground(new Color(30, 130, 75));

            JOptionPane.showMessageDialog(this,
                    "Pendaftaran berhasil disimpan!",
                    "Transaksi Berhasil",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            tampilkanError("Gagal menyimpan pendaftaran.", e);
        }
    }

    // RESET
    private void resetForm() {

        int jawab = JOptionPane.showConfirmDialog(
                this,
                "Kosongkan seluruh input pendaftaran?",
                "Konfirmasi Reset",
                JOptionPane.YES_NO_OPTION);

        if (jawab != JOptionPane.YES_OPTION) {
            return;
        }

        if (cmbPeserta.getItemCount() > 0) {
            cmbPeserta.setSelectedIndex(0);
        }

        txtKodeAkses.setText("");
        groupJenis.clearSelection();

        if (cmbKursus.getItemCount() > 0) {
            cmbKursus.setSelectedIndex(0);
        }

        for (JCheckBox cb : daftarCheckboxFasilitas) {
            cb.setSelected(false);
        }

        txtRingkasan.setText(
                "Belum ada transaksi yang disimpan.");

        lblStatus.setText("  MENUNGGU PENDAFTARAN  ");
        lblStatus.setBackground(new Color(255, 244, 230));
        lblStatus.setForeground(new Color(180, 105, 20));

        updatePreview();
    }

    // ERROR DATABASE
    private void tampilkanError(String pesan, SQLException e) {

        e.printStackTrace();

        JOptionPane.showMessageDialog(
                this,
                pesan + "\n\nDetail: " + e.getMessage(),
                "Kesalahan Database",
                JOptionPane.ERROR_MESSAGE);
    }

    // MAIN
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                for (UIManager.LookAndFeelInfo info
                        : UIManager.getInstalledLookAndFeels()) {

                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            new FormPendaftaranKursus().setVisible(true);
        });
    }
}
