package com.example.jurnalproject;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

public class Jurnal implements Serializable{
    private String judul;
    private String tahun;

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    private String pengarang;
    private String link;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Jurnal(){

    }


    @Override
    public String toString() {
        return " "+judul+"\n" +
                " "+tahun+"\n" +
                " "+pengarang+"\n" +
                " "+link;
    }
    public Jurnal(String jdl, String thn, String pgr, String lnk){
        judul = jdl;
        tahun = thn;
        pengarang = pgr;
        link = lnk;
    }


}

