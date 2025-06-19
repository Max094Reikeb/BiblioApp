package dev.school.app.biblioapp.models;

import dev.school.app.biblioapp.views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModelTest {
	// ...

	private final ObservableList<Book> bookObservableList;
	private final ObservableList<Book> unmodifiableBookList;

	private ModelTest() {
		this.viewFactory = new ViewFactory();
		this.bookObservableList = FXCollections.observableArrayList();
		this.unmodifiableBookList = FXCollections.unmodifiableObservableList(bookObservableList);
	}

	public ObservableList<Book> getBooks() {
		return unmodifiableBookList;
	}

	// Ajouter une méthode publique pour modifier la liste
	public void addBook(Book book) {
		bookObservableList.add(book);
	}

	public void removeBook(Book book) {
		bookObservableList.remove(book);
	}
}
