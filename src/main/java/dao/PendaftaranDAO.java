
package dao;

import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PendaftaranDAO {

    public void daftarKursus(
            String nim,
            String namaKursus,
            List<String> fasilitasDipilih) throws SQLException {

        if (fasilitasDipilih == null) {
            throw new SQLException("Daftar fasilitas tidak boleh null.");
        }

        String sqlPeserta =
                "SELECT id_peserta FROM peserta WHERE nim = ?";

        String sqlKursus =
                "SELECT id_kursus FROM kursus WHERE nama_kursus = ?";

        String sqlPendaftaran =
                "INSERT INTO pendaftaran (id_peserta, id_kursus) VALUES (?, ?)";

        String sqlFasilitas =
                "SELECT id_fasilitas FROM fasilitas WHERE nama_fasilitas = ?";

        String sqlSimpanFasilitas =
                "INSERT INTO pendaftaran_fasilitas "
                + "(id_pendaftaran, id_fasilitas) VALUES (?, ?)";

        try (Connection conn = Database.getConnection()) {

            boolean autoCommitAwal = conn.getAutoCommit();
            conn.setAutoCommit(false);

            try {
                int idPeserta;
                int idKursus;

                // Cari peserta
                try (PreparedStatement ps =
                        conn.prepareStatement(sqlPeserta)) {

                    ps.setString(1, nim);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException(
                                    "Peserta tidak ditemukan: " + nim);
                        }

                        idPeserta = rs.getInt("id_peserta");
                    }
                }

                // Cari kursus
                try (PreparedStatement ps =
                        conn.prepareStatement(sqlKursus)) {

                    ps.setString(1, namaKursus);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException(
                                    "Kursus tidak ditemukan: " + namaKursus);
                        }

                        idKursus = rs.getInt("id_kursus");
                    }
                }

                // Simpan pendaftaran
                int idPendaftaran;

                try (PreparedStatement ps = conn.prepareStatement(
                        sqlPendaftaran,
                        Statement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, idPeserta);
                    ps.setInt(2, idKursus);
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException(
                                    "ID pendaftaran gagal dibuat.");
                        }

                        idPendaftaran = rs.getInt(1);
                    }
                }

                // Simpan fasilitas
                try (PreparedStatement ps =
                        conn.prepareStatement(sqlFasilitas);
                     PreparedStatement psSimpan =
                        conn.prepareStatement(sqlSimpanFasilitas)) {

                    for (String namaFasilitas : fasilitasDipilih) {

                        ps.setString(1, namaFasilitas);

                        try (ResultSet rs = ps.executeQuery()) {

                            if (!rs.next()) {
                                throw new SQLException(
                                        "Fasilitas tidak ditemukan: "
                                        + namaFasilitas);
                            }

                            int idFasilitas =
                                    rs.getInt("id_fasilitas");

                            psSimpan.setInt(1, idPendaftaran);
                            psSimpan.setInt(2, idFasilitas);
                            psSimpan.executeUpdate();
                        }
                    }
                }

                conn.commit();

            } catch (SQLException e) {

                conn.rollback();
                throw e;

            } finally {

                conn.setAutoCommit(autoCommitAwal);
            }
        }
    }
}
