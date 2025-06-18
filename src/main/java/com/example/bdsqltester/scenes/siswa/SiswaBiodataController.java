package com.example.bdsqltester.scenes.siswa;

import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.datasources.MainDataSource;
import com.example.bdsqltester.dtos.User;
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

public class SiswaBiodataController {

    private User user = new User();

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
    private Label golonganDarah;
    @FXML
    private Button lihatDataOrangTua;

    public void setUser(User user) {
        this.user = user;
        updateBioPribadi();
    }

    @FXML
    void initialize(){
        updateBioPribadi();
    }
    void updateBioPribadi(){
        try (Connection data = MainDataSource.getConnection()){
            if(user.id!=null) {
                PreparedStatement stmt = data.prepareStatement("SELECT * FROM siswa WHERE id_siswa = ?");
                stmt.setInt(1, Integer.parseInt(user.id));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    namaLengkap.setText("Nama Lengkap: " + user.username);
                    tempatTanggalLahir.setText("Tempat/Tgl Lahir: " + rs.getString("tempat_tanggal_lahir_siswa"));
                    nomorIndukSiswa.setText("Nomor Induk Siswa: " + rs.getString("id_siswa"));
                    jenisKelamin.setText("Jenis Kelamin: " + rs.getString("gender_siswa"));
                    agama.setText("Agama: " + rs.getString("agama"));
                    alamat.setText("Alamat: ");
                    nomorTelepon.setText("Nomor Telepon: ");
                    golonganDarah.setText("Golongan Darah: ");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error updateBioPribadiSql");
        }
    }

    @FXML
    void onLihatDataOrangTuaClicked(ActionEvent actionEvent) {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            app.getPrimaryStage().setTitle("Data Orang Tua");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("siswa-dataoOrangTua.fxml"));
            Parent root = loader.load();
            DataOrangTuaController dataOrangTuaController = loader.load();
            dataOrangTuaController.setUser(user);
            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onKembaliClicked(ActionEvent actionEvent) {
        try {
        HelloApplication app = HelloApplication.getApplicationInstance();
        app.getPrimaryStage().setTitle("Siswa View");

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("siswa-view.fxml"));
        Parent root = loader.load();
        SiswaViewController siswaViewController = loader.getController();
        siswaViewController.setUser(user);
        Scene scene = new Scene(root);
        app.getPrimaryStage().setScene(scene);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
