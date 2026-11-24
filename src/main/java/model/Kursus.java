
package model;

public class Kursus {

    private int idKursus;
    private String namaKursus;
    private String deskripsi;

    public Kursus() {
    }

    public Kursus(int idKursus, String namaKursus, String deskripsi) {
        this.idKursus = idKursus;
        this.namaKursus = namaKursus;
        this.deskripsi = deskripsi;
    }

    public Kursus(String namaKursus, String deskripsi) {
        this.namaKursus = namaKursus;
        this.deskripsi = deskripsi;
    }

    public Kursus(String javabsc, String java_Desktop_Fundamental, String basic, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getIdKursus() {
        return idKursus;
    }

    public void setIdKursus(int idKursus) {
        this.idKursus = idKursus;
    }

    public String getNamaKursus() {
        return namaKursus;
    }

    public void setNamaKursus(String namaKursus) {
        this.namaKursus = namaKursus;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setBiaya(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double hitungBiayaSetelahDiskon(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
