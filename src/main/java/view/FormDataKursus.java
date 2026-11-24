
package view;

import dao.KursusDAO;
import model.Kursus;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class FormDataKursus extends JFrame {

    private final KursusDAO dao = new KursusDAO();

    private final Color ORANGE = new Color(244, 81, 30);
    private final Color DARK = new Color(35, 45, 65);
    private final Color BG = new Color(245, 247, 251);

    private JTextField txtCari;
    private JTable tabel;
    private DefaultTableModel model;
    private JLabel lblJumlah;

    public FormDataKursus() {
        setTitle("SIKURSUS - Data Kursus");
        setSize(1050, 650);
        setMinimumSize(new Dimension(850, 550));
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
        judulPanel.setLayout(new BoxLayout(judulPanel, BoxLayout.Y_AXIS));

        JLabel judul = new JLabel("Data Kursus");
        judul.setFont(new Font("Arial", Font.BOLD, 27));
        judul.setForeground(DARK);

        JLabel deskripsi = new JLabel(
            "Kelola program dan informasi kursus yang tersedia."
        );
        deskripsi.setFont(new Font("Arial", Font.PLAIN, 13));
        deskripsi.setForeground(new Color(120, 130, 145));

        judulPanel.add(judul);
        judulPanel.add(Box.createVerticalStrut(7));
        judulPanel.add(deskripsi);

        JButton btnTambah = tombol("+ Tambah Kursus", ORANGE, Color.WHITE);
        btnTambah.addActionListener(e -> tambahData());

        header.add(judulPanel, BorderLayout.WEST);
        header.add(btnTambah, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);

        // CONTENT
        JPanel center = new JPanel(new BorderLayout(0, 18));
        center.setOpaque(false);

        // SUMMARY CARD
        JPanel summary = new JPanel(new BorderLayout());
        summary.setBackground(Color.WHITE);
        summary.setBorder(new EmptyBorder(18, 22, 18, 22));

        JPanel summaryText = new JPanel();
        summaryText.setOpaque(false);
        summaryText.setLayout(new BoxLayout(summaryText, BoxLayout.Y_AXIS));

        JLabel labelJumlah = new JLabel("TOTAL PROGRAM KURSUS");
        labelJumlah.setFont(new Font("Arial", Font.BOLD, 11));
        labelJumlah.setForeground(new Color(130, 140, 155));

        lblJumlah = new JLabel("0 Kursus");
        lblJumlah.setFont(new Font("Arial", Font.BOLD, 25));
        lblJumlah.setForeground(ORANGE);

        summaryText.add(labelJumlah);
        summaryText.add(Box.createVerticalStrut(7));
        summaryText.add(lblJumlah);

        summary.add(summaryText, BorderLayout.WEST);
        center.add(summary, BorderLayout.NORTH);

        // TABLE PANEL
        JPanel tablePanel = new JPanel(new BorderLayout(0, 15));
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel toolbar = new JPanel(new BorderLayout(12, 0));
        toolbar.setOpaque(false);

        JLabel labelTabel = new JLabel("Daftar Kursus");
        labelTabel.setFont(new Font("Arial", Font.BOLD, 17));
        labelTabel.setForeground(DARK);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        searchPanel.setOpaque(false);

        txtCari = new JTextField(19);
        txtCari.setFont(new Font("Arial", Font.PLAIN, 13));
        txtCari.setPreferredSize(new Dimension(190, 35));

        JButton btnCari = tombol("Cari", DARK, Color.WHITE);
        JButton btnRefresh = tombol("Muat Ulang", new Color(235, 238, 243), DARK);

        searchPanel.add(txtCari);
        searchPanel.add(btnCari);
        searchPanel.add(btnRefresh);

        toolbar.add(labelTabel, BorderLayout.WEST);
        toolbar.add(searchPanel, BorderLayout.EAST);

        tablePanel.add(toolbar, BorderLayout.NORTH);

        // TABLE
        model = new DefaultTableModel(
            new Object[]{"ID", "Nama Kursus", "Deskripsi"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabel = new JTable(model);
        tabel.setRowHeight(38);
        tabel.setFont(new Font("Arial", Font.PLAIN, 13));
        tabel.setForeground(DARK);
        tabel.setSelectionBackground(new Color(255, 235, 225));
        tabel.setSelectionForeground(DARK);
        tabel.setShowVerticalLines(false);
        tabel.setGridColor(new Color(235, 238, 243));
        tabel.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader th = tabel.getTableHeader();
        th.setFont(new Font("Arial", Font.BOLD, 12));
        th.setBackground(new Color(245, 247, 250));
        th.setForeground(DARK);
        th.setPreferredSize(new Dimension(0, 42));
        th.setReorderingAllowed(false);

        tabel.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabel.getColumnModel().getColumn(1).setPreferredWidth(220);
        tabel.getColumnModel().getColumn(2).setPreferredWidth(500);

        JScrollPane scroll = new JScrollPane(tabel);
        scroll.setBorder(BorderFactory.createLineBorder(
            new Color(230, 233, 238)
        ));

        tablePanel.add(scroll, BorderLayout.CENTER);

        // BUTTONS
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        JButton btnUbah = tombol("Ubah", new Color(52, 120, 210), Color.WHITE);
        JButton btnHapus = tombol("Hapus", new Color(210, 60, 60), Color.WHITE);
        JButton btnReset = tombol("Reset", new Color(235, 238, 243), DARK);

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

    private JButton tombol(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(10, 16, 10, 16));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void tampilData() {
        try {
            List<Kursus> daftar = dao.findAll();
            isiTabel(daftar);
        } catch (SQLException e) {
            tampilError(e);
        }
    }

    private void isiTabel(List<Kursus> daftar) {
        model.setRowCount(0);

        for (Kursus k : daftar) {
            model.addRow(new Object[]{
                k.getIdKursus(),
                k.getNamaKursus(),
                k.getDeskripsi()
            });
        }

        lblJumlah.setText(daftar.size() + " Kursus");
    }

    private void tambahData() {
        JTextField nama = new JTextField();
        JTextArea deskripsi = new JTextArea(4, 25);
        deskripsi.setLineWrap(true);
        deskripsi.setWrapStyleWord(true);

        JPanel panel = formDialog(nama, deskripsi);

        int hasil = JOptionPane.showConfirmDialog(
            this, panel, "Tambah Kursus",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (hasil == JOptionPane.OK_OPTION) {
            if (nama.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Nama kursus wajib diisi.");
                return;
            }

            try {
                Kursus k = new Kursus(
                    nama.getText().trim(),
                    deskripsi.getText().trim()
                );

                if (dao.insert(k)) {
                    JOptionPane.showMessageDialog(this,
                        "Kursus berhasil ditambahkan.");
                    tampilData();
                }
            } catch (SQLException e) {
                tampilError(e);
            }
        }
    }

    private void ubahData() {
        int row = tabel.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                "Pilih kursus yang ingin diubah.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String namaLama = String.valueOf(model.getValueAt(row, 1));
        String deskripsiLama = String.valueOf(model.getValueAt(row, 2));

        JTextField nama = new JTextField(namaLama);
        JTextArea deskripsi = new JTextArea(deskripsiLama, 4, 25);
        deskripsi.setLineWrap(true);
        deskripsi.setWrapStyleWord(true);

        JPanel panel = formDialog(nama, deskripsi);

        int hasil = JOptionPane.showConfirmDialog(
            this, panel, "Ubah Kursus",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (hasil == JOptionPane.OK_OPTION) {
            if (nama.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Nama kursus wajib diisi.");
                return;
            }

            try {
                Kursus k = new Kursus(
                    id,
                    nama.getText().trim(),
                    deskripsi.getText().trim()
                );

                if (dao.update(k)) {
                    JOptionPane.showMessageDialog(this,
                        "Data kursus berhasil diperbarui.");
                    tampilData();
                }
            } catch (SQLException e) {
                tampilError(e);
            }
        }
    }

    private JPanel formDialog(JTextField nama, JTextArea deskripsi) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 8));

        panel.add(new JLabel("Nama Kursus"));
        panel.add(nama);
        panel.add(new JLabel("Deskripsi"));

        JScrollPane scroll = new JScrollPane(deskripsi);
        panel.add(scroll);

        return panel;
    }

    private void hapusData() {
        int row = tabel.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                "Pilih kursus yang ingin dihapus.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String nama = String.valueOf(model.getValueAt(row, 1));

        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Hapus kursus \"" + nama + "\"?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                if (dao.delete(id)) {
                    JOptionPane.showMessageDialog(this,
                        "Kursus berhasil dihapus.");
                    tampilData();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                    "Kursus tidak dapat dihapus.\n"
                    + "Pastikan kursus tidak digunakan dalam pendaftaran.\n"
                    + e.getMessage(),
                    "Gagal Menghapus",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void cariData() {
        try {
            List<Kursus> daftar = dao.search(txtCari.getText().trim());
            isiTabel(daftar);
        } catch (SQLException e) {
            tampilError(e);
        }
    }

    private void tampilError(Exception e) {
        JOptionPane.showMessageDialog(
            this,
            e.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
