package com.example.bdsqltester.scenes.guru;

import com.example.bdsqltester.HelloApplication;
import com.example.bdsqltester.datasources.MainDataSource;
import com.example.bdsqltester.dtos.User;
import com.example.bdsqltester.scenes.walikelas.WaliKelasViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GuruAbsensiController {
    // A custom data format to ensure we are only dragging/dropping our own data.
    private static final DataFormat STUDENT_FORMAT = new DataFormat("application/x-student");

    @FXML
    private ComboBox<String> kelasComboBox;
    @FXML
    private ComboBox<String> mapelComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Time> jamMulaiComboBox;
    @FXML
    private ListView<String> belumAbsenListView;
    @FXML
    private ListView<String> hadirListView;
    @FXML
    private ListView<String> izinListView;
    @FXML
    private ListView<String> alphaListView;

    private User user = new User();

    public void setUser(User user) {
        this.user = user;
        initialize();
    }

    @FXML
    void initialize(){
        // Set default date to today
        datePicker.setValue(LocalDate.now());

        // Setup Drag and Drop for all ListViews
        setupDragAndDrop(belumAbsenListView);
        setupDragAndDrop(hadirListView);
        setupDragAndDrop(izinListView);
        setupDragAndDrop(alphaListView);

        // Initialize the ComboBoxes with data from the DB
        initializeComboBoxes();
    }

    private void initializeComboBoxes() {
        ObservableList<String> kelasList = FXCollections.observableArrayList();
        ObservableList<String> mapelList = FXCollections.observableArrayList();
        ObservableList<Time> jamList = FXCollections.observableArrayList();

        try (Connection data = MainDataSource.getConnection()) {
            // Get classes taught by this teacher
            PreparedStatement stmt = data.prepareStatement("SELECT DISTINCT k.nama_kelas FROM kelas k JOIN jadwal_kelas jk ON k.id_kelas = jk.id_kelas WHERE jk.nip_guru = ? ORDER BY k.nama_kelas");
            stmt.setString(1, user.id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("nama_kelas"));
                kelasList.add(rs.getString("nama_kelas"));
            }
            kelasComboBox.setItems(kelasList);

            // Get subjects taught by this teacher
            stmt = data.prepareStatement("SELECT DISTINCT mp.nama_mata_pelajaran FROM mata_pelajaran mp JOIN jadwal_kelas jk ON mp.id_mata_pelajaran = jk.id_mata_pelajaran WHERE jk.nip_guru = ? ORDER BY mp.nama_mata_pelajaran");
            stmt.setString(1, user.id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("nama_mata_pelajaran"));
                mapelList.add(rs.getString("nama_mata_pelajaran"));
            }
            mapelComboBox.setItems(mapelList);

            // Get schedule times
            stmt = data.prepareStatement("SELECT DISTINCT jam_jadwal_kelas FROM jadwal_kelas ORDER BY jam_jadwal_kelas");
            rs = stmt.executeQuery();
            while(rs.next()){
                jamList.add(rs.getTime("jam_jadwal_kelas"));
            }
            jamMulaiComboBox.setItems(jamList);

        } catch (SQLException e) {
            System.err.println("Error initializing ComboBoxes: " + e.getMessage());
        }
    }

    private void setupDragAndDrop(ListView<String> listView) {
        listView.setCellFactory(param -> {
            ListCell<String> cell = new ListCell<>();

            cell.setOnDragDetected(event -> {
                if (cell.getItem() == null || cell.isEmpty()) {
                    return;
                }
                Dragboard dragboard = cell.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.put(STUDENT_FORMAT, cell.getItem());
                dragboard.setContent(content);
                // Keep track of the source list
                dragboard.setDragView(cell.snapshot(null, null));
                event.consume();
            });

            cell.setOnDragOver(event -> {
                if (event.getGestureSource() != listView && event.getDragboard().hasContent(STUDENT_FORMAT)) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });

            cell.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasContent(STUDENT_FORMAT)) {
                    String draggedItem = (String) db.getContent(STUDENT_FORMAT);
                    listView.getItems().add(draggedItem);
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            });

            // This is key: after the drop is completed, the source list is notified
            cell.setOnDragDone(event -> {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    ListView<String> sourceList = (ListView<String>) event.getGestureSource();
                    String draggedItem = (String) event.getDragboard().getContent(STUDENT_FORMAT);
                    sourceList.getItems().remove(draggedItem);
                }
                event.consume();
            });


            // This is essential to actually display the text in the cell!
            cell.textProperty().bind(cell.itemProperty());

            return cell;
        });
    }

    // This button loads the students for the selected class into the "Belum Absen" list
    @FXML
    void onTerapkanClicked() {
        if (kelasComboBox.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih kelas terlebih dahulu.");
            return;
        }

        // Clear all lists first
        onResetClicked();

        ObservableList<String> students = FXCollections.observableArrayList();
        try (Connection data = MainDataSource.getConnection()) {
            PreparedStatement stmt = data.prepareStatement(
                    "SELECT nomor_induk_siswa, nama_siswa FROM siswa WHERE id_kelas = (SELECT id_kelas FROM kelas WHERE nama_kelas = ?) ORDER BY nama_siswa"
            );
            stmt.setString(1, kelasComboBox.getValue());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(String.format("(%s) - %s", rs.getString("nomor_induk_siswa"), rs.getString("nama_siswa")));
            }
            belumAbsenListView.setItems(students);

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Gagal memuat data siswa: " + e.getMessage());
        }
    }

    // This button saves the current state of the lists to the database
    @FXML
    void onSimpanAbsensiClicker() {
        if (kelasComboBox.getValue() == null || mapelComboBox.getValue() == null || datePicker.getValue() == null || jamMulaiComboBox.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Data Tidak Lengkap", "Harap isi semua pilihan (Kelas, Mapel, Tanggal, Jam) sebelum menyimpan.");
            return;
        }

        List<String> hadirList = new ArrayList<>(hadirListView.getItems());
        List<String> izinList = new ArrayList<>(izinListView.getItems());
        List<String> alphaList = new ArrayList<>(alphaListView.getItems());

        try (Connection data = MainDataSource.getConnection()) {
            // Use a transaction to ensure all or nothing is saved
            data.setAutoCommit(false);

            // Clear previous attendance for this specific class session to avoid duplicates
            PreparedStatement deleteStmt = data.prepareStatement("DELETE FROM absensi WHERE id_mata_pelajaran = (SELECT id_mata_pelajaran FROM mata_pelajaran WHERE nama_mata_pelajaran = ?) AND tanggal = ? AND jam_mulai = ? AND nomor_induk_siswa IN (SELECT nomor_induk_siswa FROM siswa WHERE id_kelas = (SELECT id_kelas FROM kelas WHERE nama_kelas = ?))");
            deleteStmt.setString(1, mapelComboBox.getValue());
            deleteStmt.setDate(2, Date.valueOf(datePicker.getValue()));
            deleteStmt.setTime(3, jamMulaiComboBox.getValue());
            deleteStmt.setString(4, kelasComboBox.getValue());
            deleteStmt.executeUpdate();


            // Insert new attendance records
            saveListToDb(data, hadirList, "Hadir");
            saveListToDb(data, izinList, "Izin");
            saveListToDb(data, alphaList, "Alpha");

            data.commit(); // Finalize the transaction
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Data absensi berhasil disimpan.");

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Gagal menyimpan absensi: " + e.getMessage());
        }
    }

    private void saveListToDb(Connection connection, List<String> studentList, String status) throws SQLException {
        String sql = "INSERT INTO absensi (nomor_induk_siswa, id_mata_pelajaran, nip_guru, tanggal, jam_mulai, status) VALUES (?, (SELECT id_mata_pelajaran FROM mata_pelajaran WHERE nama_mata_pelajaran = ?), ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (String studentInfo : studentList) {
                String nis = getNis(studentInfo);
                stmt.setString(1, nis);
                stmt.setString(2, mapelComboBox.getValue());
                stmt.setString(3, user.id);
                stmt.setDate(4, Date.valueOf(datePicker.getValue()));
                stmt.setTime(5, jamMulaiComboBox.getValue());
                stmt.setString(6, status);
                stmt.addBatch(); // Add statement to the batch
            }
            stmt.executeBatch(); // Execute all statements in the batch
        }
    }

    @FXML
    void onResetClicked() {
        belumAbsenListView.getItems().clear();
        hadirListView.getItems().clear();
        izinListView.getItems().clear();
        alphaListView.getItems().clear();
    }

    // Utility function to get NIS from the list string, e.g., "(SD001) - Nama" -> "SD001"
    private String getNis(String s) {
        if (s == null || !s.contains("(") || !s.contains(")")) {
            return "";
        }
        return s.substring(s.indexOf('(') + 1, s.indexOf(')'));
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void onKembaliClicked() {
        try {
            HelloApplication app = HelloApplication.getApplicationInstance();
            if ("Wali Kelas".equals(user.role)) {
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