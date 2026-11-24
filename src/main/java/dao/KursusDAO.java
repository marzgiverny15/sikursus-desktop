
package dao;

import config.Database;
import model.Kursus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KursusDAO {

    // INSERT DATA KURSUS
    public boolean insert(Kursus k) throws SQLException {

        String sql = "INSERT INTO kursus (nama_kursus, deskripsi) "
                   + "VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, k.getNamaKursus());
            ps.setString(2, k.getDeskripsi());

            return ps.executeUpdate() == 1;
        }
    }

    // READ SEMUA DATA KURSUS
    public List<Kursus> findAll() throws SQLException {

        List<Kursus> daftar = new ArrayList<>();

        String sql = "SELECT * FROM kursus ORDER BY id_kursus DESC";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                daftar.add(mapRow(rs));
            }
        }

        return daftar;
    }

    // READ BERDASARKAN ID
    public Kursus findById(int id) throws SQLException {

        String sql = "SELECT * FROM kursus WHERE id_kursus = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    // UPDATE DATA KURSUS
    public boolean update(Kursus k) throws SQLException {

        String sql = "UPDATE kursus SET nama_kursus = ?, "
                   + "deskripsi = ? WHERE id_kursus = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, k.getNamaKursus());
            ps.setString(2, k.getDeskripsi());
            ps.setInt(3, k.getIdKursus());

            return ps.executeUpdate() == 1;
        }
    }

    // DELETE DATA KURSUS
    public boolean delete(int id) throws SQLException {

        String sql = "DELETE FROM kursus WHERE id_kursus = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() == 1;
        }
    }

    // SEARCH DATA KURSUS
    public List<Kursus> search(String keyword) throws SQLException {

        List<Kursus> daftar = new ArrayList<>();

        String sql = "SELECT * FROM kursus "
                   + "WHERE nama_kursus LIKE ? OR deskripsi LIKE ? "
                   + "ORDER BY id_kursus DESC";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String cari = "%" + keyword + "%";

            ps.setString(1, cari);
            ps.setString(2, cari);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    daftar.add(mapRow(rs));
                }
            }
        }

        return daftar;
    }

    // MAPPING RESULTSET
    private Kursus mapRow(ResultSet rs) throws SQLException {

        return new Kursus(
            rs.getInt("id_kursus"),
            rs.getString("nama_kursus"),
            rs.getString("deskripsi")
        );
    }
}
