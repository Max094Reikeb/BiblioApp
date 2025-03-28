package com.biblio.bibliotheque.Models;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


import java.time.LocalDate;

public class User {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty email;
    private final StringProperty password;

    public User(String fName, String lName, String email, String password, LocalDate date) {
        this.firstName = new SimpleStringProperty(this, "FirstName", fName);
        this.lastName = new SimpleStringProperty(this, "LastName", lName);
        this.email = new SimpleStringProperty(this, "Email", email);
        this.password = new SimpleStringProperty(this, "Password", password);

    }
}
