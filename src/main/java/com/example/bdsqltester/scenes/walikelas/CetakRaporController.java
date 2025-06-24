package com.example.bdsqltester.scenes.walikelas;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CetakRaporController {
    @FXML
    private Label nilaiAkhirLabel;
    @FXML
    private Label kelasLabel;
    @FXML
    private Label waliLabel;
    @FXML
    private ComboBox<String> siswaComboBox;
    @FXML
    private ComboBox<String> tahunAjaranComboBox;
    @FXML
    private ComboBox<String> semesterComboBox;
    @FXML
    private Label namaSiswaLabel;
    @FXML
    private Label judulRaporLabel;
    @FXML
    private TableView<TableViewGrade> raporTable;
    @FXML
    private TableColumn<TableViewGrade, String> mapelColumn;
    @FXML
    private TableColumn<TableViewGrade, Double> utsColumn;
    @FXML
    private TableColumn<TableViewGrade, Double> uasColumn;
    @FXML
    private TableColumn<TableViewGrade, Double> rataRataColumn;
    private User user = new User();

    public void setUser(User user) {
        this.user = user;
        initializeComboBox();
        initializeLabel();
    }

    @FXML
    void initialize(){
        mapelColumn.setCellValueFactory(new PropertyValueFactory<>("mapelName"));
        uasColumn.setCellValueFactory(new PropertyValueFactory<>("utsValue"));
        uasColumn.setCellValueFactory(new PropertyValueFactory<>("uasValue"));
        rataRataColumn.setCellValueFactory(new PropertyValueFactory<>("rataRataValue"));
    }

    void initializeLabel(){
        try (Connection data = MainDataSource.getConnection()){
            PreparedStatement stmt = data.prepareStatement("SELECT nama_kelas FROM kelas WHERE id_kelas = (SELECT id_kelas FROM wali_kelas WHERE nip_guru = ?)");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                kelasLabel.setText("Kelas: "+rs.getString("nama_kelas"));
            }
            waliLabel.setText("Wali Kelas: "+user.username);
        }catch (SQLException e){
            System.out.println("Error updateNameLabelSQL");
        }
    }

    void initializeComboBox(){
        ObservableList<String> tahunAjaranList = FXCollections.observableArrayList();
        ObservableList<String> nisList = FXCollections.observableArrayList();
        semesterComboBox.getItems().addAll("1","2");
        semesterComboBox.setValue("1");
        try (Connection data = MainDataSource.getConnection()){
            PreparedStatement stmt = data.prepareStatement("SELECT DISTINCT tahun_ajaran FROM rapor");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tahunAjaranList.add(rs.getString("tahun_ajaran"));
            }
            stmt = data.prepareStatement("SELECT DISTINCT nomor_induk_siswa FROM siswa WHERE id_kelas = (SELECT id_kelas FROM wali_kelas WHERE nip_guru = ?) ORDER BY nomor_induk_siswa");
            stmt.setString(1, user.id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                nisList.add(rs.getString("nomor_induk_siswa"));
            }
            tahunAjaranComboBox.setItems(tahunAjaranList);
            siswaComboBox.setItems(nisList);
        }catch (SQLException e){
            System.out.println("Error updateNameLabelSQL");
        }
    }

    @FXML
    void onKembaliClicked() {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            app.getPrimaryStage().setTitle("Wali Kelas View");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("guru-walikelasView.fxml"));
            Parent root = loader.load();

            WaliKelasViewController waliKelasController = loader.getController();
            waliKelasController.setUser(user);

            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onCetakPdfClicked(ActionEvent actionEvent) {

    }
}
