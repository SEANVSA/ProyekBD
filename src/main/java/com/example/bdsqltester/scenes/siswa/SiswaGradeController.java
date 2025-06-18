package com.example.bdsqltester.scenes.siswa;

import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.datasources.MainDataSource;
import com.example.bdsqltester.dtos.TableViewGrade;
import com.example.bdsqltester.dtos.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SiswaGradeController {

    @FXML
    private TableView<TableViewGrade> gradesTable;
    @FXML
    private TableColumn<TableViewGrade, String> mataPelajaranColumn;
    @FXML
    private TableColumn<TableViewGrade, Double> nilaiColumn;
    @FXML
    private ChoiceBox<String> semesterChoice;
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

    private User user = new User();

    public void setUser(User user) {
        this.user = user;
        update();
    }

    @FXML
    void initialize(){
        semesterChoice.getItems().addAll("1", "2");
        semesterChoice.setValue("1");

        mataPelajaranColumn.setCellValueFactory(new PropertyValueFactory<>("mapel"));
        nilaiColumn.setCellValueFactory(new PropertyValueFactory<>("nilai"));
    }

    void update(){
        ObservableList<TableViewGrade> grades = FXCollections.observableArrayList();
        try (Connection data = MainDataSource.getConnection()){
            if (user.id != null) {
                PreparedStatement stmt = data.prepareStatement("SELECT * FROM kelas WHERE id_kelas = (SELECT id_kelas FROM siswa WHERE id_siswa = ?)");
                stmt.setInt(1, Integer.parseInt(user.id));

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    studentNameLabel.setText("Nilai Akademik "+user.username);
                    classLabel.setText("Kelas : "+rs.getString("nama_kelas"));
                }


                stmt = data.prepareStatement("SELECT nama_mata_pelajaran, nilai FROM nilai_ujian nilai JOIN mata_pelajaran mapel ON nilai.id_mata_pelajaran = mapel.id_mata_pelajaran WHERE id_siswa = ?");
                stmt.setInt(1, Integer.parseInt(user.id));

                rs = stmt.executeQuery();
                while (rs.next()) {
                    String mapel = rs.getString("nama_mata_pelajaran");
                    double nilai = rs.getDouble("nilai");
                    grades.add(new TableViewGrade(mapel,nilai));
                }

                gradesTable.setItems(grades);
                System.out.println(gradesTable);
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
            siswaViewController.setUser(user);
            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
