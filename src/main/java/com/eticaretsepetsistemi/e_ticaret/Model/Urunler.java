package com.eticaretsepetsistemi.e_ticaret.Model;


import java.math.BigDecimal;


public class Urunler {


    private int urunID;

    private String urunAdi;


    private String aciklama;

    private BigDecimal fiyat;

    private int stok;

    // Boş constructor (JPA için gerekli)
    public Urunler() {
    }

    // Parametreli constructor
    public Urunler(String urunAdi, String aciklama, BigDecimal fiyat, int stok) {
        this.urunAdi = urunAdi;
        this.aciklama = aciklama;
        this.fiyat = fiyat;
        this.stok = stok;
    }

    // Getter ve Setter metodları
    public int getUrunID() {
        return urunID;
    }

    public void setUrunID(int urunID) {
        this.urunID = urunID;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public BigDecimal getFiyat() {
        return fiyat;
    }

    public void setFiyat(BigDecimal fiyat) {
        this.fiyat = fiyat;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    // toString metodu (debug ve loglama için)
    @Override
    public String toString() {
        return "Urun{" +
                "urunID=" + urunID +
                ", urunAdi='" + urunAdi + '\'' +
                ", aciklama='" + aciklama + '\'' +
                ", fiyat=" + fiyat +
                ", stok=" + stok +
                '}';
    }
}