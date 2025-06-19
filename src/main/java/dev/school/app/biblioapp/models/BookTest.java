package dev.school.app.biblioapp.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookTest {

	@Test
	public void testBookProperties() {
		Book book = new Book(
				"Titre Test",
				"Prénom",
				"Nom",
				"Description test",
				2025,
				1,
				2,
				"http://image.url/image.png",
				true
		);

		// Test des getters classiques
		assertEquals("Titre Test", book.getTitle());
		assertEquals("Prénom", book.getAuthorFirstName());
		assertEquals("Nom", book.getAuthorLastName());
		assertEquals("Description test", book.getDescription());
		assertEquals(2025, book.getPublicationYear());
		assertEquals(1, book.getColumn());
		assertEquals(2, book.getRow());
		assertEquals("http://image.url/image.png", book.getPathImage());
		assertTrue(book.isBorrowed());

		// Test des propriétés bindables JavaFX
		assertEquals("Titre Test", book.titleProperty().get());
		assertEquals("Prénom", book.authorFirstNameProperty().get());
		assertEquals("Nom", book.authorLastNameProperty().get());
		assertEquals("Description test", book.descriptionProperty().get());
		assertEquals(2025, book.publicationYearProperty().get());
		assertEquals(1, book.columnProperty().get());
		assertEquals(2, book.rowProperty().get());
		assertEquals("http://image.url/image.png", book.pathImageProperty().get());
		assertTrue(book.borrowedProperty().get());

		// Test de la méthode toString (vérification basique)
		String toStringResult = book.toString();
		assertTrue(toStringResult.contains("Titre Test"));
		assertTrue(toStringResult.contains("Prénom"));
		assertTrue(toStringResult.contains("Nom"));
		assertTrue(toStringResult.contains("Description test"));
		assertTrue(toStringResult.contains("2025"));
	}
}
