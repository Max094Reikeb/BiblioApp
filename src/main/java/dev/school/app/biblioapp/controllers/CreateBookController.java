package dev.school.app.biblioapp.controllers;

import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class CreateBookController implements Initializable {

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
    private CheckBox borrowed;

    @FXML
    private Button submitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submitButton.setOnAction(event -> handleAddBook());
    }

    private void handleAddBook() {
        String title = titleField.getText().trim();
        String firstName = authorFirstNameField.getText().trim();
        String lastName = authorLastNameField.getText().trim();
        String description = descriptionField.getText().trim();
        String publicationYearStr = publicationYearField.getText().trim();
        String columnStr = columnField.getText().trim();
        String rowStr = rowField.getText().trim();
        String imageUrl = imageUrlField.getText().trim();
        boolean isBorrowed = borrowed.isSelected();

        // Required fields check
        if (title.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champs manquants", "Veuillez remplir au minimum le titre et le nom de l'auteur.");
            return;
        }

        // Validate numeric inputs
        int publicationYear = 0, column = 0, row = 0;
        try {
            if (!publicationYearStr.isEmpty()) publicationYear = Integer.parseInt(publicationYearStr);
            if (!columnStr.isEmpty()) column = Integer.parseInt(columnStr);
            if (!rowStr.isEmpty()) row = Integer.parseInt(rowStr);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez entrer des valeurs numériques valides pour l'année, la colonne et la rangée.");
            return;
        }

        // Show confirmation alert
        showAlert(Alert.AlertType.INFORMATION, "Livre ajouté", 
            "Livre ajouté : " + title +
            "\nAuteur : " + firstName + " " + lastName +
            "\nAnnée : " + publicationYear +
            "\nDescription : " + description +
            "\nColonne : " + column +
            "\nRangée : " + row +
            "\nEmprunté : " + (isBorrowed ? "Oui" : "Non"));

        // Clear form fields
        clearForm();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearForm() {
        titleField.clear();
        authorFirstNameField.clear();
        authorLastNameField.clear();
        descriptionField.clear();
        publicationYearField.clear();
        columnField.clear();
        rowField.clear();
        imageUrlField.clear();
        borrowed.setSelected(false);
    }
}
