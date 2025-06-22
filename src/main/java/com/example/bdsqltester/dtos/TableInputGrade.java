package com.example.bdsqltester.dtos;

import javafx.beans.property.*;

public class TableInputGrade {
    private final SimpleIntegerProperty no;
    private final SimpleStringProperty namaSiswa;
    private final SimpleStringProperty nis;
    private final SimpleDoubleProperty uts;
    private final SimpleDoubleProperty uas;
    private final SimpleStringProperty catatan;

    public TableInputGrade(int no, String namaSiswa, String nis, double uts, double uas, String catatan) {
        this.no = new SimpleIntegerProperty(no);
        this.namaSiswa = new SimpleStringProperty(namaSiswa);
        this.nis = new SimpleStringProperty(nis);
        this.uts = new SimpleDoubleProperty(uts);
        this.uas = new SimpleDoubleProperty(uas);
        this.catatan = new SimpleStringProperty(catatan);
    }

    public int getNo() {
        return no.get();
    }

    public SimpleIntegerProperty noProperty() {
        return no;
    }

    public String getNamaSiswa() {
        return namaSiswa.get();
    }

    public SimpleStringProperty namaSiswaProperty() {
        return namaSiswa;
    }

    public String getNis() {
        return nis.get();
    }

    public SimpleStringProperty nisProperty() {
        return nis;
    }

    public double getUts() {
        return uts.get();
    }

    public SimpleDoubleProperty utsProperty() {
        return uts;
    }

    public double getUas() {
        return uas.get();
    }

    public SimpleDoubleProperty uasProperty() {
        return uas;
    }

    public String getCatatan() {
        return catatan.get();
    }

    public SimpleStringProperty catatanProperty() {
        return catatan;
    }
}
