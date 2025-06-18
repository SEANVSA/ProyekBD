package com.example.bdsqltester.scenes.admin;

import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.datasources.MainDataSource;
import com.example.bdsqltester.dtos.User;
import com.example.bdsqltester.scenes.siswa.SiswaViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.*;

public class TampilkanDataController {


    @FXML
    private ChoiceBox<String> tahunAjaranChoice;
    @FXML
    private ChoiceBox<String> kelasChoice;
    @FXML
    private ChoiceBox<String> semesterChoice;
    @FXML
    private TableView<String> siswaTable;
    @FXML
    private Label jumlahSiswaLabel;

    private User user = new User();

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    void initialize() {
        tahunAjaranChoice.getItems().addAll();
        tahunAjaranChoice.setValue(null);

        kelasChoice.getItems().addAll("I", "II", "III", "IV", "V", "VI");
        kelasChoice.setValue(null);

        semesterChoice.getItems().addAll("1", "2");
        semesterChoice.setValue(null);
    }

    @FXML
    void onKembaliClicked(ActionEvent actionEvent) {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();

            app.getPrimaryStage().setTitle("Pembagian Kelas");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin-membagiKelas.fxml"));
            Parent root = loader.load();
            MembagiKelasController membagiKelasController = loader.getController();
            membagiKelasController.setUser(user);
            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onTampilkanClicked(ActionEvent actionEvent) {
    }

    @FXML
    void onCetakDataClicked(ActionEvent actionEvent) {
    }

    @FXML
    void onExportExcelClicked(ActionEvent actionEvent) {
    }
}
