package dev.school.app.biblioapp;

/**
 * Classe type représentant un livre dans la bibliothèque.
 *
 * @param title           Titre du livre.
 * @param authorFirstName Prénom de l'auteur du livre.
 * @param authorLastName  Nom de famille de l'auteur du livre.
 * @param description     Description courte du livre.
 * @param publicationYear Année de sortie du livre.
 * @param column          Colonne où se trouve le livre physiquement dans la bibliothèque.
 * @param row             Rangée où se trouve le livre physiquement dans la bibliothèque.
 */
public record Book(String title, String authorFirstName, String authorLastName, String description, int publicationYear,
				   int column, int row) {

	/**
	 * Fonction permettant d'obtenir les informations d'un livre à partir de celui-ci.
	 *
	 * @return Un string avec les informations du livre.
	 */
	@Override
	public String toString() {
		return "Book{" +
				"title='" + title + '\'' +
				", authorFirstName='" + authorFirstName + '\'' +
				", authorLastName='" + authorLastName + '\'' +
				", description='" + description + '\'' +
				", publicationYear=" + publicationYear +
				", column=" + column +
				", row=" + row +
				'}';
	}
}
