package com.example.bdsqltester.scenes.guru;

import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.dtos.User;
import com.example.bdsqltester.scenes.siswa.SiswaViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class InputTampilkanSiswa {

    private User user = new User();

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    void onKembaliClicked() {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            app.getPrimaryStage().setTitle("Input Nilai");

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("guru-inputNilai.fxml"));
            Parent root = loader.load();

            InputNilaiController inputNilaiController = loader.getController();
            inputNilaiController.setUser(user);

            Scene scene = new Scene(root);
            app.getPrimaryStage().setScene(scene);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
