
package view;

import dao.InstrukturDAO;
import model.Instruktur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class FormDataInstruktur extends JFrame {

    private final InstrukturDAO dao = new InstrukturDAO();

    private final Color ORANGE = new Color(244, 81, 30);
    private final Color DARK = new Color(35, 45, 65);
    private final Color BG = new Color(245, 247, 251);
    private final Color MUTED = new Color(120, 130, 145);

    private JTextField txtCari;
    private JTable tabel;
    private DefaultTableModel model;
    private JLabel lblJumlah;

    public FormDataInstruktur() {
        setTitle("SIKURSUS - Data Instruktur");
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

        JLabel judul = new JLabel("Data Instruktur");
        judul.setFont(new Font("Arial", Font.BOLD, 27));
        judul.setForeground(DARK);

        JLabel deskripsi = new JLabel(
            "Kelola informasi dan data tenaga pengajar kursus."
        );
        deskripsi.setFont(new Font("Arial", Font.PLAIN, 13));
        deskripsi.setForeground(MUTED);

        judulPanel.add(judul);
        judulPanel.add(Box.createVerticalStrut(7));
        judulPanel.add(deskripsi);

        JButton btnTambah = tombol(
            "+ Tambah Instruktur", ORANGE, Color.WHITE
        );

        header.add(judulPanel, BorderLayout.WEST);
        header.add(btnTambah, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);

        // CONTENT
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

        JLabel labelJumlah = new JLabel("TOTAL INSTRUKTUR");
        labelJumlah.setFont(
            new Font("Arial", Font.BOLD, 11)
        );
        labelJumlah.setForeground(MUTED);

        lblJumlah = new JLabel("0 Instruktur");
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

        JPanel toolbar = new JPanel(
            new BorderLayout(12, 0)
        );
        toolbar.setOpaque(false);

        JLabel labelTabel = new JLabel("Daftar Instruktur");
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
                "ID", "Nama Instruktur", "Jenis Kelamin",
                "No. HP", "Email", "Keahlian"
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
        tabel.setAutoResizeMode(
            JTable.AUTO_RESIZE_OFF
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

        tabel.getColumnModel().getColumn(0)
            .setPreferredWidth(50);
        tabel.getColumnModel().getColumn(1)
            .setPreferredWidth(170);
        tabel.getColumnModel().getColumn(2)
            .setPreferredWidth(110);
        tabel.getColumnModel().getColumn(3)
            .setPreferredWidth(120);
        tabel.getColumnModel().getColumn(4)
            .setPreferredWidth(190);
        tabel.getColumnModel().getColumn(5)
            .setPreferredWidth(160);

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

    // DESAIN TOMBOL
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

    // TAMPILKAN DATA
    private void tampilData() {
        isiTabel(dao.findAll());
    }

    // ISI TABEL
    private void isiTabel(List<Instruktur> daftar) {

        model.setRowCount(0);

        for (Instruktur i : daftar) {
            model.addRow(new Object[]{
                i.getId(),
                i.getNama(),
                i.getJenisKelamin(),
                i.getNoHp(),
                i.getEmail(),
                i.getKeahlian()
            });
        }

        lblJumlah.setText(
            daftar.size() + " Instruktur"
        );
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

    // COMBO JENIS KELAMIN
    private JComboBox<String> buatJenisKelamin() {
        JComboBox<String> combo = new JComboBox<>(
            new String[]{"Laki-laki", "Perempuan"}
        );
        combo.setBackground(Color.WHITE);
        combo.setFont(
            new Font("Arial", Font.PLAIN, 13)
        );
        return combo;
    }

    // DESAIN FORM PREMIUM
    private JPanel buatForm(
        JTextField nama,
        JComboBox<String> jenisKelamin,
        JTextField noHp,
        JTextField email,
        JTextArea alamat,
        JTextField keahlian
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

        JLabel judul = new JLabel("PROFIL INSTRUKTUR");
        judul.setFont(
            new Font("Arial", Font.BOLD, 17)
        );
        judul.setForeground(Color.WHITE);

        JLabel subjudul = new JLabel(
            "Lengkapi informasi tenaga pengajar SIKURSUS"
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

        JLabel ikon = new JLabel("ID");
        ikon.setFont(
            new Font("Arial", Font.BOLD, 25)
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
        g.insets = new Insets(7, 7, 7, 7);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.weightx = 0.35;
        int y = 0;

        tambahField(form, g, y++, "NAMA LENGKAP", nama);
        tambahField(form, g, y++, "JENIS KELAMIN", jenisKelamin);
        tambahField(form, g, y++, "NOMOR TELEPON", noHp);
        tambahField(form, g, y++, "EMAIL", email);

        alamat.setFont(
            new Font("Arial", Font.PLAIN, 13)
        );
        alamat.setLineWrap(true);
        alamat.setWrapStyleWord(true);
        alamat.setBorder(
            new EmptyBorder(8, 9, 8, 9)
        );

        JScrollPane scrollAlamat = new JScrollPane(alamat);
        scrollAlamat.setPreferredSize(
            new Dimension(230, 65)
        );

        tambahField(form, g, y++, "ALAMAT", scrollAlamat);
        tambahField(form, g, y++, "KEAHLIAN", keahlian);

        // FOOTER
        JPanel footer = new JPanel(
            new FlowLayout(FlowLayout.LEFT)
        );
        footer.setOpaque(false);

        JLabel catatan = new JLabel(
            "Pastikan informasi instruktur sudah benar."
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

    // LABEL DAN INPUT FORM
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

        komponen.setFont(
            new Font("Arial", Font.PLAIN, 13)
        );

        if (komponen instanceof JComboBox) {
            ((JComboBox<?>) komponen).setBackground(
                Color.WHITE
            );
            ((JComboBox<?>) komponen).setPreferredSize(
                new Dimension(230, 35)
            );
        }

        panel.add(komponen, g);
    }

    // TAMBAH DATA
    private void tambahData() {

        JTextField nama = buatTextField();
        JComboBox<String> jk = buatJenisKelamin();
        JTextField noHp = buatTextField();
        JTextField email = buatTextField();

        JTextArea alamat = new JTextArea();
        alamat.setLineWrap(true);
        alamat.setWrapStyleWord(true);

        JTextField keahlian = buatTextField();

        JPanel panel = buatForm(
            nama, jk, noHp, email, alamat, keahlian
        );

        panel.setPreferredSize(
            new Dimension(490, 430)
        );

        int hasil = JOptionPane.showConfirmDialog(
            this,
            panel,
            "SIKURSUS | Tambah Instruktur",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (hasil != JOptionPane.OK_OPTION) {
            return;
        }

        if (nama.getText().trim().isEmpty()) {
            tampilPeringatan(
                "Nama instruktur wajib diisi."
            );
            return;
        }

        Instruktur i = new Instruktur(
            0,
            nama.getText().trim(),
            noHp.getText().trim(),
            (String) jk.getSelectedItem(),
            email.getText().trim(),
            alamat.getText().trim(),
            keahlian.getText().trim()
        );

        if (dao.insert(i)) {
            JOptionPane.showMessageDialog(
                this,
                "Data instruktur berhasil ditambahkan.",
                "Berhasil",
                JOptionPane.INFORMATION_MESSAGE
            );
            tampilData();
        } else {
            tampilError(
                "Data instruktur gagal ditambahkan."
            );
        }
    }

    // UBAH DATA
    private void ubahData() {

        int row = tabel.getSelectedRow();

        if (row == -1) {
            tampilPeringatan(
                "Pilih instruktur yang ingin diubah."
            );
            return;
        }

        int id = Integer.parseInt(
            model.getValueAt(row, 0).toString()
        );

        Instruktur lama = dao.findById(id);

        if (lama == null) {
            tampilError(
                "Data instruktur tidak ditemukan."
            );
            return;
        }

        JTextField nama = buatTextField();
        nama.setText(lama.getNama());

        JComboBox<String> jk = buatJenisKelamin();
        jk.setSelectedItem(lama.getJenisKelamin());

        JTextField noHp = buatTextField();
        noHp.setText(lama.getNoHp());

        JTextField email = buatTextField();
        email.setText(lama.getEmail());

        JTextArea alamat = new JTextArea(
            lama.getAlamat(), 3, 20
        );
        alamat.setLineWrap(true);
        alamat.setWrapStyleWord(true);

        JTextField keahlian = buatTextField();
        keahlian.setText(lama.getKeahlian());

        JPanel panel = buatForm(
            nama, jk, noHp, email, alamat, keahlian
        );

        panel.setPreferredSize(
            new Dimension(490, 430)
        );

        int hasil = JOptionPane.showConfirmDialog(
            this,
            panel,
            "SIKURSUS | Ubah Instruktur",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (hasil != JOptionPane.OK_OPTION) {
            return;
        }

        if (nama.getText().trim().isEmpty()) {
            tampilPeringatan(
                "Nama instruktur wajib diisi."
            );
            return;
        }

        Instruktur i = new Instruktur(
            id,
            nama.getText().trim(),
            noHp.getText().trim(),
            (String) jk.getSelectedItem(),
            email.getText().trim(),
            alamat.getText().trim(),
            keahlian.getText().trim()
        );

        if (dao.update(i)) {
            JOptionPane.showMessageDialog(
                this,
                "Data instruktur berhasil diperbarui."
            );
            tampilData();
        } else {
            tampilError(
                "Data instruktur gagal diperbarui."
            );
        }
    }

    // HAPUS DATA
    private void hapusData() {

        int row = tabel.getSelectedRow();

        if (row == -1) {
            tampilPeringatan(
                "Pilih instruktur yang ingin dihapus."
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
            "Apakah kamu yakin ingin menghapus data:\n"
                + nama + "?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {

            if (dao.delete(id)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Data instruktur berhasil dihapus."
                );
                tampilData();
            } else {
                tampilError(
                    "Data instruktur gagal dihapus. "
                    + "Periksa apakah instruktur masih digunakan."
                );
            }
        }
    }

    // PENCARIAN
    private void cariData() {
        List<Instruktur> daftar = dao.search(
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
            new FormDataInstruktur().setVisible(true);
        });
    }
}
