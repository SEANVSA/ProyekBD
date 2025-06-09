package com.example.bdsqltester.scenes.siswa;

import com.example.bdsqltester.datasources.MainDataSource;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SiswaBiodataController {
    Connection data = MainDataSource.getConnection();

    private String username;
    private String id;

    @FXML
    private Label namaLengkap;
    @FXML
    private Label welcomeNama;
    @FXML
    private Label tempatTanggalLahir;
    @FXML
    private Label nomorIndukSiswa;
    @FXML
    private Label jenisKelamin;
    @FXML
    private Label agama;
    @FXML
    private Label nomorTelepon;
    @FXML
    private Label alamat;
    @FXML
    private Label namaOrangTua;
    @FXML
    private Label pekerjaanOrangTua;
    @FXML
    private Label nomorTelponOrangTua;
    @FXML
    private Label golDar;
    @FXML
    private Label emailOrangTua;
    @FXML
    private Label alamatOrangTua;
    @FXML
    private Label statusOrangTua;

    public SiswaBiodataController() throws SQLException {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
        updateBioPribadi();
    }

    @FXML
    void initialize(){
        updateBioPribadi();
    }
    void updateBioPribadi(){
        try {
            PreparedStatement stmt = data.prepareStatement("SELECT * FROM students WHERE nrp = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                namaLengkap.setText(namaLengkap.getText()+" "+username);
                welcomeNama.setText("Welcome "+username+"!");
                tempatTanggalLahir.setText(tempatTanggalLahir.getText()+" "+rs.getString("place_of_birth")+", "+rs.getString("date_of_birth"));
                jenisKelamin.setText(jenisKelamin.getText()+" "+rs.getString("gender"));
            }
        } catch (SQLException e) {
            System.out.println("Error updateBioPribadiSql");
        }
    }
}
