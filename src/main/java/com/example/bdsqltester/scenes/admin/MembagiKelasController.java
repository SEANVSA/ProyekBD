package com.example.bdsqltester.scenes.admin;

import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.datasources.MainDataSource;
import com.example.bdsqltester.scenes.siswa.SiswaViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MembagiKelasController {


    @FXML
    private TextField tahunAjaranField;
    @FXML
    private ChoiceBox<String> tingkatKelasChoice;
    @FXML
    private ListView<String> unassignedStudentsList;
    @FXML
    private ChoiceBox<String> kelasTujuanChoice;
    @FXML
    private ChoiceBox<String> waliKelasChoice;
    @FXML
    private ListView<String> assignedStudentsList;
    @FXML
    private Label kelasLabel;
    @FXML
    private Label jumlahSiswaLabel;
    @FXML
    private Label waliKelasLabel;
    
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    @FXML
    void initialize() {
        tahunAjaranField.setPromptText("2023/2024");

        tingkatKelasChoice.getItems().addAll();
        tingkatKelasChoice.setValue(null);

        kelasTujuanChoice.getItems().addAll();
        kelasTujuanChoice.setValue(null);

        waliKelasChoice.getItems().addAll();
        waliKelasChoice.setValue(null);

        kelasLabel.setText(null);
        jumlahSiswaLabel.setText(null);
        waliKelasLabel.setText(null);
    }



    @FXML
    void onKembaliClicked(ActionEvent actionEvent) {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            app.getPrimaryStage().setTitle("Admin View");

            // Load fxml and set the scene
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));
            Parent root = loader.load();
            AdminViewController adminController = loader.getController();
            adminController.setId(id);
            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onTampilkanDataClicked(ActionEvent actionEvent){
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            app.getPrimaryStage().setTitle("Tampilan Data");

            // Load fxml and set the scene
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin-TampilkanData.fxml"));
            Parent root = loader.load();
            TampilkanDataController tampilkanDataController = loader.getController();
            tampilkanDataController.setId(id);
            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onSimpanClicked(ActionEvent actionEvent) {
    }

    @FXML
    void onResetClicked(ActionEvent actionEvent) {
        tahunAjaranField.setPromptText("2023/2024");
        tingkatKelasChoice.setValue(null);
        kelasTujuanChoice.setValue(null);
        waliKelasChoice.setValue(null);
        kelasLabel.setText(null);
        jumlahSiswaLabel.setText(null);
        waliKelasLabel.setText(null);
    }

    @FXML
    void onTambahkanClicked(ActionEvent actionEvent) {
    }

    @FXML
    void onKembalikanClicked(ActionEvent actionEvent) {
    }

    @FXML
     void onSimpanWaliKelasClicked(ActionEvent actionEvent) {
    }
}
