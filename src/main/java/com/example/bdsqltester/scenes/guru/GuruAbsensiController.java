package com.example.bdsqltester.scenes.guru;

import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.dtos.User;
import com.example.bdsqltester.scenes.walikelas.WaliKelasViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

import java.io.IOException;

public class GuruAbsensiController {
    @FXML
    private  ComboBox<String> kelasComboBox;
    @FXML
    private  ComboBox<String> mapelComboBox;
    @FXML
    private  DatePicker datePicker;
    @FXML
    private  ComboBox<String> jamMulaiComboBox;
    @FXML
    private  ListView<String> belumAbsenListView;
    @FXML
    private  ListView<String> hadirListView;
    @FXML
    private  ListView<String> izinListView;
    @FXML
    private  ListView<String> alphaListView;
    private User user = new User();

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    void onSimpanAbsensiClicker() {
    }

    @FXML 
    void onResetClicked() {
    }

    @FXML 
    void onTerapkanClicked() {
    }

    @FXML
    void onKembaliClicked() {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            if (user.role.equals("Wali Kelas")) {
                app.getPrimaryStage().setTitle("Wali Kelas View");

                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("guru-walikelasView.fxml"));
                Parent root = loader.load();

                WaliKelasViewController waliKelasController = loader.getController();
                waliKelasController.setUser(user);

                Scene scene = new Scene(root);
                app.getPrimaryStage().setScene(scene);
            } else {
                app.getPrimaryStage().setTitle("Guru View");

                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("guru-view.fxml"));
                Parent root = loader.load();

                GuruViewController guruController = loader.getController();
                guruController.setUser(user);

                Scene scene = new Scene(root);
                app.getPrimaryStage().setScene(scene);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
