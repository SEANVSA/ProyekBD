package com.example.bdsqltester.dtos;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableViewGrade {
    private final SimpleStringProperty mapelName;
    private final SimpleDoubleProperty gradeValue;

    public TableViewGrade(String mapelName, double gradeValue){
        this.mapelName = new SimpleStringProperty(mapelName);
        this.gradeValue = new SimpleDoubleProperty(gradeValue);
    }

    public String getMapelName() {
        return mapelName.get();
    }
    public double getGradeValue() {
        return gradeValue.get();
    }

    public SimpleStringProperty mapelNameProperty() {
        return mapelName;
    }
    public SimpleDoubleProperty gradeValueProperty() {
        return gradeValue;
    }
}