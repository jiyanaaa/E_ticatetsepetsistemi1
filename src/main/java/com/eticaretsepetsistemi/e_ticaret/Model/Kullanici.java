package com.eticaretsepetsistemi.e_ticaret.Model;


import java.util.List;
import java.util.Objects;

public class Kullanici {


    private int kullaniciID;


    private String ad;


    private String soyad;


    private String eposta;


    private String sifre;


    private List<Sepet> sepetItems;

    // Boş constructor (JPA için gerekli)
    public Kullanici() {
    }

    // Parametreli constructor
    public Kullanici(String ad, String soyad, String eposta, String sifre) {
        this.ad = ad;
        this.soyad = soyad;
        this.eposta = eposta;
        this.sifre = sifre;
    }

    // Getter ve Setter metodları
    public int getKullaniciID() {
        return kullaniciID;
    }

    public void setKullaniciID(int kullaniciID) {
        this.kullaniciID = kullaniciID;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public List<Sepet> getSepetItems() {
        return sepetItems;
    }

    public void setSepetItems(List<Sepet> sepetItems) {
        this.sepetItems = sepetItems;
    }

    // equals ve hashCode metodları
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kullanici kullanici = (Kullanici) o;
        return kullaniciID == kullanici.kullaniciID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kullaniciID);
    }

    // toString metodu (debug ve loglama için)
    @Override
    public String toString() {
        return "Kullanici{" +
                "kullaniciID=" + kullaniciID +
                ", ad='" + ad + '\'' +
                ", soyad='" + soyad + '\'' +
                ", eposta='" + eposta + '\'' +
                '}'; // Şifre bilgisi güvenlik nedeniyle toString'de gösterilmiyor
    }
}