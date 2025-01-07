package dev.school.app.biblioapp;

public record Book(String title, String authorFirstName, String authorLastName, String description, int publicationYear,
				   int column, int row) {

	// Display
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
