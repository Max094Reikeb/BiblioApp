package dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.Main;
import dev.school.app.biblioapp.models.AlertManager;
import dev.school.app.biblioapp.models.Book;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateBookController implements Initializable {

	private static final Logger LOGGER = Main.getLogger();

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
			AlertManager.showError("book.form.error.missing");
			return;
		}

		int year;
		try {
			year = Integer.parseInt(publicationYear);
		} catch (NumberFormatException e) {
			AlertManager.showError("book.form.error.publish");
			return;
		}

		int col;
		int rowNumber;
		try {
			col = Integer.parseInt(column);
			rowNumber = Integer.parseInt(row);
		} catch (NumberFormatException e) {
			AlertManager.showError("book.form.error.number");
			return;
		}

		if (col < 1 || col > 7 || rowNumber < 1 || rowNumber > 7) {
			AlertManager.showError("book.form.error.outbound");
			return;
		}

		// Vérification d'unicité
		boolean exists = bookList.stream()
				.anyMatch(book -> book.getTitle().equalsIgnoreCase(title)
						&& book.getAuthorFirstName().equalsIgnoreCase(firstName)
						&& book.getAuthorLastName().equalsIgnoreCase(lastName)
						&& book.getPublicationYear() == year);

		if (exists) {
			AlertManager.showError("book.form.error.unique");
			return;
		}

		int currentYear = java.time.Year.now().getValue();
		if (year > currentYear) {
			AlertManager.showError("book.form.error.year");
			return;
		}

		// Création du livre
		Book book = new Book(title, firstName, lastName, description, year, col, rowNumber, imageUrl, isBorrowed);

		// Ajout à la liste
		bookList.add(book);

		LOGGER.log(Level.INFO, "Livre ajouté : {0}", book.toString());

		clearForm();
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
