package com.example.bdsqltester.scenes.guru;

import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.datasources.MainDataSource;
import com.example.bdsqltester.dtos.TableInputGrade;
import com.example.bdsqltester.dtos.User;
import com.example.bdsqltester.dtos.TableViewGrade;
import com.example.bdsqltester.scenes.walikelas.WaliKelasViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InputNilaiController {

    @FXML
    private ComboBox<String> mapelComboBox;
    @FXML
    private ComboBox<String> kelasComboBox;
    @FXML
    private Label guruNameLabel;

    private User user = new User();

    public void setUser(User user) {
        this.user = user;
        initializeComboBox();
        guruNameLabel.setText("Guru: "+user.username);
    }

    @FXML
    void initialize() {
    }

    void initializeComboBox(){
        ObservableList<String> mapelList = FXCollections.observableArrayList();
        ObservableList<String> kelasList = FXCollections.observableArrayList();
        try (Connection data = MainDataSource.getConnection()){
            PreparedStatement stmt = data.prepareStatement("SELECT DISTINCT nama_mata_pelajaran FROM mata_pelajaran");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapelList.add(rs.getString("nama_mata_pelajaran"));
            }
            stmt = data.prepareStatement("SELECT nama_kelas FROM kelas");
            rs = stmt.executeQuery();
            while (rs.next()) {
                kelasList.add(rs.getString("nama_kelas"));
            }
            mapelComboBox.setItems(mapelList);
            kelasComboBox.setItems(kelasList);
        }catch (SQLException e){
            System.out.println("Error updateNameLabelSQL");
        }
    }

    @FXML
    void onTampilkanSiswaClicked() {
        if (kelasComboBox.getValue() != null) {
            try {
                HelloApplication app = HelloApplication.getApplicationInstance();
                app.getPrimaryStage().setTitle("Tampilan Siswa");

                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("guru-inputTampilkanSiswa.fxml"));
                Parent root = loader.load();

                InputTampilkanSiswa inputTampilkanSiswa = loader.getController();
                inputTampilkanSiswa.setUser(user);
                inputTampilkanSiswa.setKelas(kelasComboBox.getValue());

                Scene scene = new Scene(root);
                app.getPrimaryStage().setScene(scene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Kelas not Set");
            alert.setContentText("Please set Kelas first");
            alert.showAndWait();
        }
    }

    @FXML
    void onKembaliClicked() {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            if (user.role.equals("Wali Kelas")) {
                app.getPrimaryStage().setTitle("Wali Kelas View");

                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("guru-walikelasView.fxml"));
                Parent root = loader.load();

                WaliKelasViewController waliKelasController = loader.getController();
                waliKelasController.setUser(user);

                Scene scene = new Scene(root);
                app.getPrimaryStage().setScene(scene);
            }else {
                app.getPrimaryStage().setTitle("Guru View");

                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("guru-view.fxml"));
                Parent root = loader.load();

                GuruViewController guruController = loader.getController();
                guruController.setUser(user);

                Scene scene = new Scene(root);
                app.getPrimaryStage().setScene(scene);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onSimpanNilaiClicked() {
    }
}