package com.infoman.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private StringProperty firstName;
    private StringProperty middleName;
    private StringProperty lastName;
    private StringProperty address;
    private StringProperty phoneNumber;
    private StringProperty email;
    private StringProperty gender;

    public Student(String firstName, String middleName, String lastName, String address, String phoneNumber, String email, String gender) {
        this.firstName = new SimpleStringProperty(firstName);
        this.middleName = new SimpleStringProperty(middleName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.gender = new SimpleStringProperty(gender);
    }

    // Getters for properties
    public StringProperty firstNameProperty() { return firstName; }
    public StringProperty middleNameProperty() { return middleName; }
    public StringProperty lastNameProperty() { return lastName; }
    public StringProperty addressProperty() { return address; }
    public StringProperty phoneNumberProperty() { return phoneNumber; }
    public StringProperty emailProperty() { return email; }
    public StringProperty genderProperty() { return gender; }
}
