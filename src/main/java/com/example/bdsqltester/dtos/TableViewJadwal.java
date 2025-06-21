package com.example.bdsqltester.dtos;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;

public class TableViewJadwal {
    private final SimpleStringProperty waktu;
    private final SimpleStringProperty senin;
    private final SimpleStringProperty selasa;
    private final SimpleStringProperty rabu;
    private final SimpleStringProperty kamis;
    private final SimpleStringProperty jumat;

    public TableViewJadwal(Timestamp waktu, String senin, String selasa, String rabu, String kamis, String jumat) {
        this.waktu = new SimpleStringProperty(waktu.toString().substring(11,19));
        this.senin = new SimpleStringProperty(senin);
        this.selasa = new SimpleStringProperty(selasa);
        this.rabu = new SimpleStringProperty(rabu);
        this.kamis = new SimpleStringProperty(kamis);
        this.jumat = new SimpleStringProperty(jumat);
    }

    public String getWaktu() {
        return waktu.get();
    }

    public SimpleStringProperty waktuProperty() {
        return waktu;
    }

    public String getSenin() {
        return senin.get();
    }

    public SimpleStringProperty seninProperty() {
        return senin;
    }

    public String getSelasa() {
        return selasa.get();
    }

    public SimpleStringProperty selasaProperty() {
        return selasa;
    }

    public String getRabu() {
        return rabu.get();
    }

    public SimpleStringProperty rabuProperty() {
        return rabu;
    }

    public String getKamis() {
        return kamis.get();
    }

    public SimpleStringProperty kamisProperty() {
        return kamis;
    }

    public String getJumat() {
        return jumat.get();
    }

    public SimpleStringProperty jumatProperty() {
        return jumat;
    }
}
