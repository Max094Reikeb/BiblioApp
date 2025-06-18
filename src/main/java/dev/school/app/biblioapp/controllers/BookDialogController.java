package dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.models.AlertManager;
import dev.school.app.biblioapp.models.Book;
import dev.school.app.biblioapp.models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookDialogController implements Initializable {

	private Stage dialogStage;
	private Book book;
	private boolean saved = false;
	private boolean isEditMode = false;

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

	/**
	 * Fonction principale se lançant lors de l'initialisation du controller.
	 *
	 * @param location  l'URL de l'objet root object, ou null si aucun emplacement n'est spécifié.
	 * @param resources le ResourceBundle permettant de traduire l'objet root, ou null si aucun bundle n'est spécifié.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * Gère la sauvegarde d'un objet {@Book}.
	 */
	@FXML
	private void handleSave() {
		String title = titleField.getText().trim();
		String firstName = authorFirstNameField.getText().trim();
		String lastName = authorLastNameField.getText().trim();
		String description = descriptionField.getText().trim();
		String publicationYear = publicationYearField.getText().trim();
		String column = columnField.getText().trim();
		String row = rowField.getText().trim();
		String imageUrl = imageUrlField.getText().trim();
		boolean isBorrowed = borrowed.isSelected();

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

		List<Book> bookList = Model.getInstance().getBooks();
		boolean duplicate = bookList.stream().anyMatch(b ->
				b != book && b.getTitle().equalsIgnoreCase(title)
						&& b.getAuthorFirstName().equalsIgnoreCase(firstName)
						&& b.getAuthorLastName().equalsIgnoreCase(lastName)
						&& b.getPublicationYear() == year
		);

		if (duplicate) {
			AlertManager.showError("book.form.error.unique");
			return;
		}

		int currentYear = java.time.Year.now().getValue();
		if (year > currentYear) {
			AlertManager.showError("book.form.error.year");
			return;
		}

		if (!isEditMode) {
			book = new Book(
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
		} else {
			book.titleProperty().set(title);
			book.authorFirstNameProperty().set(firstName);
			book.authorLastNameProperty().set(lastName);
			book.descriptionProperty().set(description);
			book.publicationYearProperty().set(year);
			book.columnProperty().set(col);
			book.rowProperty().set(rowNumber);
			book.pathImageProperty().set(imageUrl);
			book.borrowedProperty().set(isBorrowed);
		}

		Model.getInstance().saveBook(book, isEditMode);

		saved = true;
		dialogStage.close();
	}

	/**
	 * Gère l'arrêt de l'action utilisateur et la fermeture de la fenêtre.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Met les données du livre dans les champs et initialize le formulaire.
	 *
	 * @param book       l'objet {@Book} dont on veut les données.
	 * @param isEditMode indique si on est en mode édition ou non.
	 */
	public void setBook(Book book, boolean isEditMode) {
		this.book = book;
		this.isEditMode = isEditMode;

		if (book != null) {
			titleField.setText(book.getTitle());
			authorFirstNameField.setText(book.getAuthorFirstName());
			authorLastNameField.setText(book.getAuthorLastName());
			descriptionField.setText(book.getDescription());
			publicationYearField.setText(String.valueOf(book.getPublicationYear()));
			columnField.setText(String.valueOf(book.getColumn()));
			rowField.setText(String.valueOf(book.getRow()));
			imageUrlField.setText(book.getPathImage());
			borrowed.setSelected(book.isBorrowed());
		}
	}

	/**
	 * Met la scène pour la fenêtre de dialogue.
	 *
	 * @param stage la scène utilisée.
	 */
	public void setDialogStage(Stage stage) {
		this.dialogStage = stage;
	}

	/**
	 * Vérifie si l'état actuel est sauvegardé.
	 *
	 * @return true si l'état est sauvegardé, false sinon.
	 */
	public boolean isSaved() {
		return saved;
	}

	/**
	 * Retourne l'instance {@Book} actuellement associée avec ce controller.
	 *
	 * @return l'instance {@Book}.
	 */
	public Book getBook() {
		return book;
	}
}
