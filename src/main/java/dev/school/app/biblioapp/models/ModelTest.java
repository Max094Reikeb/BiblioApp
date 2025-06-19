package dev.school.app.biblioapp.models;

import dev.school.app.biblioapp.views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModelTest {

	private static ModelTest instance;

	private final ViewFactory viewFactory;
	private final ObservableList<Book> bookObservableList;
	private final ObservableList<Book> unmodifiableBookList;

	private ModelTest() {
		this.viewFactory = new ViewFactory();
		this.bookObservableList = FXCollections.observableArrayList();
		this.unmodifiableBookList = FXCollections.unmodifiableObservableList(bookObservableList);
	}

	public static ModelTest getInstance() {
		if (instance == null) {
			instance = new ModelTest();
		}
		return instance;
	}

	public ViewFactory getViewFactory() {
		return viewFactory;
	}

	public ObservableList<Book> getBooks() {
		return unmodifiableBookList;
	}

	public void addBook(Book book) {
		bookObservableList.add(book);
	}

	public void removeBook(Book book) {
		bookObservableList.remove(book);
	}

	public void clearBooks() {
		bookObservableList.clear();
	}
}
