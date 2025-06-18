package com.example.bdsqltester.scenes.admin;

import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.datasources.MainDataSource;
import com.example.bdsqltester.scenes.siswa.SiswaViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InputJadwalKelasController {

    @FXML
    private ChoiceBox<String> kelasChoice;
    @FXML
    private ChoiceBox<String> hariChoice;

    @FXML
    private TextField jamMulai1;
    @FXML
    private TextField jamSelesai1;
    @FXML
    private TextField mapel1;
    @FXML
    private TextField guru1;

    @FXML
    private TextField jamMulai2;
    @FXML
    private TextField jamSelesai2;
    @FXML
    private TextField mapel2;
    @FXML
    private TextField guru2;

    @FXML
    private TextField jamMulai3;
    @FXML
    private TextField jamSelesai3;
    @FXML
    private TextField mapel3;
    @FXML
    private TextField guru3;

    @FXML
    private TextField jamMulai4;
    @FXML
    private TextField jamSelesai4;
    @FXML
    private TextField mapel4;
    @FXML
    private TextField guru4;

    @FXML
    private TextField jamMulai5;
    @FXML
    private TextField jamSelesai5;
    @FXML
    private TextField mapel5;
    @FXML
    private TextField guru5;

    @FXML
    private TextField jamMulai6;
    @FXML
    private TextField jamSelesai6;
    @FXML
    private TextField mapel6;
    @FXML
    private TextField guru6;

    @FXML
    private TextField jamMulai7;
    @FXML
    private TextField jamSelesai7;
    @FXML
    private TextField mapel7;
    @FXML
    private TextField guru7;

    @FXML
    private TextField jamMulai8;
    @FXML
    private TextField jamSelesai8;
    @FXML
    private TextField mapel8;
    @FXML
    private TextField guru8;

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    @FXML
    void initialize() {
        kelasChoice.getItems().addAll("I", "II", "III", "IV", "V", "VI");
        kelasChoice.setValue(null);
        
        hariChoice.getItems().addAll("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu");
        hariChoice.setValue(null);
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
    void onFilterJadwalClicked(ActionEvent actionEvent) {
    }

    @FXML
    void onSimpanClicked(ActionEvent actionEvent) {
        if (cekJadwal()){

        } else {

        }
    }

    boolean cekJadwal(){
        return true;
    }

    @FXML
    void onResetClicked(ActionEvent actionEvent) {
        kelasChoice.setValue(null);
        hariChoice.setValue(null);
        
        jamMulai1.setText(null);
        jamSelesai1.setText(null);
        mapel1.setText(null);
        guru1.setText(null);

        jamMulai2.setText(null);
        jamSelesai2.setText(null);
        mapel2.setText(null);
        guru2.setText(null);

        jamMulai3.setText(null);
        jamSelesai3.setText(null);
        mapel3.setText(null);
        guru3.setText(null);

        jamMulai4.setText(null);
        jamSelesai4.setText(null);
        mapel4.setText(null);
        guru4.setText(null);

        jamMulai5.setText(null);
        jamSelesai5.setText(null);
        mapel5.setText(null);
        guru5.setText(null);

        jamMulai6.setText(null);
        jamSelesai6.setText(null);
        mapel6.setText(null);
        guru6.setText(null);

        jamMulai7.setText(null);
        jamSelesai7.setText(null);
        mapel7.setText(null);
        guru7.setText(null);

        jamMulai8.setText(null);
        jamSelesai8.setText(null);
        mapel8.setText(null);
        guru8.setText(null);
    }
}
