package com.example.bdsqltester.dtos;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableViewGrade {
    private final SimpleLongProperty userId;
    private final SimpleLongProperty assignmentId;
    private final SimpleStringProperty assignmentName; // Assuming you'd join with assignments table
    private final SimpleDoubleProperty gradeValue;
    private final SimpleStringProperty studentName; // Assuming you'd join with students table

    public TableViewGrade(long userId, String studentName, long assignmentId, String assignmentName, double gradeValue) {
        this.userId = new SimpleLongProperty(userId);
        this.studentName = new SimpleStringProperty(studentName);
        this.assignmentId = new SimpleLongProperty(assignmentId);
        this.assignmentName = new SimpleStringProperty(assignmentName);
        this.gradeValue = new SimpleDoubleProperty(gradeValue);
    }

    // Getters for properties (e.g., for TableColumn.setCellValueFactory)
    public SimpleLongProperty userIdProperty() { return userId; }
    public SimpleStringProperty studentNameProperty() { return studentName; }
    public SimpleLongProperty assignmentIdProperty() { return assignmentId; }
    public SimpleStringProperty assignmentNameProperty() { return assignmentName; }
    public SimpleDoubleProperty gradeValueProperty() { return gradeValue; }

    // Regular getters (optional, but good practice)
    public long getUserId() { return userId.get(); }
    public String getStudentName() { return studentName.get(); }
    public long getAssignmentId() { return assignmentId.get(); }
    public String getAssignmentName() { return assignmentName.get(); }
    public double getGradeValue() { return gradeValue.get(); }
}