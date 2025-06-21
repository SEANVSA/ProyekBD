package com.example.bdsqltester.scenes.walikelas;

import com.example.bdsqltester.datasources.*;
import com.example.bdsqltester.dtos.User;
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

    private User user = new User();

    public void setUser(User user) {
        this.user = user;
        updateNameLabel();
    }

    private void updateNameLabel() {
        if (user.id != null) {
            try(Connection data = MainDataSource.getConnection()){
                PreparedStatement stmt = data.prepareStatement("SELECT * FROM wali_kelas WHERE nip_guru = ?");
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
        updateNameLabel();
    }
}
