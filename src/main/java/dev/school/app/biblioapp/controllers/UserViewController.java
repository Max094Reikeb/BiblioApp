package dev.school.app.biblioapp.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dev.school.app.biblioapp.Main;
import dev.school.app.biblioapp.models.User;
import dev.school.app.biblioapp.models.UserManager;
import dev.school.app.biblioapp.views.AboutWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.text.MessageFormat;
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

	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private CheckBox adminCheckBox;

	/**
	 * Fonction principale se lançant lors de l'initialisation du controller.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		users.addAll(userManager.getAllUsers());
		userTable.setItems(users);

		aboutLabel.setOnMouseClicked(e -> new AboutWindow());

		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
		adminColumn.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));

		actionsColumn.setCellFactory(column -> new TableCell<>() {
			private final Button editButton = new Button();
			private final Button deleteButton = new Button();
			private final HBox actionButtons = new HBox(5);


			{
				// Edit button
				FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
				editIcon.setGlyphSize(16);
				editIcon.getStyleClass().add("edit-icon");

				editButton.setGraphic(editIcon);
				editButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

				editButton.setOnAction(e -> {
					User user = getTableView().getItems().get(getIndex());

					usernameField.setText(user.getUsername());
					passwordField.setText(user.getPassword());
					adminCheckBox.setSelected(user.isAdmin());
				});

				// Delete button
				FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
				icon.setGlyphSize(16);
				icon.getStyleClass().add("delete-icon");

				deleteButton.setGraphic(icon);
				deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

				deleteButton.setOnAction(e -> {
					User user = getTableView().getItems().get(getIndex());

					Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
					confirm.setTitle(bundle.getString("global.delete.title"));
					confirm.setHeaderText(bundle.getString("global.delete.header"));
					confirm.setContentText(MessageFormat.format(bundle.getString("user.delete.message"), user.getUsername()));

					ButtonType yes = new ButtonType(bundle.getString("global.yes"), ButtonBar.ButtonData.YES);
					ButtonType no = new ButtonType(bundle.getString("global.no"), ButtonBar.ButtonData.NO);

					confirm.getButtonTypes().setAll(yes, no);

					confirm.showAndWait().ifPresent(response -> {
						if (response == yes) {
							userTable.getItems().remove(user);
							// TODO: Remove user
						}
					});
				});

				actionButtons.getChildren().addAll(editButton, deleteButton);
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(actionButtons);
				}
			}
		});
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

	/**
	 * Function permettant de nettoyer le formulaire de création d'utilisateur.
	 */
	private void clearForm() {
		usernameField.clear();
		passwordField.clear();
		adminCheckBox.setSelected(false);
	}
}
