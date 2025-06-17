package com.example.bdsqltester.scenes.admin;

import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.datasources.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;

public class AdminViewController {


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
        try(Connection data = MainDataSource.getConnection()) {
            if (id != null) {
                PreparedStatement stmt = data.prepareStatement("SELECT * FROM admins WHERE id = ?");
                stmt.setInt(1, Integer.parseInt(id));
                // Execute the query
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    username = rs.getString("name");
                    adminNameLabel.setText("Admin: " + username);
                }
            }
        } catch (SQLException e) {
            System.out.println("Update Failed");
        }
    }

    @FXML
    void onInputDataSiswaClicked(ActionEvent actionEvent) {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();

            app.getPrimaryStage().setTitle("Input Data Siswa");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin-inputDataSiswa.fxml"));
            Parent root = loader.load();
            InputDataSiswaController inputDataSiswaController = loader.getController();
            inputDataSiswaController.setId(id);
            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onInputJadwalKelasClicked(ActionEvent actionEvent) {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();

            app.getPrimaryStage().setTitle("Input Jadwal Kelas");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin-inputJadwalKelas.fxml"));
            Parent root = loader.load();
            InputJadwalKelasController inputJadwalKelasController = loader.getController();
            inputJadwalKelasController.setId(id);
            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onBagiKelasClicked(ActionEvent actionEvent) {
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

    @FXML
    void onLogOutClicked(ActionEvent actionEvent) {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();

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
