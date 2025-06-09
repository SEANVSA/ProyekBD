package com.example.bdsqltester.scenes.admin;

import com.example.bdsqltester.datasources.*;
import com.example.bdsqltester.dtos.Assignment;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class AdminController {

    @FXML
    private TextArea answerKeyField;

    @FXML
    private ListView<Assignment> assignmentList = new ListView<>();

    @FXML
    private TextField idField;

    @FXML
    private TextArea instructionsField;

    @FXML
    private TextField nameField;

    private final ObservableList<Assignment> assignments = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        // Set idField to read-only
        idField.setEditable(false);
        idField.setMouseTransparent(true);
        idField.setFocusTraversable(false);

        // Populate the ListView with assignment names
        refreshAssignmentList();

        assignmentList.setCellFactory(param -> new ListCell<Assignment>() {
            @Override
            protected void updateItem(Assignment item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.name);
                }
            }

            // Bind the onAssignmentSelected method to the ListView
            @Override
            public void updateSelected(boolean selected) {
                super.updateSelected(selected);
                if (selected) {
                    onAssignmentSelected(getItem());
                }
            }
        });
    }

    void refreshAssignmentList() {
        // Clear the current list
        assignments.clear();

        // Re-populate the ListView with assignment names
        try (Connection c = MainDataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM assignments");

            while (rs.next()) {
                // Create a new assignment object
                assignments.addAll(new Assignment(rs));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText(e.toString());
        }

        // Set the ListView to display assignment names
        assignmentList.setItems(assignments);

        // Set currently selected to the id inside the id field
        // This is inefficient, you can optimize this.
        try {
            if (!idField.getText().isEmpty()) {
                long id = Long.parseLong(idField.getText());
                for (Assignment assignment : assignments) {
                    if (assignment.id == id) {
                        assignmentList.getSelectionModel().select(assignment);
                        break;
                    }
                }
            }
        } catch (NumberFormatException e) {
            // Ignore, idField is empty
        }
    }

    void onAssignmentSelected(Assignment assignment) {
        // Set the id field
        idField.setText(String.valueOf(assignment.id));

        // Set the name field
        nameField.setText(assignment.name);

        // Set the instructions field
        instructionsField.setText(assignment.instructions);

        // Set the answer key field
        answerKeyField.setText(assignment.answerKey);
    }

    @FXML
    void onNewAssignmentClick(ActionEvent event) {
        // Clear the contents of the id field
        idField.clear();

        // Clear the contents of all text fields
        nameField.clear();
        instructionsField.clear();
        answerKeyField.clear();
    }

    @FXML
    void onSaveClick(ActionEvent event) {
        // If id is set, update, else insert
        if (idField.getText().isEmpty()) {
            // Insert new assignment
            try (Connection c = MainDataSource.getConnection()) {
                PreparedStatement stmt = c.prepareStatement("INSERT INTO assignments (name, instructions, answer_key) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, nameField.getText());
                stmt.setString(2, instructionsField.getText());
                stmt.setString(3, answerKeyField.getText());
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // Get generated id, update idField
                    idField.setText(String.valueOf(rs.getLong(1)));
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Database Error");
                alert.setContentText(e.toString());
            }
        } else {
            // Update existing assignment
            try (Connection c = MainDataSource.getConnection()) {
                PreparedStatement stmt = c.prepareStatement("UPDATE assignments SET name = ?, instructions = ?, answer_key = ? WHERE id = ?");
                stmt.setString(1, nameField.getText());
                stmt.setString(2, instructionsField.getText());
                stmt.setString(3, answerKeyField.getText());
                stmt.setInt(4, Integer.parseInt(idField.getText()));
                stmt.executeUpdate();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Database Error");
                alert.setContentText(e.toString());
            }
        }

        // Refresh the assignment list
        refreshAssignmentList();
    }

    @FXML
    void onShowGradesClick(ActionEvent event) {
        // Make sure id is set
        if (idField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Assignment Selected");
            alert.setContentText("Please select an assignment to view grades.");
            alert.showAndWait();
            return;
        }
    }
}
