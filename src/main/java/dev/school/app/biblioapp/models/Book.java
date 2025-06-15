package dev.school.app.biblioapp.models;

import javafx.beans.property.*;

/**
 * Classe-type représentant un livre dans la bibliothèque.
 * Contient des propriétés liantes (bindings) pour mettre à jour la table FXML.
 */
public class Book {
	private final SimpleStringProperty title;
	private final SimpleStringProperty authorFirstName;
	private final SimpleStringProperty authorLastName;
	private final SimpleStringProperty description;
	private final SimpleIntegerProperty publicationYear;
	private final SimpleIntegerProperty column;
	private final SimpleIntegerProperty row;
	private final SimpleStringProperty pathImage;
	private final SimpleBooleanProperty borrowed;


	/**
	 * Initialisation d'un livre-type de la bibliothèque.
	 *
	 * @param title           Titre du livre.
	 * @param authorFirstName Prénom de l'auteur du livre.
	 * @param authorLastName  Nom de famille de l'auteur du livre.
	 * @param description     Description courte du livre.
	 * @param publicationYear Année de publication du livre.
	 * @param column          Colonne où se trouve le livre physiquement dans la bibliothèque.
	 * @param row             Rangée où se trouve le livre physiquement dans la bibliothèque.
	 * @param borrowed        Est-ce que le livre a été emprunté ou non.
	 */
	public Book(String title, String authorFirstName, String authorLastName, String description, int publicationYear,
				int column, int row, String pathImage, boolean borrowed) {
		this.title = new SimpleStringProperty(title);
		this.authorFirstName = new SimpleStringProperty(authorFirstName);
		this.authorLastName = new SimpleStringProperty(authorLastName);
		this.description = new SimpleStringProperty(description);
		this.publicationYear = new SimpleIntegerProperty(publicationYear);
		this.column = new SimpleIntegerProperty(column);
		this.row = new SimpleIntegerProperty(row);
		this.pathImage = new SimpleStringProperty(pathImage);
		this.borrowed = new SimpleBooleanProperty(borrowed);
	}

	/**
	 * Getter public du titre du livre.
	 *
	 * @return Le titre du livre.
	 */
	public String getTitle() {
		return title.get();
	}

	/**
	 * Getter public du prénom de l'auteur du livre.
	 *
	 * @return Le prénom de l'auteur du livre.
	 */
	public String getAuthorFirstName() {
		return authorFirstName.get();
	}

	/**
	 * Getter public du nom de famille de l'auteur du livre.
	 *
	 * @return Le nom de famille de l'auteur du livre.
	 */
	public String getAuthorLastName() {
		return authorLastName.get();
	}

	/**
	 * Getter public de la description du livre.
	 *
	 * @return La description du livre.
	 */
	public String getDescription() {
		return description.get();
	}

	/**
	 * Getter public de l'année de publication du livre.
	 *
	 * @return Année de publication du livre.
	 */
	public int getPublicationYear() {
		return publicationYear.get();
	}

	/**
	 * Getter public de la colonne où se trouve le livre physiquement dans la bibliothèque.
	 *
	 * @return Colonne où se trouve le livre physiquement dans la bibliothèque.
	 */
	public int getColumn() {
		return column.get();
	}

	/**
	 * Getter public de la rangée où se trouve le livre physiquement dans la bibliothèque.
	 *
	 * @return Rangée où se trouve le livre physiquement dans la bibliothèque.
	 */
	public int getRow() {
		return row.get();
	}

	/**
	 * Getter public de la couverture du livre.
	 *
	 * @return L'URL de la couverture du livre.
	 */
	public String getPathImage() {
		return pathImage.get();
	}

	/**
	 * Getter public de si le livre a été emprunté ou non.
	 *
	 * @return Si le livre a été emprunté ou non.
	 */
	public boolean isBorrowed() {
		return borrowed.get();
	}

	/**
	 * Getter bindé pour JavaFX
	 *
	 * @return Le titre du livre.
	 */
	public StringProperty titleProperty() {
		return title;
	}

	/**
	 * Getter bindé pour JavaFX
	 *
	 * @return Le prénom de l'auteur du livre.
	 */
	public StringProperty authorFirstNameProperty() {
		return authorFirstName;
	}

	/**
	 * Getter bindé pour JavaFX
	 *
	 * @return Le nom de famille de l'auteur du livre.
	 */
	public StringProperty authorLastNameProperty() {
		return authorLastName;
	}

	/**
	 * Getter bindé pour JavaFX
	 *
	 * @return La description du livre.
	 */
	public StringProperty descriptionProperty() {
		return description;
	}

	/**
	 * Getter bindé pour JavaFX
	 *
	 * @return L'année de publication du livre.
	 */
	public IntegerProperty publicationYearProperty() {
		return publicationYear;
	}

	/**
	 * Getter bindé pour JavaFX
	 *
	 * @return Colonne où se trouve le livre physiquement dans la bibliothèque.
	 */
	public IntegerProperty columnProperty() {
		return column;
	}

	/**
	 * Getter bindé pour JavaFX
	 *
	 * @return Rangée où se trouve le livre physiquement dans la bibliothèque.
	 */
	public IntegerProperty rowProperty() {
		return row;
	}

	/**
	 * Getter bindé pour JavaFX
	 *
	 * @return URL de la couverture du livre.
	 */
	public StringProperty pathImageProperty() {
		return pathImage;
	}

	/**
	 * Getter bindé pour JavaFX
	 *
	 * @return Si le livre a été emprunté ou non.
	 */
	public BooleanProperty borrowedProperty() {
		return borrowed;
	}

	/**
	 * Fonction permettant d'obtenir les informations d'un livre à partir de celui-ci.
	 *
	 * @return Un string avec les informations du livre.
	 */
	@Override
	public String toString() {
		return "Book{" +
				"title='" + getTitle() + '\'' +
				", authorFirstName='" + getAuthorFirstName() + '\'' +
				", authorLastName='" + getAuthorLastName() + '\'' +
				", description='" + getDescription() + '\'' +
				", publicationYear=" + getPublicationYear() +
				", column=" + getColumn() +
				", row=" + getRow() +
				", pathImage=" + getPathImage() +
				", borrowed=" + isBorrowed() +
				'}';
	}
}
