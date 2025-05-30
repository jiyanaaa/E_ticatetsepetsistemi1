package com.eticaretsepetsistemi.e_ticaret.Model;


import com.eticaretsepetsistemi.e_ticaret.Model.Urunler;

import java.util.Objects;


public class Sepet {


    private int sepetID;


    private Kullanici kullanici;


    private Urunler urun;

    private int miktar;

    // Boş constructor (JPA için gerekli)
    public Sepet() {
    }

    // Parametreli constructor
    public Sepet(Kullanici kullanici, Urunler urun, int miktar) {
        this.kullanici = kullanici;
        this.urun = urun;
        this.miktar = miktar;
    }

    // Getter ve Setter metodları
    public int getSepetID() {
        return sepetID;
    }

    public void setSepetID(int sepetID) {
        this.sepetID = sepetID;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public Urunler getUrun() {
        return urun;
    }

    public void setUrun(Urunler urun) {
        this.urun = urun;
    }

    public int getMiktar() {
        return miktar;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }

    // equals ve hashCode metodları
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sepet sepet = (Sepet) o;
        return sepetID == sepet.sepetID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sepetID);
    }

    // toString metodu (debug ve loglama için)
    @Override
    public String toString() {
        return "Sepet{" +
                "sepetID=" + sepetID +
                ", kullaniciID=" + (kullanici != null ? kullanici.getKullaniciID() : "null") +
                ", urunID=" + (urun != null ? urun.getUrunID() : "null") +
                ", miktar=" + miktar +
                '}';
    }
}