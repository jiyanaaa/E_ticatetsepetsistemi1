package com.eticaretsepetsistemi.e_ticaret.Model;


import java.math.BigDecimal;




public class Urunler {
    private int urunID;
    private String urunAdi;
    private String aciklama;
    private double fiyat;
    private int stok;
    private String kategori;

    public Urunler(int urunID, String urunAdi, String aciklama, double fiyat, int stok, String kategori) {
        this.urunID = urunID;
        this.urunAdi = urunAdi;
        this.aciklama = aciklama;
        this.fiyat = fiyat;
        this.stok = stok;
        this.kategori = kategori;
    }

    public int getUrunID() {
        return urunID;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public double getFiyat() {
        return fiyat;
    }

    public int getStok() {
        return stok;
    }

    public String getKategori() {
        return kategori;
    }
}
