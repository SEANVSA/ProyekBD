package com.example.bdsqltester.scenes.guru;

import com.example.bdsqltester.datasources.*;
import com.example.bdsqltester.dtos.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class GuruViewController {
    @FXML
    private Label nameLabel;

    @FXML
    private Button biodataButton;

    @FXML
    private Button scheduleButton;

    @FXML
    private Button gradeButton;

    private User user = new User();

    public void setUser(User user) {
        this.user = user;
        updateNameLabel();
    }
    private void updateNameLabel() {
        if (user.id != null) {
            try(Connection data = MainDataSource.getConnection()){
                PreparedStatement stmt = data.prepareStatement("SELECT * FROM teachers WHERE nrp = ?");
                stmt.setString(1, user.id);
                // Execute the query
                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    user.username = rs.getString("name");
                    nameLabel.setText(user.username);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void initialize(){

    }
}
