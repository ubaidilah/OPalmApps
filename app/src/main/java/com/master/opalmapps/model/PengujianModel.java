package com.master.opalmapps.model;

public class PengujianModel {

    String pengujianId;
    String beratTbs;
    String kadarM;
    String klasifikasi;
    String waktuUji;
    String tanggalUji;

    public PengujianModel() {
    }

    public PengujianModel(String pengujianId, String beratTbs, String kadarM, String klasifikasi, String waktuUji, String tanggalUji) {
        this.pengujianId = pengujianId;
        this.beratTbs = beratTbs;
        this.kadarM = kadarM;
        this.klasifikasi = klasifikasi;
        this.waktuUji = waktuUji;
        this.tanggalUji = tanggalUji;
    }

    public String getPengujianId() {
        return pengujianId;
    }

    public String getBeratTbs() {
        return beratTbs;
    }

    public String getKadarM() {
        return kadarM;
    }

    public String getKlasifikasi() {
        return klasifikasi;
    }

    public String getWaktuUji() {
        return waktuUji;
    }

    public String getTanggalUji() {
        return tanggalUji;
    }
}
