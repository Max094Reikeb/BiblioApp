package dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.Main;
import dev.school.app.biblioapp.models.User;
import dev.school.app.biblioapp.models.UserManager;
import dev.school.app.biblioapp.views.AboutWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class UserViewController implements Initializable {

	private static final Logger LOGGER = Main.getLogger();
	private final ResourceBundle bundle = Main.getBundle();

	private final ObservableList<User> users = FXCollections.observableArrayList();
	private final UserManager userManager = new UserManager("src/main/resources/dev/school/app/biblioapp/users.xml");

	@FXML
	private Label aboutLabel;

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

	/**
	 * Fonction principale se lançant lors de l'initialisation du controller.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		users.addAll(userManager.getAllUsers());
		userTable.setItems(users);

		aboutLabel.setOnMouseClicked(e -> new AboutWindow());

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

	/**
	 * Fonction s'exécutant lorsque l'utilisateur veut fermer l'application. (via le bouton "Quitter")
	 *
	 * @param event Évènement / Action utilisateur.
	 */
	@FXML
	void closeApp(MouseEvent event) {
		Stage stage = (Stage) ((javafx.scene.control.Label) event.getSource())
				.getScene().getWindow();

		LOGGER.info("Closing app...");
		stage.close();
	}
}
