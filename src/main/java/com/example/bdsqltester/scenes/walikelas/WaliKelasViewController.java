package com.example.bdsqltester.scenes.walikelas;

import com.example.bdsqltester.datasources.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class WaliKelasViewController {
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
                PreparedStatement stmt = data.prepareStatement("SELECT * FROM wali_kelas WHERE nrp = ?");
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
