package com.example.bdsqltester.scenes.siswa;

import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.datasources.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;

public class SiswaViewController {
    Connection data = MainDataSource.getConnection();
    @FXML
    private Label nameLabel;

    @FXML
    private Button biodataButton;

    @FXML
    private Button scheduleButton;

    @FXML
    private Button gradeButton;

    private String id;
    private String username;

    public SiswaViewController() throws SQLException {
    }

    public void setId(String id) {
        this.id = id;
        updateNameLabel();
    }
    private void updateNameLabel() {
        try {
            if (id != null) {
                PreparedStatement stmt = data.prepareStatement("SELECT * FROM students WHERE nrp = ?");
                stmt.setString(1, id);
                // Execute the query
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    username = rs.getString("name");
                    nameLabel.setText(username);
                }
            }
        }catch (SQLException e){
            System.out.println("Error updateNameLabelSQL");
        }
    }
    @FXML
    void initialize(){
        updateNameLabel();
    }
    @FXML
    void onBiodataButtonClicked(){
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            // Load the user view
            app.getPrimaryStage().setTitle("Siswa Biodata");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("siswa-biodata.fxml"));
            Parent root = loader.load();
            SiswaBiodataController siswaBiodataController = loader.getController();
            siswaBiodataController.setUsername(username);
            siswaBiodataController.setId(id);
            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onScheduleButtonClicked(){
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            // Load the user view
            app.getPrimaryStage().setTitle("Siswa Schedule");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("siswa-jadwal.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onGradeButtonClicked(){
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            // Load the user view
            app.getPrimaryStage().setTitle("Siswa Grade");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("siswa-grade.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
