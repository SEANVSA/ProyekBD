package com.example.bdsqltester.scenes.walikelas;

import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.dtos.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class CetakRaporController {
    private User user = new User();

    public void setUser(User user) {
        this.user = user;
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

    public void onCetakPdfClicked(ActionEvent actionEvent) {

    }
}
