package com.example.bdsqltester.scenes.siswa;

import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.datasources.MainDataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SiswaGradeController {

    @FXML
    private ChoiceBox<String> semesterChoice;
    @FXML
    private TableView<String> gradesTable;
    @FXML
    private Label studentNameLabel;
    @FXML
    private Label classLabel;
    @FXML
    private Label averageGradeLabel;
    @FXML
    private Label predicateLabel;
    @FXML
    private Label classRankLabel;

    private String id;
    private String username;

    public void setId(String id) {
        this.id = id;
        update();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    void initialize(){
        semesterChoice.getItems().addAll("1", "2");
        semesterChoice.setValue("1");
    }

    void update(){
        try (Connection data = MainDataSource.getConnection()){
            if (id != null) {
                PreparedStatement stmt = data.prepareStatement("SELECT * FROM kelas WHERE id_kelas = (SELECT id_kelas FROM siswa WHERE id_siswa = ?)");
                stmt.setInt(1, Integer.parseInt(id));

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    studentNameLabel.setText("Nilai Akademik "+username);
                    classLabel.setText("Kelas : "+rs.getString("nama_kelas"));
                }

                stmt = data.prepareStatement("SELECT nama_mata_pelajaran, nilai FROM nilai_ujian nilai JOIN mata_pelajaran mapel ON nilai.id_mata_pelajaran = mapel.id_mata_pelajaran WHERE id_siswa = ?");
                stmt.setInt(1, Integer.parseInt(id));

                rs = stmt.executeQuery();
                if (rs.next()) {

                }
            }
        }catch (SQLException e){
            System.out.println("Error updateNameLabelSQL");
        }
    }

    @FXML
    void onCetakLaporanClicked(ActionEvent actionEvent) {
    }

    @FXML
    void onKembaliClicked(ActionEvent actionEvent) {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            app.getPrimaryStage().setTitle("Siswa View");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("siswa-view.fxml"));
            Parent root = loader.load();
            SiswaViewController siswaViewController = loader.getController();
            siswaViewController.setId(id);
            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
