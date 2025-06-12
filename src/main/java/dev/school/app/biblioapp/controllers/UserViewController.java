package dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.models.User;
import dev.school.app.biblioapp.models.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UserViewController {

	@FXML
	private TableView<User> userTable;
	@FXML
	private TableColumn<User, String> usernameColumn;
	@FXML
	private TableColumn<User, String> passwordColumn;
	@FXML
	private TableColumn<User, Boolean> adminColumn;
	@FXML
	private TableColumn<User, Void> actionsColumn;

	private final ObservableList<User> users = FXCollections.observableArrayList();
	private final UserManager userManager = new UserManager("src/main/resources/dev/school/app/biblioapp/users.xml");

	@FXML
	public void initialize() {
		users.addAll(userManager.getAllUsers());
		userTable.setItems(users);

		usernameColumn.setCellValueFactory(data ->
				new javafx.beans.property.SimpleStringProperty(data.getValue().getUsername()));
		passwordColumn.setCellValueFactory(data ->
				new javafx.beans.property.SimpleStringProperty(data.getValue().getPassword()));
		adminColumn.setCellValueFactory(data ->
				new javafx.beans.property.SimpleBooleanProperty(data.getValue().isAdmin()));

		// TODO: Add delete/edit action buttons here
	}

	@FXML
	private void onAddUser() {
		// TODO: Open a form to create a new user
	}

	@FXML
	private void onSaveUsers() {
		userManager.saveUsers(users);
	}
}
