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

public class InputDataSiswaController {

    @FXML
    private TextField nomorIndukField;
    @FXML
    private TextField namaLengkapField;
    @FXML
    private TextField tempatLahirField;
    @FXML
    private DatePicker tanggalLahirPicker;
    @FXML
    private ChoiceBox<String> jenisKelaminChoice;
    @FXML
    private ChoiceBox<String> agamaChoice;
    @FXML
    private TextArea alamatField;
    @FXML
    private TextField nomorTeleponField;
    @FXML
    private ChoiceBox<String> golonganDarahChoice;

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    @FXML
    void initialize() {
        jenisKelaminChoice.getItems().addAll("Pria","Wanita");
        jenisKelaminChoice.setValue(null);

        agamaChoice.getItems().addAll("Islam", "Protestan", "Katolik", "Hindu", "Buddha", "Konghucu");
        agamaChoice.setValue(null);

        golonganDarahChoice.getItems().addAll("A", "B", "AB", "O");
        golonganDarahChoice.setValue(null);

    }

    @FXML
    void onResetFormClicked(ActionEvent actionEvent) {
        nomorIndukField.setText(null);
        namaLengkapField.setText(null);
        tempatLahirField.setText(null);
        tanggalLahirPicker.setValue(null);
        jenisKelaminChoice.setValue(null);
        agamaChoice.setValue(null);
        alamatField.setText(null);
        nomorTeleponField.setText(null);
        golonganDarahChoice.setValue(null);
    }

    @FXML
    void onSimpanDataClicked(ActionEvent actionEvent) {
        if (cekData()){
            System.out.println("Sukses");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Simpan Failed");
            alert.setHeaderText("Required Data not Filled");
            alert.setContentText("Please check your data");
            alert.showAndWait();
        }
    }

    boolean cekData(){
        if (nomorIndukField.getText() == null || namaLengkapField.getText() == null || tempatLahirField.getText() == null || tanggalLahirPicker.getValue() == null || jenisKelaminChoice.getValue() == null || agamaChoice.getValue() == null || alamatField.getText() == null) return false;
        //if nomor induk udah ada
        return true;
    }

    @FXML
    void onKembaliClicked(ActionEvent actionEvent) {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            app.getPrimaryStage().setTitle("Admin View");

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
    void onGenerateClicked(ActionEvent actionEvent) {
    }
}
