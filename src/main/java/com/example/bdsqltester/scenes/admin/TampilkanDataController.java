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
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TampilkanDataController {


    @FXML
    private Button inputDataSiswa;
    @FXML
    private Button inputJadwalKelas;
    @FXML
    private Button bagiKelas;
    @FXML
    private Label adminNameLabel;
    @FXML
    private Button logOut;

    private String id;
    private String username;

    public void setId(String id) {
        this.id = id;
        update();
    }

    @FXML
    void initialize() {
        update();
    }

    void update() {

    }


    @FXML
    void onKembaliClicked(ActionEvent actionEvent) {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();

            app.getPrimaryStage().setTitle("Pembagian Kelas");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin-membagiKelas.fxml"));
            Parent root = loader.load();
            MembagiKelasController membagiKelasController = loader.getController();
            membagiKelasController.setId(id);
            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
