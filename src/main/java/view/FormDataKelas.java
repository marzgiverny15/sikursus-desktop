
package view;

import dao.KelasDAO;
import dao.KursusDAO;
import model.Kelas;
import model.Kursus;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class FormDataKelas extends JFrame {

    private final KelasDAO kelasDAO = new KelasDAO();
    private final KursusDAO kursusDAO = new KursusDAO();

    private final Color ORANGE = new Color(244, 81, 30);
    private final Color DARK = new Color(35, 45, 65);
    private final Color BG = new Color(245, 247, 251);
    private final Color MUTED = new Color(120, 130, 145);

    private JTextField txtCari;
    private JTable tabel;
    private DefaultTableModel model;
    private JLabel lblJumlah;

    public FormDataKelas() {

        setTitle("SIKURSUS - Data Kelas");
        setSize(1150, 700);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        tampilData();
    }

    private void initComponents() {

        JPanel root = new JPanel(new BorderLayout(0, 20));
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(25, 30, 25, 30));

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel judulPanel = new JPanel();
        judulPanel.setOpaque(false);
        judulPanel.setLayout(
            new BoxLayout(judulPanel, BoxLayout.Y_AXIS)
        );

        JLabel judul = new JLabel("Data Kelas");
        judul.setFont(new Font("Arial", Font.BOLD, 27));
        judul.setForeground(DARK);

        JLabel deskripsi = new JLabel(
            "Kelola kelas, program kursus, kapasitas, dan jadwal."
        );
        deskripsi.setFont(new Font("Arial", Font.PLAIN, 13));
        deskripsi.setForeground(MUTED);

        judulPanel.add(judul);
        judulPanel.add(Box.createVerticalStrut(7));
        judulPanel.add(deskripsi);

        JButton btnTambah = tombol(
            "+ Tambah Kelas", ORANGE, Color.WHITE
        );

        header.add(judulPanel, BorderLayout.WEST);
        header.add(btnTambah, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);

        // CENTER
        JPanel center = new JPanel(new BorderLayout(0, 18));
        center.setOpaque(false);

        // SUMMARY
        JPanel summary = new JPanel(new BorderLayout());
        summary.setBackground(Color.WHITE);
        summary.setBorder(
            new EmptyBorder(18, 22, 18, 22)
        );

        JPanel summaryText = new JPanel();
        summaryText.setOpaque(false);
        summaryText.setLayout(
            new BoxLayout(summaryText, BoxLayout.Y_AXIS)
        );

        JLabel labelJumlah = new JLabel("TOTAL KELAS");
        labelJumlah.setFont(
            new Font("Arial", Font.BOLD, 11)
        );
        labelJumlah.setForeground(MUTED);

        lblJumlah = new JLabel("0 Kelas");
        lblJumlah.setFont(
            new Font("Arial", Font.BOLD, 25)
        );
        lblJumlah.setForeground(ORANGE);

        summaryText.add(labelJumlah);
        summaryText.add(Box.createVerticalStrut(7));
        summaryText.add(lblJumlah);

        summary.add(summaryText, BorderLayout.WEST);
        center.add(summary, BorderLayout.NORTH);

        // TABLE PANEL
        JPanel tablePanel = new JPanel(
            new BorderLayout(0, 15)
        );
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(
            new EmptyBorder(20, 20, 20, 20)
        );

        // TOOLBAR
        JPanel toolbar = new JPanel(
            new BorderLayout(12, 0)
        );
        toolbar.setOpaque(false);

        JLabel labelTabel = new JLabel("Daftar Kelas");
        labelTabel.setFont(
            new Font("Arial", Font.BOLD, 17)
        );
        labelTabel.setForeground(DARK);

        JPanel searchPanel = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 8, 0)
        );
        searchPanel.setOpaque(false);

        txtCari = new JTextField(18);
        txtCari.setFont(
            new Font("Arial", Font.PLAIN, 13)
        );
        txtCari.setPreferredSize(
            new Dimension(185, 35)
        );

        JButton btnCari = tombol(
            "Cari", DARK, Color.WHITE
        );

        JButton btnRefresh = tombol(
            "Muat Ulang",
            new Color(235, 238, 243),
            DARK
        );

        searchPanel.add(txtCari);
        searchPanel.add(btnCari);
        searchPanel.add(btnRefresh);

        toolbar.add(labelTabel, BorderLayout.WEST);
        toolbar.add(searchPanel, BorderLayout.EAST);

        tablePanel.add(toolbar, BorderLayout.NORTH);

        // TABLE
        model = new DefaultTableModel(
            new Object[]{
                "ID", "Nama Kelas", "Program Kursus",
                "Kapasitas", "Jadwal"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(
                int row, int column
            ) {
                return false;
            }
        };

        tabel = new JTable(model);
        tabel.setRowHeight(40);
        tabel.setFont(
            new Font("Arial", Font.PLAIN, 12)
        );
        tabel.setForeground(DARK);
        tabel.setSelectionBackground(
            new Color(255, 235, 225)
        );
        tabel.setSelectionForeground(DARK);
        tabel.setShowVerticalLines(false);
        tabel.setGridColor(
            new Color(235, 238, 243)
        );
        tabel.setIntercellSpacing(
            new Dimension(0, 1)
        );

        JTableHeader th = tabel.getTableHeader();
        th.setFont(
            new Font("Arial", Font.BOLD, 12)
        );
        th.setBackground(
            new Color(245, 247, 250)
        );
        th.setForeground(DARK);
        th.setPreferredSize(
            new Dimension(0, 42)
        );
        th.setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabel);
        scroll.setBorder(
            BorderFactory.createLineBorder(
                new Color(230, 233, 238)
            )
        );

        tablePanel.add(scroll, BorderLayout.CENTER);

        // ACTION BUTTONS
        JPanel actions = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 10, 0)
        );
        actions.setOpaque(false);

        JButton btnReset = tombol(
            "Reset", new Color(235, 238, 243), DARK
        );

        JButton btnUbah = tombol(
            "Ubah", new Color(52, 120, 210), Color.WHITE
        );

        JButton btnHapus = tombol(
            "Hapus", new Color(210, 60, 60), Color.WHITE
        );

        actions.add(btnReset);
        actions.add(btnUbah);
        actions.add(btnHapus);

        tablePanel.add(actions, BorderLayout.SOUTH);

        center.add(tablePanel, BorderLayout.CENTER);

        root.add(center, BorderLayout.CENTER);

        add(root);

        // EVENTS
        btnTambah.addActionListener(e -> tambahData());
        btnCari.addActionListener(e -> cariData());

        btnRefresh.addActionListener(e -> {
            txtCari.setText("");
            tampilData();
        });

        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());

        btnReset.addActionListener(e -> {
            tabel.clearSelection();
            txtCari.setText("");
            tampilData();
        });

        txtCari.addActionListener(e -> cariData());
    }

    // BUTTON
    private JButton tombol(
        String text, Color bg, Color fg
    ) {

        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setBorder(
            new EmptyBorder(10, 16, 10, 16)
        );
        b.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );

        return b;
    }

    // TEXT FIELD
    private JTextField buatTextField() {

        JTextField field = new JTextField(20);

        field.setFont(
            new Font("Arial", Font.PLAIN, 13)
        );

        field.setPreferredSize(
            new Dimension(230, 35)
        );

        field.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(220, 225, 233)
                ),
                new EmptyBorder(7, 9, 7, 9)
            )
        );

        return field;
    }

    // COMBO KURSUS
    private JComboBox<Kursus> buatComboKursus() {

        JComboBox<Kursus> combo = new JComboBox<>();

        combo.setFont(
            new Font("Arial", Font.PLAIN, 13)
        );

        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(
            new Dimension(230, 35)
        );

        try {
            List<Kursus> daftar = kursusDAO.findAll();

            for (Kursus k : daftar) {
                combo.addItem(k);
            }

        } catch (Exception e) {
            e.printStackTrace();
            tampilError(
                "Gagal memuat daftar kursus."
            );
        }

        combo.setRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus
            ) {

                super.getListCellRendererComponent(
                    list, value, index,
                    isSelected, cellHasFocus
                );

                if (value instanceof Kursus) {
                    setText(
                        ((Kursus) value).getNamaKursus()
                    );
                }

                return this;
            }
        });

        return combo;
    }

    // FORM FIELD
    private void tambahField(
        JPanel panel,
        GridBagConstraints g,
        int y,
        String label,
        Component komponen
    ) {

        g.gridx = 0;
        g.gridy = y;
        g.weightx = 0.35;

        JLabel lbl = new JLabel(label);
        lbl.setFont(
            new Font("Arial", Font.BOLD, 10)
        );
        lbl.setForeground(
            new Color(100, 110, 125)
        );

        panel.add(lbl, g);

        g.gridx = 1;
        g.weightx = 0.65;

        panel.add(komponen, g);
    }

    // FORM PREMIUM
    private JPanel buatForm(
        JTextField nama,
        JComboBox<Kursus> kursus,
        JTextField kapasitas,
        JTextField jadwal
    ) {

        JPanel utama = new JPanel(
            new BorderLayout(0, 16)
        );

        utama.setBackground(Color.WHITE);
        utama.setBorder(
            new EmptyBorder(18, 20, 18, 20)
        );

        // HEADER
        JPanel header = new JPanel(
            new BorderLayout()
        );

        header.setBackground(DARK);
        header.setBorder(
            new EmptyBorder(17, 20, 17, 20)
        );

        JPanel teks = new JPanel();
        teks.setOpaque(false);
        teks.setLayout(
            new BoxLayout(teks, BoxLayout.Y_AXIS)
        );

        JLabel judul = new JLabel("INFORMASI KELAS");
        judul.setFont(
            new Font("Arial", Font.BOLD, 17)
        );
        judul.setForeground(Color.WHITE);

        JLabel subjudul = new JLabel(
            "Lengkapi informasi kelas kursus SIKURSUS"
        );
        subjudul.setFont(
            new Font("Arial", Font.PLAIN, 11)
        );
        subjudul.setForeground(
            new Color(220, 225, 235)
        );

        teks.add(judul);
        teks.add(Box.createVerticalStrut(6));
        teks.add(subjudul);

        JLabel ikon = new JLabel("CLS");
        ikon.setFont(
            new Font("Arial", Font.BOLD, 23)
        );
        ikon.setForeground(ORANGE);

        header.add(teks, BorderLayout.CENTER);
        header.add(ikon, BorderLayout.EAST);

        // FORM
        JPanel form = new JPanel(
            new GridBagLayout()
        );
        form.setBackground(Color.WHITE);

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(9, 7, 9, 7);
        g.fill = GridBagConstraints.HORIZONTAL;

        tambahField(form, g, 0, "NAMA KELAS", nama);
        tambahField(form, g, 1, "PROGRAM KURSUS", kursus);
        tambahField(form, g, 2, "KAPASITAS PESERTA", kapasitas);
        tambahField(form, g, 3, "JADWAL KELAS", jadwal);

        // FOOTER
        JPanel footer = new JPanel(
            new FlowLayout(FlowLayout.LEFT)
        );
        footer.setOpaque(false);

        JLabel catatan = new JLabel(
            "Pastikan program kursus dan kapasitas sudah benar."
        );
        catatan.setFont(
            new Font("Arial", Font.ITALIC, 10)
        );
        catatan.setForeground(MUTED);

        footer.add(catatan);

        utama.add(header, BorderLayout.NORTH);
        utama.add(form, BorderLayout.CENTER);
        utama.add(footer, BorderLayout.SOUTH);

        return utama;
    }

    // TAMPIL DATA
    private void tampilData() {
        isiTabel(kelasDAO.findAll());
    }

    // ISI TABEL
    private void isiTabel(List<Kelas> daftar) {

        model.setRowCount(0);

        for (Kelas k : daftar) {

            String namaKursus = "-";

            try {
                Kursus kursus = kursusDAO.findById(
                    k.getIdKursus()
                );

                if (kursus != null) {
                    namaKursus = kursus.getNamaKursus();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            model.addRow(new Object[]{
                k.getIdKelas(),
                k.getNamaKelas(),
                namaKursus,
                k.getKapasitas(),
                k.getJadwal()
            });
        }

        lblJumlah.setText(
            daftar.size() + " Kelas"
        );
    }

    // TAMBAH DATA
    private void tambahData() {

        JTextField nama = buatTextField();
        JComboBox<Kursus> kursus = buatComboKursus();
        JTextField kapasitas = buatTextField();
        JTextField jadwal = buatTextField();

        if (kursus.getItemCount() == 0) {
            tampilPeringatan(
                "Belum ada data kursus. Tambahkan kursus terlebih dahulu."
            );
            return;
        }

        JPanel panel = buatForm(
            nama, kursus, kapasitas, jadwal
        );

        panel.setPreferredSize(
            new Dimension(490, 350)
        );

        int hasil = JOptionPane.showConfirmDialog(
            this,
            panel,
            "SIKURSUS | Tambah Kelas",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (hasil != JOptionPane.OK_OPTION) {
            return;
        }

        if (!validasiForm(nama, kapasitas, jadwal)) {
            return;
        }

        Kursus pilihan = (Kursus) kursus.getSelectedItem();

        if (pilihan == null) {
            tampilPeringatan(
                "Pilih program kursus terlebih dahulu."
            );
            return;
        }

        Kelas k = new Kelas(
            nama.getText().trim(),
            pilihan.getIdKursus(),
            Integer.parseInt(kapasitas.getText().trim()),
            jadwal.getText().trim()
        );

        if (kelasDAO.insert(k)) {

            JOptionPane.showMessageDialog(
                this,
                "Data kelas berhasil ditambahkan.",
                "Berhasil",
                JOptionPane.INFORMATION_MESSAGE
            );

            tampilData();

        } else {
            tampilError(
                "Data kelas gagal ditambahkan."
            );
        }
    }

    // UBAH DATA
    private void ubahData() {

        int row = tabel.getSelectedRow();

        if (row == -1) {
            tampilPeringatan(
                "Pilih kelas yang ingin diubah."
            );
            return;
        }

        int id = Integer.parseInt(
            model.getValueAt(row, 0).toString()
        );

        Kelas lama = kelasDAO.findById(id);

        if (lama == null) {
            tampilError(
                "Data kelas tidak ditemukan."
            );
            return;
        }

        JTextField nama = buatTextField();
        nama.setText(lama.getNamaKelas());

        JComboBox<Kursus> kursus = buatComboKursus();

        for (int i = 0; i < kursus.getItemCount(); i++) {

            Kursus item = kursus.getItemAt(i);

            if (item.getIdKursus() == lama.getIdKursus()) {
                kursus.setSelectedIndex(i);
                break;
            }
        }

        JTextField kapasitas = buatTextField();
        kapasitas.setText(
            String.valueOf(lama.getKapasitas())
        );

        JTextField jadwal = buatTextField();
        jadwal.setText(lama.getJadwal());

        JPanel panel = buatForm(
            nama, kursus, kapasitas, jadwal
        );

        panel.setPreferredSize(
            new Dimension(490, 350)
        );

        int hasil = JOptionPane.showConfirmDialog(
            this,
            panel,
            "SIKURSUS | Ubah Kelas",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (hasil != JOptionPane.OK_OPTION) {
            return;
        }

        if (!validasiForm(nama, kapasitas, jadwal)) {
            return;
        }

        Kursus pilihan = (Kursus) kursus.getSelectedItem();

        if (pilihan == null) {
            tampilPeringatan(
                "Pilih program kursus terlebih dahulu."
            );
            return;
        }

        Kelas k = new Kelas(
            id,
            nama.getText().trim(),
            pilihan.getIdKursus(),
            Integer.parseInt(kapasitas.getText().trim()),
            jadwal.getText().trim()
        );

        if (kelasDAO.update(k)) {

            JOptionPane.showMessageDialog(
                this,
                "Data kelas berhasil diperbarui."
            );

            tampilData();

        } else {
            tampilError(
                "Data kelas gagal diperbarui."
            );
        }
    }

    // VALIDASI
    private boolean validasiForm(
        JTextField nama,
        JTextField kapasitas,
        JTextField jadwal
    ) {

        if (nama.getText().trim().isEmpty()) {
            tampilPeringatan(
                "Nama kelas wajib diisi."
            );
            return false;
        }

        if (kapasitas.getText().trim().isEmpty()) {
            tampilPeringatan(
                "Kapasitas peserta wajib diisi."
            );
            return false;
        }

        try {

            int jumlah = Integer.parseInt(
                kapasitas.getText().trim()
            );

            if (jumlah <= 0) {
                tampilPeringatan(
                    "Kapasitas harus lebih dari 0."
                );
                return false;
            }

        } catch (NumberFormatException e) {
            tampilPeringatan(
                "Kapasitas harus berupa angka."
            );
            return false;
        }

        if (jadwal.getText().trim().isEmpty()) {
            tampilPeringatan(
                "Jadwal kelas wajib diisi."
            );
            return false;
        }

        return true;
    }

    // HAPUS DATA
    private void hapusData() {

        int row = tabel.getSelectedRow();

        if (row == -1) {
            tampilPeringatan(
                "Pilih kelas yang ingin dihapus."
            );
            return;
        }

        int id = Integer.parseInt(
            model.getValueAt(row, 0).toString()
        );

        String nama = model.getValueAt(
            row, 1
        ).toString();

        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Apakah kamu yakin ingin menghapus kelas:\n"
                + nama + "?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {

            if (kelasDAO.delete(id)) {

                JOptionPane.showMessageDialog(
                    this,
                    "Data kelas berhasil dihapus."
                );

                tampilData();

            } else {
                tampilError(
                    "Data kelas gagal dihapus. "
                    + "Periksa apakah kelas masih digunakan."
                );
            }
        }
    }

    // PENCARIAN
    private void cariData() {

        List<Kelas> daftar = kelasDAO.search(
            txtCari.getText().trim()
        );

        isiTabel(daftar);
    }

    // PESAN
    private void tampilPeringatan(String pesan) {

        JOptionPane.showMessageDialog(
            this,
            pesan,
            "Perhatian",
            JOptionPane.WARNING_MESSAGE
        );
    }

    private void tampilError(String pesan) {

        JOptionPane.showMessageDialog(
            this,
            pesan,
            "Terjadi Kesalahan",
            JOptionPane.ERROR_MESSAGE
        );
    }

    // MAIN
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new FormDataKelas().setVisible(true);
        });
    }
}
