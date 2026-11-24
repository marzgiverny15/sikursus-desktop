
package dao;

import config.Database;
import model.Peserta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PesertaDAO {

    // =====================================
    // INSERT DATA PESERTA
    // =====================================

    public boolean insert(Peserta p) throws SQLException {

        String sql = "INSERT INTO peserta "
                + "(nim, nama, jenis_kelamin, prodi, no_hp, email) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNim());
            ps.setString(2, p.getNama());
            ps.setString(3, p.getJenisKelamin());
            ps.setString(4, p.getProdi());
            ps.setString(5, p.getNoHp());
            ps.setString(6, p.getEmail());

            return ps.executeUpdate() == 1;
        }
    }

    // =====================================
    // READ SEMUA DATA PESERTA
    // =====================================

    public List<Peserta> findAll() throws SQLException {

        List<Peserta> daftar = new ArrayList<>();

        String sql = "SELECT * FROM peserta "
                + "ORDER BY id_peserta DESC";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                daftar.add(mapRow(rs));
            }
        }

        return daftar;
    }

    // =====================================
    // READ BERDASARKAN NIM
    // =====================================

    public Peserta findByNim(String nim) throws SQLException {

        String sql = "SELECT * FROM peserta WHERE nim = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nim);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    // =====================================
    // UPDATE DATA PESERTA
    // =====================================

    public boolean update(Peserta p) throws SQLException {

        String sql = "UPDATE peserta SET "
                + "nim=?, nama=?, jenis_kelamin=?, "
                + "prodi=?, no_hp=?, email=? "
                + "WHERE id_peserta=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNim());
            ps.setString(2, p.getNama());
            ps.setString(3, p.getJenisKelamin());
            ps.setString(4, p.getProdi());
            ps.setString(5, p.getNoHp());
            ps.setString(6, p.getEmail());
            ps.setInt(7, p.getId());

            return ps.executeUpdate() == 1;
        }
    }

    // =====================================
    // DELETE DATA PESERTA
    // =====================================

    public boolean delete(int id) throws SQLException {

        String sqlFasilitas =
                "DELETE FROM pendaftaran_fasilitas "
                + "WHERE id_pendaftaran IN "
                + "(SELECT id_pendaftaran FROM pendaftaran "
                + "WHERE id_peserta=?)";

        String sqlPendaftaran =
                "DELETE FROM pendaftaran WHERE id_peserta=?";

        String sqlPeserta =
                "DELETE FROM peserta WHERE id_peserta=?";

        try (Connection conn = Database.getConnection()) {

            conn.setAutoCommit(false);

            try {

                // Hapus fasilitas pendaftaran terlebih dahulu
                try (PreparedStatement ps =
                        conn.prepareStatement(sqlFasilitas)) {

                    ps.setInt(1, id);
                    ps.executeUpdate();
                }

                // Hapus riwayat pendaftaran
                try (PreparedStatement ps =
                        conn.prepareStatement(sqlPendaftaran)) {

                    ps.setInt(1, id);
                    ps.executeUpdate();
                }

                // Hapus data peserta
                int hasil;

                try (PreparedStatement ps =
                        conn.prepareStatement(sqlPeserta)) {

                    ps.setInt(1, id);
                    hasil = ps.executeUpdate();
                }

                conn.commit();

                return hasil == 1;

            } catch (SQLException ex) {

                conn.rollback();
                throw ex;

            } finally {

                conn.setAutoCommit(true);
            }
        }
    }

    // =====================================
    // SEARCH BERDASARKAN NIM ATAU NAMA
    // =====================================

    public List<Peserta> search(String keyword)
            throws SQLException {

        List<Peserta> daftar = new ArrayList<>();

        String sql = "SELECT * FROM peserta "
                + "WHERE nim LIKE ? OR nama LIKE ? "
                + "ORDER BY id_peserta DESC";

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

    // =====================================
    // MAPPING RESULTSET KE OBJEK PESERTA
    // =====================================

    private Peserta mapRow(ResultSet rs) throws SQLException {

        Peserta p = new Peserta(
                rs.getInt("id_peserta"),
                rs.getString("nama"),
                rs.getString("no_hp"),
                rs.getString("nim"),
                rs.getString("prodi")
        );

        p.setJenisKelamin(rs.getString("jenis_kelamin"));
        p.setEmail(rs.getString("email"));

        return p;
    }
}
