package dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.models.Book;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

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
        String publicationYear = publicationYearField.getText().trim();
        String column = columnField.getText().trim();
        String row = rowField.getText().trim();
        String imageUrl = imageUrlField.getText().trim();
        boolean isBorrowed = borrowed.isSelected();

        // Validation: required fields
        if (title.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champs manquants",
                    "Veuillez remplir au minimum le titre et le nom de l'auteur.");
            return;
        }

        // Validation: publication year must be an integer
        int year;
        try {
            year = Integer.parseInt(publicationYear);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie",
                    "L'année de publication doit être un nombre valide.");
            return;
        }

        // Validation: column and row must be integers
        int col;
        int rowNumber;
        try {
            col = Integer.parseInt(column);
            rowNumber = Integer.parseInt(row);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie",
                    "Les champs Colonne et Rangée doivent être des nombres valides.");
            return;
        }

        // Create the Book object
        Book book = new Book(
                title,
                firstName,
                lastName,
                description,
                year,
                col,
                rowNumber,
                imageUrl,
                isBorrowed
        );

        // (Optional) Here you could add the book to a database, list, or TableView!

        // Confirmation dialog with Book's toString
        showAlert(Alert.AlertType.INFORMATION, "Livre ajouté", "Le livre a bien été ajouté :\n\n" + book.toString());

        // Clear the form
        clearForm();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
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
