package com.example.bdsqltester.scenes.guru;

import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.datasources.MainDataSource;
import com.example.bdsqltester.dtos.TableInputGrade;
import com.example.bdsqltester.dtos.User;
import com.example.bdsqltester.dtos.TableViewGrade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class InputNilaiController {

    @FXML
    private ComboBox<String> mapelComboBox;
    @FXML
    private ComboBox<String> kelasComboBox;
    @FXML
    private Label guruNameLabel;
    @FXML
    private TableView<TableInputGrade> nilaiTable;
    @FXML
    private TableColumn<TableViewGrade,Integer> noColumn;
    @FXML
    private TableColumn<TableViewGrade,String> namaSiswaColumn;
    @FXML
    private TableColumn<TableViewGrade,String> nisColumn;
    @FXML
    private TableColumn<TableViewGrade,Double> utsColumn;
    @FXML
    private TableColumn<TableViewGrade,Double> uasColumn;
    @FXML
    private TableColumn<TableViewGrade,String> catatanColumn;

    private User user = new User();
    private final Map<String, String> studentNameToIdMap = new HashMap<>();

    public void setUser(User user) {
        this.user = user;
        initializeComboBox();
        guruNameLabel.setText(user.username);
    }

    @FXML
    void initialize() {
        noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
        namaSiswaColumn.setCellValueFactory(new PropertyValueFactory<>("namaSiswa"));
        nisColumn.setCellValueFactory(new PropertyValueFactory<>("nis"));
        utsColumn.setCellValueFactory(new PropertyValueFactory<>("uts"));
        uasColumn.setCellValueFactory(new PropertyValueFactory<>("uas"));
        catatanColumn.setCellValueFactory(new PropertyValueFactory<>("catatan"));
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
    void onTampilkanSiswaClicked(ActionEvent actionEvent) {
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
    void onKembaliClicked(ActionEvent event) {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            app.getPrimaryStage().setTitle("Guru View");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("guru-view.fxml"));
            Parent root = loader.load();
            GuruViewController guruController = loader.getController();
            guruController.setUser(user);
            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onSimpanSemuaNilaiClicked(ActionEvent actionEvent) {
    }
}