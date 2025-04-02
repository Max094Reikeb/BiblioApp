package dev.school.app.biblioapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
// Liaison entre l'interface Fxml et les composants java
public class CreateBookController {

    @FXML
    private TextField titleField;
    
    @FXML
    private TextField authorFirstNameField;
    
    @FXML
    private TextField authorLastNameField;
    
    @FXML
    private TextField descriptionField;
    
    @FXML
    private TextField publicationYearField;
    
    @FXML
    private TextField columnField;
    
    @FXML
    private TextField rowField;
    
    @FXML
    private TextField imageUrlField;
    
    @FXML
    private CheckBox borrowedCheckBox;
    
    @FXML
    private Button submitButton;

    @FXML
    public void initialize() {
        submitButton.setOnAction(event -> handleAddBook());
    }

    private void handleAddBook() {
        String title = titleField.getText();
        String firstName = authorFirstNameField.getText();
        String lastName = authorLastNameField.getText();
        String description = descriptionField.getText();
        String publicationYear = publicationYearField.getText();
        String column = columnField.getText();
        String row = rowField.getText();
        String imageUrl = imageUrlField.getText();
        boolean isBorrowed = borrowedCheckBox.isSelected();

        // Affichage d'une boîte de dialogue
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Livre ajouté");
        alert.setHeaderText(null);
        alert.setContentText("Livre ajouté : " + title + "\nAuteur : " + firstName + " " + lastName +
                "\nAnnée : " + publicationYear + "\nDescription : " + description + 
                "\nEmprunté : " + (isBorrowed ? "Oui" : "Non"));
        alert.showAndWait();
    }
}
