package com.infoman.controller;

import com.infoman.DatabaseConnection;
import com.infoman.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

    @FXML
    private TextField firstName;
    @FXML
    private TextField middleName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField address;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField email;
    @FXML
    private ComboBox<String> gender;

    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> middleNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;
    @FXML
    private TableColumn<Student, String> addressColumn;
    @FXML
    private TableColumn<Student, String> phoneNumberColumn;
    @FXML
    private TableColumn<Student, String> emailColumn;
    @FXML
    private TableColumn<Student, String> genderColumn;

    @FXML
    private Button deleteButton;

    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private DatabaseConnection connection;

    public void initialize() {
        connection = new DatabaseConnection();
        gender.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));

        // Initialize table columns
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        middleNameColumn.setCellValueFactory(cellData -> cellData.getValue().middleNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        phoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        genderColumn.setCellValueFactory(cellData -> cellData.getValue().genderProperty());

        loadStudentsFromDatabase();
    }

    @FXML
    private void save() {
        String sql = "INSERT INTO students(first_name, middle_name, last_name, address, phone_number, email, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.connection.prepareStatement(sql)) {
            stmt.setString(1, firstName.getText());
            stmt.setString(2, middleName.getText());
            stmt.setString(3, lastName.getText());
            stmt.setString(4, address.getText());
            stmt.setString(5, phoneNumber.getText());
            stmt.setString(6, email.getText());
            stmt.setString(7, gender.getValue());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Record saved successfully!");
                clearForm();
                loadStudentsFromDatabase();
            } else {
                System.out.println("Failed to save the record.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error saving student", "An error occurred while saving the student record.");
        }
    }

    @FXML
    private void deleteStudentFromTable() {
        // Get the selected student from the TableView
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            String sql = "DELETE FROM students WHERE first_name = ? AND middle_name = ? AND last_name = ? AND address = ? AND phone_number = ? AND email = ? AND gender = ?";

            try (PreparedStatement stmt = connection.connection.prepareStatement(sql)) {
                stmt.setString(1, selectedStudent.firstNameProperty().get());
                stmt.setString(2, selectedStudent.middleNameProperty().get());
                stmt.setString(3, selectedStudent.lastNameProperty().get());
                stmt.setString(4, selectedStudent.addressProperty().get());
                stmt.setString(5, selectedStudent.phoneNumberProperty().get());
                stmt.setString(6, selectedStudent.emailProperty().get());
                stmt.setString(7, selectedStudent.genderProperty().get());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Record deleted successfully!");
                    studentList.remove(selectedStudent); // Remove from TableView
                } else {
                    System.out.println("Failed to delete the record.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Database Error", "Error deleting student", "An error occurred while deleting the student record.");
            }
        } else {
            // No student is selected
            showAlert("No Selection", "No Student Selected", "Please select a student from the table.");
        }
    }

    private void clearForm() {
        firstName.clear();
        middleName.clear();
        lastName.clear();
        address.clear();
        phoneNumber.clear();
        email.clear();
        gender.getSelectionModel().clearSelection();
    }

    private void loadStudentsFromDatabase() {
        studentList.clear(); // Clear the list before reloading
        String query = "SELECT * FROM students";

        try (PreparedStatement stmt = connection.connection.prepareStatement(query)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                studentList.add(new Student(
                        resultSet.getString("first_name"),
                        resultSet.getString("middle_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        resultSet.getString("gender")
                ));
            }
            studentTable.setItems(studentList);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error loading students", "An error occurred while loading student records.");
        }
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
