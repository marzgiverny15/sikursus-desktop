
package model;

public class Instruktur extends Orang {

    private String jenisKelamin;
    private String email;
    private String alamat;
    private String keahlian;

    // Constructor lengkap
    public Instruktur(int id, String nama, String noHp,
            String jenisKelamin, String email,
            String alamat, String keahlian) {

        super(id, nama, noHp);

        this.jenisKelamin = jenisKelamin;
        this.email = email;
        this.alamat = alamat;
        this.keahlian = keahlian;
    }

    // Constructor untuk kompatibilitas kode lama
    public Instruktur(int id, String nama, String noHp, String keahlian) {
        this(id, nama, noHp, "Laki-laki", "", "", keahlian);
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKeahlian() {
        return keahlian;
    }

    public void setKeahlian(String keahlian) {
        this.keahlian = keahlian;
    }

    @Override
    public String getInfo() {
        return "[Instruktur] " + super.getInfo()
                + " | Jenis Kelamin: " + jenisKelamin
                + " | Email: " + email
                + " | Keahlian: " + keahlian;
    }
}
