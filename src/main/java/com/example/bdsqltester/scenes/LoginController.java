package com.example.bdsqltester.scenes;

import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.datasources.MainDataSource;
import com.example.bdsqltester.scenes.admin.AdminController;
import com.example.bdsqltester.scenes.guru.GuruController;
import com.example.bdsqltester.scenes.siswa.SiswaViewController;
import com.example.bdsqltester.scenes.walikelas.WaliKelasController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

public class LoginController {
    Connection data = MainDataSource.getConnection();

    @FXML
    private TextField passwordField;

    @FXML
    private ChoiceBox<String> selectRole;

    @FXML
    private TextField idField;

    String ref_id = "";

    public LoginController() throws SQLException {
    }

    boolean verifyCredentials(String username, String password, String role) throws SQLException {
        // Call the database to verify the credentials
        // This is insecure as this stores the password in plain text.
        // In a real application, you should hash the password and store it securely.

        // Get a connection to the database
        try {
            // Create a prepared statement to prevent SQL injection
            PreparedStatement stmt = data.prepareStatement("SELECT * FROM users WHERE login_id = ? AND role = ?");
            stmt.setString(1, username);
            stmt.setString(2, role.toLowerCase());

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // User found, check the password
                String dbPassword = rs.getString("password");

                if (dbPassword.equals(password)) {
                    ref_id = rs.getString("ref_id");
                    return true; // Credentials are valid
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // If we reach here, the credentials are invalid
        return false;
    }

    @FXML
    void initialize() {
        selectRole.getItems().addAll("Admin", "Siswa", "Guru", "Wali Kelas");
        selectRole.setValue("Siswa");
    }

    @FXML
    void onLoginClick(ActionEvent event) {
        // Get the username and password from the text fields
        String id = idField.getText();
        String password = passwordField.getText();
        String role = selectRole.getValue();

        // Verify the credentials
        try {
            if (verifyCredentials(id, password, role)) {
                HelloApplication app = HelloApplication.getApplicationInstance();
                // Load the correct view based on the role
                if (role.equals("Admin")) {
                    // Load the admin view
                    app.getPrimaryStage().setTitle("Admin View");

                    // Load fxml and set the scene
                    FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));
                    Parent root = loader.load();
                    AdminController adminController = loader.getController();
                    Scene scene = new Scene(root);
                    app.getPrimaryStage().setScene(scene);
                } else if (role.equals("Siswa")){
                    // Load the user view
                    app.getPrimaryStage().setTitle("Siswa View");

                    FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("siswa-view.fxml"));
                    Parent root = loader.load();
                    SiswaViewController siswaViewController = loader.getController();
                    siswaViewController.setId(ref_id);
                    Scene scene = new Scene(root);
                    app.getPrimaryStage().setScene(scene);
                } else if (role.equals("Guru")){
                    // Load the user view
                    app.getPrimaryStage().setTitle("Guru View");

                    FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("siswa-view.fxml"));
                    Parent root = loader.load();
                    GuruController guruController = loader.getController();
                    guruController.setId(ref_id);
                    Scene scene = new Scene(root);
                    app.getPrimaryStage().setScene(scene);
                } else if (role.equals("Wali Kelas")){
                    // Load the user view
                    app.getPrimaryStage().setTitle("Wali Kelas View");

                    FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("siswa-view.fxml"));
                    Parent root = loader.load();
                    WaliKelasController waliKelasController = loader.getController();
                    waliKelasController.setId(ref_id);
                    Scene scene = new Scene(root);
                    app.getPrimaryStage().setScene(scene);
                }
            } else {
                // Show an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText("Invalid Credentials");
                alert.setContentText("Please check your username and password.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Database Connection Failed");
            alert.setContentText("Could not connect to the database. Please try again later.");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
