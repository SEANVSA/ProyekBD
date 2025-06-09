package com.example.bdsqltester.scenes.siswa;

import com.example.bdsqltester.datasources.GradingDataSource;
import com.example.bdsqltester.datasources.MainDataSource;
import com.example.bdsqltester.dtos.Assignment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;

public class SiswaViewController {
    @FXML
    private Label nameLabel;

    @FXML
    private Button biodataButton;

    @FXML
    private Button scheduleButton;

    @FXML
    private Button gradeButton;

    private String id;

    public void setId(String id) {
        this.id = id;
        updateNameLabel();
    }
    private void updateNameLabel() {
        if (id != null) {
            try(Connection data = MainDataSource.getConnection()){
                PreparedStatement stmt = data.prepareStatement("SELECT * FROM students WHERE nrp = ?");
                stmt.setString(1, id);
                // Execute the query
                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    nameLabel.setText(rs.getString("name"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void initialize(){
        updateNameLabel();
    }
}
