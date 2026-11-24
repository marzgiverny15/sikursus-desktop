
package dao;

import config.Database;
import model.Instruktur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstrukturDAO {

    // SIMPAN DATA
    public boolean insert(Instruktur i) {
        String sql = "INSERT INTO instruktur "
                + "(nama_instruktur, jenis_kelamin, no_hp, email, alamat, keahlian) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection c = Database.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, i.getNama());
            p.setString(2, i.getJenisKelamin());
            p.setString(3, i.getNoHp());
            p.setString(4, i.getEmail());
            p.setString(5, i.getAlamat());
            p.setString(6, i.getKeahlian());

            return p.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // AMBIL SEMUA DATA
    public List<Instruktur> findAll() {
        List<Instruktur> data = new ArrayList<>();

        String sql = "SELECT * FROM instruktur ORDER BY id_instruktur DESC";

        try (Connection c = Database.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {

            while (r.next()) {
                data.add(mapResult(r));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    // CARI BERDASARKAN ID
    public Instruktur findById(int id) {
        String sql = "SELECT * FROM instruktur WHERE id_instruktur = ?";

        try (Connection c = Database.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, id);

            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    return mapResult(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // UPDATE DATA
    public boolean update(Instruktur i) {
        String sql = "UPDATE instruktur SET "
                + "nama_instruktur=?, jenis_kelamin=?, no_hp=?, "
                + "email=?, alamat=?, keahlian=? "
                + "WHERE id_instruktur=?";

        try (Connection c = Database.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, i.getNama());
            p.setString(2, i.getJenisKelamin());
            p.setString(3, i.getNoHp());
            p.setString(4, i.getEmail());
            p.setString(5, i.getAlamat());
            p.setString(6, i.getKeahlian());
            p.setInt(7, i.getId());

            return p.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // HAPUS DATA
    public boolean delete(int id) {
        String sql = "DELETE FROM instruktur WHERE id_instruktur=?";

        try (Connection c = Database.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, id);
            return p.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // PENCARIAN
    public List<Instruktur> search(String keyword) {
        List<Instruktur> data = new ArrayList<>();

        String sql = "SELECT * FROM instruktur "
                + "WHERE nama_instruktur LIKE ? "
                + "OR keahlian LIKE ? "
                + "OR email LIKE ? "
                + "ORDER BY id_instruktur DESC";

        try (Connection c = Database.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            String cari = "%" + keyword + "%";

            p.setString(1, cari);
            p.setString(2, cari);
            p.setString(3, cari);

            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    data.add(mapResult(r));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    // MAPPING DATABASE KE MODEL
    private Instruktur mapResult(ResultSet r) throws SQLException {
        return new Instruktur(
                r.getInt("id_instruktur"),
                r.getString("nama_instruktur"),
                r.getString("no_hp"),
                r.getString("jenis_kelamin"),
                r.getString("email"),
                r.getString("alamat"),
                r.getString("keahlian")
        );
    }
}
