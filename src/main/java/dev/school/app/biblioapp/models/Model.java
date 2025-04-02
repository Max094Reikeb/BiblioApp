package dev.school.app.biblioapp.models;

import dev.school.app.biblioapp.views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

	private static Model model;
	private final ViewFactory viewFactory;
	private final ObservableList<Book> bookObservableList;

	private Model() {
		this.viewFactory = new ViewFactory();
		this.bookObservableList = FXCollections.observableArrayList();
	}

	public static synchronized Model getInstance() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}

	public ViewFactory getViewFactory() {
		return viewFactory;
	}

	public ObservableList<Book> getBooks() {
		return bookObservableList;
	}
}
