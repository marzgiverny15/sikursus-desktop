package view;

import config.Database;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ReportService {

    public static void tampilkanLaporanPeserta() {
        String file = "/report/Laporan_Data_Peserta.jasper";

        try (Connection conn = Database.getConnection()) {

            Map<String, Object> parameters = new HashMap<>();

            JasperPrint print = JasperFillManager.fillReport(
                ReportService.class.getResourceAsStream(file),
                parameters,
                conn
            );

            JasperViewer viewer = new JasperViewer(print, false);
            viewer.setTitle("Laporan Data Peserta SIKURSUS");
            viewer.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Gagal menampilkan laporan: " + e.getMessage(),
                "Kesalahan Laporan",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
}