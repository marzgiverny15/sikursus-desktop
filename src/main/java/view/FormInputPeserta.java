
package view;

import config.Database;
import dao.PendaftaranDAO;
import dao.PesertaDAO;
import model.Peserta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormInputPeserta extends JFrame {

    private final Color ORANGE = new Color(244, 81, 30);
    private final Color DARK = new Color(35, 45, 65);
    private final Color BG = new Color(245, 247, 251);
    private final Color MUTED = new Color(120, 130, 145);

    private JTextField txtNim, txtNama, txtProdi, txtNoHp, txtEmail;
    private JTextField txtCari;
    private JPasswordField txtKodeAkses;

    private JRadioButton rbLaki, rbPerempuan;
    private ButtonGroup grupGender;

    private JComboBox<String> cbKursus;
    private JCheckBox cbModul, cbSertifikat, cbRekaman;

    private JTable tabelPeserta, tabelPendaftaran;
    private DefaultTableModel modelTabel, modelPendaftaran;

    private JButton btnSimpan, btnUpdate, btnReset;
    private JButton btnHapus, btnDaftarKursus, btnCari, btnRefresh;

    private JLabel lblJumlah;
    private int selectedId = -1;

    public FormInputPeserta() {
        setTitle("SIKURSUS - Manajemen Peserta");
        setSize(1200, 850);
        setMinimumSize(new Dimension(950, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buatTampilan();
        aturAksiTombol();

        muatPilihanKursus();
        muatDataPeserta();
        muatRiwayatPendaftaran();

        resetForm();
        setVisible(true);
    }

    private void buatTampilan() {

        JPanel utama = new JPanel(new BorderLayout(0, 20));
        utama.setBackground(BG);
        utama.setBorder(new EmptyBorder(25, 30, 25, 30));

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel judulPanel = new JPanel();
        judulPanel.setOpaque(false);
        judulPanel.setLayout(new BoxLayout(judulPanel, BoxLayout.Y_AXIS));

        JLabel judul = new JLabel("Manajemen Data Peserta");
        judul.setFont(new Font("Arial", Font.BOLD, 27));
        judul.setForeground(DARK);

        JLabel deskripsi = new JLabel(
            "Kelola informasi peserta dan pendaftaran program kursus."
        );
        deskripsi.setFont(new Font("Arial", Font.PLAIN, 13));
        deskripsi.setForeground(MUTED);

        judulPanel.add(judul);
        judulPanel.add(Box.createVerticalStrut(7));
        judulPanel.add(deskripsi);

        btnRefresh = tombol(
            "Muat Ulang", new Color(230, 234, 240), DARK
        );

        header.add(judulPanel, BorderLayout.WEST);
        header.add(btnRefresh, BorderLayout.EAST);

        utama.add(header, BorderLayout.NORTH);

        // CONTENT
        JPanel isi = new JPanel();
        isi.setOpaque(false);
        isi.setLayout(new BoxLayout(isi, BoxLayout.Y_AXIS));

        // SUMMARY
        JPanel summary = new JPanel(new BorderLayout());
        summary.setBackground(Color.WHITE);
        summary.setBorder(new EmptyBorder(16, 22, 16, 22));
        summary.setMaximumSize(new Dimension(Integer.MAX_VALUE, 95));
        summary.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel summaryText = new JPanel();
        summaryText.setOpaque(false);
        summaryText.setLayout(new BoxLayout(summaryText, BoxLayout.Y_AXIS));

        JLabel summaryTitle = new JLabel("TOTAL PESERTA TERDAFTAR");
        summaryTitle.setFont(new Font("Arial", Font.BOLD, 11));
        summaryTitle.setForeground(MUTED);

        lblJumlah = new JLabel("0 Peserta");
        lblJumlah.setFont(new Font("Arial", Font.BOLD, 25));
        lblJumlah.setForeground(ORANGE);

        summaryText.add(summaryTitle);
        summaryText.add(Box.createVerticalStrut(5));
        summaryText.add(lblJumlah);

        summary.add(summaryText, BorderLayout.WEST);

        isi.add(summary);
        isi.add(Box.createVerticalStrut(18));

        // FORM PESERTA
        JPanel form = new JPanel(new BorderLayout(0, 15));
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(20, 22, 20, 22));
        form.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel formTitle = new JLabel("Informasi Peserta");
        formTitle.setFont(new Font("Arial", Font.BOLD, 18));
        formTitle.setForeground(DARK);

        form.add(formTitle, BorderLayout.NORTH);

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        txtNim = field();
        txtNama = field();
        txtProdi = field();
        txtNoHp = field();
        txtEmail = field();

        txtKodeAkses = new JPasswordField(20);
        styleField(txtKodeAkses);

        rbLaki = new JRadioButton("Laki-laki");
        rbPerempuan = new JRadioButton("Perempuan");

        styleRadio(rbLaki);
        styleRadio(rbPerempuan);

        grupGender = new ButtonGroup();
        grupGender.add(rbLaki);
        grupGender.add(rbPerempuan);

        JPanel gender = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        gender.setOpaque(false);
        gender.add(rbLaki);
        gender.add(rbPerempuan);

        cbKursus = new JComboBox<String>();
        cbKursus.setFont(new Font("Arial", Font.PLAIN, 13));
        cbKursus.setPreferredSize(new Dimension(240, 35));

        tambahBaris(fields, gbc, 0, 0, "NIM", txtNim);
        tambahBaris(fields, gbc, 1, 0, "Nama Lengkap", txtNama);
        tambahBaris(fields, gbc, 2, 0, "Jenis Kelamin", gender);

        tambahBaris(fields, gbc, 0, 1, "Program Studi", txtProdi);
        tambahBaris(fields, gbc, 1, 1, "Nomor HP", txtNoHp);
        tambahBaris(fields, gbc, 2, 1, "Email", txtEmail);

        tambahBaris(fields, gbc, 0, 2, "Kode Akses", txtKodeAkses);
        tambahBaris(fields, gbc, 1, 2, "Pilih Kursus", cbKursus);

        JPanel fasilitas = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        fasilitas.setOpaque(false);

        cbModul = new JCheckBox("Modul Cetak");
        cbSertifikat = new JCheckBox("Sertifikat");
        cbRekaman = new JCheckBox("Akses Rekaman");

        styleCheck(cbModul);
        styleCheck(cbSertifikat);
        styleCheck(cbRekaman);

        fasilitas.add(cbModul);
        fasilitas.add(cbSertifikat);
        fasilitas.add(cbRekaman);

        tambahBaris(fields, gbc, 2, 2, "Fasilitas", fasilitas);

        form.add(fields, BorderLayout.CENTER);

        JPanel panelTombol = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 8, 0)
        );
        panelTombol.setOpaque(false);

        btnSimpan = tombol("Simpan Peserta", ORANGE, Color.WHITE);
        btnUpdate = tombol("Update", new Color(52, 120, 210), Color.WHITE);
        btnReset = tombol("Reset", new Color(230, 234, 240), DARK);
        btnHapus = tombol("Hapus", new Color(210, 60, 60), Color.WHITE);
        btnDaftarKursus = tombol(
            "Daftar Kursus", new Color(35, 150, 115), Color.WHITE
        );

        panelTombol.add(btnSimpan);
        panelTombol.add(btnUpdate);
        panelTombol.add(btnReset);
        panelTombol.add(btnHapus);
        panelTombol.add(btnDaftarKursus);

        form.add(panelTombol, BorderLayout.SOUTH);

        isi.add(form);
        isi.add(Box.createVerticalStrut(18));

        // TABEL PESERTA
        JPanel panelTabel = new JPanel(new BorderLayout(0, 12));
        panelTabel.setBackground(Color.WHITE);
        panelTabel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelTabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);

        JLabel tabelTitle = new JLabel("Daftar Peserta");
        tabelTitle.setFont(new Font("Arial", Font.BOLD, 17));
        tabelTitle.setForeground(DARK);

        JPanel cariPanel = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 8, 0)
        );
        cariPanel.setOpaque(false);

        txtCari = field();
        txtCari.setPreferredSize(new Dimension(190, 35));

        btnCari = tombol("Cari", DARK, Color.WHITE);

        cariPanel.add(new JLabel("Cari NIM / Nama:"));
        cariPanel.add(txtCari);
        cariPanel.add(btnCari);

        toolbar.add(tabelTitle, BorderLayout.WEST);
        toolbar.add(cariPanel, BorderLayout.EAST);

        panelTabel.add(toolbar, BorderLayout.NORTH);

        modelTabel = new DefaultTableModel(
            new String[]{
                "ID", "NIM", "Nama", "Jenis Kelamin",
                "Prodi", "No. HP", "Email"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelPeserta = new JTable(modelTabel);
        styleTable(tabelPeserta);
        tabelPeserta.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );
        tabelPeserta.setAutoCreateRowSorter(true);

        panelTabel.add(
            new JScrollPane(tabelPeserta), BorderLayout.CENTER
        );

        isi.add(panelTabel);
        isi.add(Box.createVerticalStrut(18));

        // RIWAYAT PENDAFTARAN
        JPanel panelRiwayat = new JPanel(new BorderLayout(0, 12));
        panelRiwayat.setBackground(Color.WHITE);
        panelRiwayat.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelRiwayat.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel riwayatTitle = new JLabel("Riwayat Pendaftaran Kursus");
        riwayatTitle.setFont(new Font("Arial", Font.BOLD, 17));
        riwayatTitle.setForeground(DARK);

        panelRiwayat.add(riwayatTitle, BorderLayout.NORTH);

        modelPendaftaran = new DefaultTableModel(
            new String[]{
                "NIM", "Nama Peserta", "Kursus",
                "Fasilitas", "Tanggal Daftar"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelPendaftaran = new JTable(modelPendaftaran);
        styleTable(tabelPendaftaran);
        tabelPendaftaran.setAutoCreateRowSorter(true);

        panelRiwayat.add(
            new JScrollPane(tabelPendaftaran), BorderLayout.CENTER
        );

        isi.add(panelRiwayat);

        JScrollPane scrollIsi = new JScrollPane(isi);
        scrollIsi.setBorder(null);
        scrollIsi.getVerticalScrollBar().setUnitIncrement(18);

        utama.add(scrollIsi, BorderLayout.CENTER);

        setContentPane(utama);
    }

    // STYLE
    private JTextField field() {
        JTextField f = new JTextField(20);
        styleField(f);
        return f;
    }

    private void styleField(JTextField f) {
        f.setFont(new Font("Arial", Font.PLAIN, 13));
        f.setPreferredSize(new Dimension(220, 35));
    }

    private void styleRadio(JRadioButton r) {
        r.setFont(new Font("Arial", Font.PLAIN, 13));
        r.setOpaque(false);
    }

    private void styleCheck(JCheckBox c) {
        c.setFont(new Font("Arial", Font.PLAIN, 12));
        c.setOpaque(false);
    }

    private JButton tombol(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(10, 15, 10, 15));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(36);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setForeground(DARK);
        table.setSelectionBackground(new Color(255, 235, 225));
        table.setSelectionForeground(DARK);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(235, 238, 243));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(new Color(245, 247, 250));
        header.setForeground(DARK);
        header.setPreferredSize(new Dimension(0, 40));
        header.setReorderingAllowed(false);
    }

    private void tambahBaris(
        JPanel panel, GridBagConstraints gbc,
        int kolom, int baris, String label, Component komponen
    ) {
        gbc.gridx = kolom * 2;
        gbc.gridy = baris;
        gbc.weightx = 0;

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setForeground(DARK);

        panel.add(lbl, gbc);

        gbc.gridx = kolom * 2 + 1;
        gbc.weightx = 1;

        panel.add(komponen, gbc);
    }

    // AKSI TOMBOL
    private void aturAksiTombol() {
        btnSimpan.addActionListener(e -> simpanPeserta());
        btnUpdate.addActionListener(e -> updatePeserta());
        btnReset.addActionListener(e -> resetForm());
        btnHapus.addActionListener(e -> hapusData());
        btnRefresh.addActionListener(e -> muatSemuaData());
        btnDaftarKursus.addActionListener(e -> daftarKursus());
        btnCari.addActionListener(e -> cariPeserta());

        txtCari.addActionListener(e -> cariPeserta());

        tabelPeserta.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()
                    && tabelPeserta.getSelectedRow() != -1) {
                tampilkanDataTerpilih();
            }
        });

        btnUpdate.setEnabled(false);
        btnHapus.setEnabled(false);
    }

    // MUAT ULANG
    private void muatSemuaData() {
        txtCari.setText("");
        muatPilihanKursus();
        muatDataPeserta();
        muatRiwayatPendaftaran();
        resetForm();
    }

    // KURSUS
    private void muatPilihanKursus() {
        cbKursus.removeAllItems();

        String sql =
            "SELECT nama_kursus FROM kursus ORDER BY nama_kursus";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cbKursus.addItem(rs.getString("nama_kursus"));
            }

        } catch (SQLException ex) {
            tampilkanErrorDatabase(ex);
        }
    }

    // VALIDASI
    private String jenisKelaminTerpilih() {
        if (rbLaki.isSelected()) return "L";
        if (rbPerempuan.isSelected()) return "P";
        return null;
    }

    private boolean validasiPeserta(boolean perluKodeAkses) {
        if (txtNim.getText().trim().isEmpty()
                || txtNama.getText().trim().isEmpty()
                || txtProdi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "NIM, Nama, dan Program Studi wajib diisi.");
            return false;
        }

        if (jenisKelaminTerpilih() == null) {
            JOptionPane.showMessageDialog(this,
                "Pilih jenis kelamin terlebih dahulu.");
            return false;
        }

        if (perluKodeAkses
                && txtKodeAkses.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this,
                "Kode akses wajib diisi.");
            return false;
        }

        return true;
    }

    // SIMPAN
    private void simpanPeserta() {
        if (!validasiPeserta(true)) return;

        Peserta peserta = new Peserta(
            txtNim.getText().trim(),
            txtNama.getText().trim(),
            jenisKelaminTerpilih(),
            txtProdi.getText().trim(),
            txtNoHp.getText().trim(),
            txtEmail.getText().trim()
        );

        try {
            PesertaDAO dao = new PesertaDAO();

            if (dao.insert(peserta)) {
                JOptionPane.showMessageDialog(this,
                    "Peserta berhasil disimpan.");
                muatDataPeserta();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Data peserta gagal disimpan.");
            }
        } catch (SQLException ex) {
            tampilkanErrorDatabase(ex);
        }
    }

    // UPDATE
    private void updatePeserta() {
        if (selectedId == -1) return;
        if (!validasiPeserta(false)) return;

        Peserta peserta = new Peserta(
            txtNim.getText().trim(),
            txtNama.getText().trim(),
            jenisKelaminTerpilih(),
            txtProdi.getText().trim(),
            txtNoHp.getText().trim(),
            txtEmail.getText().trim()
        );

        peserta.setId(selectedId);

        try {
            PesertaDAO dao = new PesertaDAO();

            if (dao.update(peserta)) {
                JOptionPane.showMessageDialog(this,
                    "Data peserta berhasil diperbarui.");
                muatDataPeserta();
                muatRiwayatPendaftaran();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Data peserta tidak ditemukan.");
            }
        } catch (SQLException ex) {
            tampilkanErrorDatabase(ex);
        }
    }

    // HAPUS
    private void hapusData() {
        if (selectedId == -1) return;

        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Hapus peserta " + txtNama.getText()
                + " beserta riwayat pendaftarannya?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (konfirmasi != JOptionPane.YES_OPTION) return;

        try {
            PesertaDAO dao = new PesertaDAO();

            if (dao.delete(selectedId)) {
                JOptionPane.showMessageDialog(this,
                    "Peserta berhasil dihapus.");
                muatDataPeserta();
                muatRiwayatPendaftaran();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Data peserta tidak ditemukan.");
            }
        } catch (SQLException ex) {
            tampilkanErrorDatabase(ex);
        }
    }

    // CARI
    private void cariPeserta() {
        String keyword = txtCari.getText().trim();

        try {
            PesertaDAO dao = new PesertaDAO();

            List<Peserta> daftar = keyword.isEmpty()
                ? dao.findAll()
                : dao.search(keyword);

            tampilkanDaftarPeserta(daftar);

            if (!keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Ditemukan " + daftar.size() + " data peserta.");
            }
        } catch (SQLException ex) {
            tampilkanErrorDatabase(ex);
        }
    }

    // TAMPIL DATA
    private void muatDataPeserta() {
        try {
            PesertaDAO dao = new PesertaDAO();
            tampilkanDaftarPeserta(dao.findAll());
        } catch (SQLException ex) {
            tampilkanErrorDatabase(ex);
        }
    }

    private void tampilkanDaftarPeserta(List<Peserta> daftar) {
        modelTabel.setRowCount(0);
        selectedId = -1;
        tabelPeserta.clearSelection();

        for (Peserta p : daftar) {
            modelTabel.addRow(new Object[]{
                p.getId(),
                p.getNim(),
                p.getNama(),
                p.getJenisKelamin(),
                p.getProdi(),
                p.getNoHp(),
                p.getEmail()
            });
        }

        lblJumlah.setText(daftar.size() + " Peserta");
        btnUpdate.setEnabled(false);
        btnHapus.setEnabled(false);
        btnSimpan.setEnabled(true);
    }

    private void tampilkanDataTerpilih() {
        int baris = tabelPeserta.getSelectedRow();
        if (baris == -1) return;

        int row = tabelPeserta.convertRowIndexToModel(baris);

        selectedId = Integer.parseInt(
            modelTabel.getValueAt(row, 0).toString()
        );

        txtNim.setText(nilaiKolom(row, 1));
        txtNama.setText(nilaiKolom(row, 2));

        String gender = nilaiKolom(row, 3);
        rbLaki.setSelected("L".equals(gender));
        rbPerempuan.setSelected("P".equals(gender));

        txtProdi.setText(nilaiKolom(row, 4));
        txtNoHp.setText(nilaiKolom(row, 5));
        txtEmail.setText(nilaiKolom(row, 6));

        btnSimpan.setEnabled(false);
        btnUpdate.setEnabled(true);
        btnHapus.setEnabled(true);
    }

    private String nilaiKolom(int row, int column) {
        Object nilai = modelTabel.getValueAt(row, column);
        return nilai == null ? "" : nilai.toString();
    }

    // PENDAFTARAN KURSUS
    private void daftarKursus() {
        int baris = tabelPeserta.getSelectedRow();

        if (baris == -1) {
            JOptionPane.showMessageDialog(this,
                "Pilih peserta dari tabel terlebih dahulu.");
            return;
        }

        if (cbKursus.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                "Belum ada pilihan kursus.");
            return;
        }

        int row = tabelPeserta.convertRowIndexToModel(baris);

        String nim = modelTabel.getValueAt(row, 1).toString();
        String nama = modelTabel.getValueAt(row, 2).toString();
        String kursus = cbKursus.getSelectedItem().toString();

        List<String> fasilitas = new ArrayList<String>();

        if (cbModul.isSelected()) fasilitas.add("Modul Cetak");
        if (cbSertifikat.isSelected()) fasilitas.add("Sertifikat");
        if (cbRekaman.isSelected()) fasilitas.add("Akses Rekaman");

        String ringkasan = fasilitas.isEmpty()
            ? "Tidak ada"
            : joinFasilitas(fasilitas);

        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Nama: " + nama
                + "\nNIM: " + nim
                + "\nKursus: " + kursus
                + "\nFasilitas: " + ringkasan
                + "\n\nSimpan pendaftaran?",
            "Konfirmasi Pendaftaran",
            JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi != JOptionPane.YES_OPTION) return;

        try {
            PendaftaranDAO dao = new PendaftaranDAO();
            dao.daftarKursus(nim, kursus, fasilitas);

            JOptionPane.showMessageDialog(this,
                "Pendaftaran berhasil disimpan ke MySQL.");

            cbModul.setSelected(false);
            cbSertifikat.setSelected(false);
            cbRekaman.setSelected(false);

            muatRiwayatPendaftaran();

        } catch (SQLException ex) {
            String pesan = ex.getMessage() == null
                ? "Terjadi kesalahan database."
                : ex.getMessage();

            if (pesan.contains("unik_peserta_kursus")
                    || pesan.contains("Duplicate entry")) {
                pesan = "Peserta sudah terdaftar pada kursus tersebut.";
            }

            JOptionPane.showMessageDialog(this,
                "Pendaftaran gagal: " + pesan,
                "Kesalahan",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private String joinFasilitas(List<String> fasilitas) {
        StringBuilder hasil = new StringBuilder();

        for (String f : fasilitas) {
            if (hasil.length() > 0) hasil.append(", ");
            hasil.append(f);
        }

        return hasil.toString();
    }

    // RIWAYAT PENDAFTARAN
    private void muatRiwayatPendaftaran() {
        modelPendaftaran.setRowCount(0);

        String sql =
            "SELECT ps.nim, ps.nama, k.nama_kursus, "
            + "GROUP_CONCAT(DISTINCT f.nama_fasilitas "
            + "ORDER BY f.nama_fasilitas SEPARATOR ', ') AS fasilitas, "
            + "p.tanggal_daftar "
            + "FROM pendaftaran p "
            + "JOIN peserta ps ON p.id_peserta=ps.id_peserta "
            + "JOIN kursus k ON p.id_kursus=k.id_kursus "
            + "LEFT JOIN pendaftaran_fasilitas pf "
            + "ON p.id_pendaftaran=pf.id_pendaftaran "
            + "LEFT JOIN fasilitas f ON pf.id_fasilitas=f.id_fasilitas "
            + "GROUP BY p.id_pendaftaran, ps.nim, ps.nama, "
            + "k.nama_kursus, p.tanggal_daftar "
            + "ORDER BY p.tanggal_daftar DESC";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String fasilitas = rs.getString("fasilitas");

                if (fasilitas == null || fasilitas.isEmpty()) {
                    fasilitas = "Tidak ada";
                }

                modelPendaftaran.addRow(new Object[]{
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("nama_kursus"),
                    fasilitas,
                    rs.getTimestamp("tanggal_daftar")
                });
            }

        } catch (SQLException ex) {
            tampilkanErrorDatabase(ex);
        }
    }

    // RESET
    private void resetForm() {
        txtNim.setText("");
        txtNama.setText("");
        txtProdi.setText("");
        txtNoHp.setText("");
        txtEmail.setText("");
        txtKodeAkses.setText("");

        grupGender.clearSelection();

        if (cbKursus.getItemCount() > 0) {
            cbKursus.setSelectedIndex(0);
        }

        cbModul.setSelected(false);
        cbSertifikat.setSelected(false);
        cbRekaman.setSelected(false);

        tabelPeserta.clearSelection();
        selectedId = -1;

        btnSimpan.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnHapus.setEnabled(false);

        txtNim.requestFocusInWindow();
    }

    private void tampilkanErrorDatabase(SQLException ex) {
        JOptionPane.showMessageDialog(
            this,
            "Kesalahan database:\n" + ex.getMessage(),
            "Kesalahan",
            JOptionPane.ERROR_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            new FormInputPeserta()
        );
    }
}
