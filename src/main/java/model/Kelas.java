
package model;

public class Kelas {

    private int idKelas;
    private String namaKelas;
    private int idKursus;
    private int kapasitas;
    private String jadwal;

    public Kelas() {
    }

    public Kelas(int idKelas, String namaKelas,
            int idKursus, int kapasitas, String jadwal) {

        this.idKelas = idKelas;
        this.namaKelas = namaKelas;
        this.idKursus = idKursus;
        this.kapasitas = kapasitas;
        this.jadwal = jadwal;
    }

    public Kelas(String namaKelas,
            int idKursus, int kapasitas, String jadwal) {

        this.namaKelas = namaKelas;
        this.idKursus = idKursus;
        this.kapasitas = kapasitas;
        this.jadwal = jadwal;
    }

    public int getIdKelas() {
        return idKelas;
    }

    public void setIdKelas(int idKelas) {
        this.idKelas = idKelas;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public int getIdKursus() {
        return idKursus;
    }

    public void setIdKursus(int idKursus) {
        this.idKursus = idKursus;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getJadwal() {
        return jadwal;
    }

    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }
}
