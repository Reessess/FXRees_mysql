package com.infoman.controller;

import com.infoman.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
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
    private ComboBox<String> gender; // Ensure type safety

    private DatabaseConnection connection;

    public void initialize() {
        connection = new DatabaseConnection();
        // Populate ComboBox with enum values
        gender.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
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

            // Execute the update
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Record saved successfully!");
            } else {
                System.out.println("Failed to save the record.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
