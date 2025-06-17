package dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.models.Book;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    // Liste simulée des livres existants
    private List<Book> bookList = new ArrayList<>();

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

        // Validation des champs obligatoires
        if (title.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champs manquants",
                    "Veuillez remplir au minimum le titre et le nom de l'auteur.");
            return;
        }

        int year;
        try {
            year = Integer.parseInt(publicationYear);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie",
                    "L'année de publication doit être un nombre valide.");
            return;
        }

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

        if (col < 1 || col > 7 || rowNumber < 1 || rowNumber > 7) {
            showAlert(Alert.AlertType.ERROR, "Valeurs incorrectes",
                    "Les champs Colonne et Rangée doivent être des nombres entre 1 et 7.");
            return;
        }

        // Vérification d'unicité
        boolean exists = bookList.stream()
                .anyMatch(book -> book.getTitle().equalsIgnoreCase(title)
                        && book.getAuthorFirstName().equalsIgnoreCase(firstName)
                        && book.getAuthorLastName().equalsIgnoreCase(lastName)
                        && book.getPublicationYear() == year);

        if (exists) {
            showAlert(Alert.AlertType.ERROR, "Livre existant",
                    "Un livre avec ce titre, auteur et année de publication existe déjà.");
            return;
        }

        int currentYear = java.time.Year.now().getValue();
        if (year > currentYear) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie",
            "L'année de publication ne peut pas être supérieure à l'année en cours (" + currentYear + ").");
            return;
        }

        // Création du livre
        Book book = new Book(title, firstName, lastName, description, year, col, rowNumber, imageUrl, isBorrowed);

        // Ajout à la liste
        bookList.add(book);

        showAlert(Alert.AlertType.INFORMATION, "Livre ajouté",
                "Le livre a bien été ajouté :\n\n" + book.toString());

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
