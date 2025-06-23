package com.example.bdsqltester.scenes.walikelas;

import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.datasources.*;
import com.example.bdsqltester.dtos.User;
import com.example.bdsqltester.scenes.guru.InputNilaiController;
import com.example.bdsqltester.scenes.guru.JadwalKelasController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;

public class WaliKelasViewController {
    @FXML
    private Label waliKelasNameLabel;

    private User user = new User();

    public void setUser(User user) {
        this.user = user;
        updateNameLabel();
    }

    private void updateNameLabel() {
        if (user.id != null) {
            try(Connection data = MainDataSource.getConnection()){
                PreparedStatement stmt = data.prepareStatement("SELECT * FROM guru WHERE nip = ?");
                stmt.setString(1, user.id);
                // Execute the query
                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    user.username = rs.getString("nama_guru");
                    waliKelasNameLabel.setText("Wali Kelas: "+user.username);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void onJadwalKelasClicked() {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            app.getPrimaryStage().setTitle("Jadwal Kelas");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("guru-jadwalKelas.fxml"));
            Parent root = loader.load();

            JadwalKelasController jadwalKelasController = loader.getController();
            jadwalKelasController.setUser(user);

            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onMasukkanNilaiClicked() {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            app.getPrimaryStage().setTitle("Input Nilai");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("guru-inputNilai.fxml"));
            Parent root = loader.load();

            InputNilaiController inputNilaiController = loader.getController();
            inputNilaiController.setUser(user);

            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onCetakRaporClicked() {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            app.getPrimaryStage().setTitle("Cetak Rapor");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("guru-walikelasCetakRapor.fxml"));
            Parent root = loader.load();

            CetakRaporController cetakRaporController = loader.getController();
            cetakRaporController.setUser(user);

            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onLogOutClicked(){
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            // Load the user view
            app.getPrimaryStage().setTitle("Login");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
