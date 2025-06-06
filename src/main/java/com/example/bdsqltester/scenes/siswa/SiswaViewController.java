package com.example.bdsqltester.scenes.siswa;

import com.example.bdsqltester.datasources.GradingDataSource;
import com.example.bdsqltester.datasources.MainDataSource;
import com.example.bdsqltester.dtos.Assignment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;

public class SiswaViewController {
    @FXML
    private Label nameLabel;

    @FXML
    private Button biodataButton;

    @FXML
    private Button scheduleButton;

    @FXML
    private Button gradeButton;

    private String username;

    public void setUsername(String name) {
        this.username = name;
        updateNameLabel();
    }
    private void updateNameLabel() {
        if (username != null) {
            nameLabel.setText(username);
        }
    }
    @FXML
    void initialize(){
        updateNameLabel();
    }
}
