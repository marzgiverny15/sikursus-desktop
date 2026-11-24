
package dao;

import config.Database;
import model.Kelas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KelasDAO {

    // TAMBAH DATA
    public boolean insert(Kelas kelas) {

        String sql = "INSERT INTO kelas "
                + "(nama_kelas, id_kursus, kapasitas, jadwal) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kelas.getNamaKelas());
            ps.setInt(2, kelas.getIdKursus());
            ps.setInt(3, kelas.getKapasitas());
            ps.setString(4, kelas.getJadwal());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // AMBIL SEMUA DATA
    public List<Kelas> findAll() {

        List<Kelas> daftar = new ArrayList<>();

        String sql = "SELECT * FROM kelas ORDER BY id_kelas DESC";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                daftar.add(mapResult(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daftar;
    }

    // CARI BERDASARKAN ID
    public Kelas findById(int id) {

        String sql = "SELECT * FROM kelas WHERE id_kelas = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResult(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // UBAH DATA
    public boolean update(Kelas kelas) {

        String sql = "UPDATE kelas SET "
                + "nama_kelas = ?, id_kursus = ?, "
                + "kapasitas = ?, jadwal = ? "
                + "WHERE id_kelas = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kelas.getNamaKelas());
            ps.setInt(2, kelas.getIdKursus());
            ps.setInt(3, kelas.getKapasitas());
            ps.setString(4, kelas.getJadwal());
            ps.setInt(5, kelas.getIdKelas());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // HAPUS DATA
    public boolean delete(int id) {

        String sql = "DELETE FROM kelas WHERE id_kelas = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // PENCARIAN
    public List<Kelas> search(String keyword) {

        List<Kelas> daftar = new ArrayList<>();

        String sql = "SELECT * FROM kelas "
                + "WHERE nama_kelas LIKE ? OR jadwal LIKE ? "
                + "ORDER BY id_kelas DESC";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String cari = "%" + keyword + "%";

            ps.setString(1, cari);
            ps.setString(2, cari);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    daftar.add(mapResult(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daftar;
    }

    // MAPPING RESULT
    private Kelas mapResult(ResultSet rs) throws SQLException {

        return new Kelas(
                rs.getInt("id_kelas"),
                rs.getString("nama_kelas"),
                rs.getInt("id_kursus"),
                rs.getInt("kapasitas"),
                rs.getString("jadwal")
        );
    }
}
