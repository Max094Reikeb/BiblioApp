package dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.Main;
import dev.school.app.biblioapp.models.AlertManager;
import dev.school.app.biblioapp.models.Model;
import dev.school.app.biblioapp.models.User;
import dev.school.app.biblioapp.models.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {

	private static final Logger LOGGER = Main.getLogger();

	private UserManager userManager;

	@FXML
	public TextField user_account_field;
	@FXML
	public TextField password_field;

	@FXML
	public Button login_btn;

	/**
	 * Fonction principale se lançant lors de l'initialisation du controller.
	 *
	 * @param location  l'URL de l'objet root object, ou null si aucun emplacement n'est spécifié.
	 * @param resources le ResourceBundle permettant de traduire l'objet root, ou null si aucun bundle n'est spécifié.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userManager = new UserManager("src/main/resources/dev/school/app/biblioapp/users.xml");
		login_btn.setOnAction(event -> onLogin());
	}

	/**
	 * Gère la connexion de l'utilisateur.
	 */
	private void onLogin() {
		String username = user_account_field.getText();
		String password = password_field.getText();

		User user = userManager.authenticate(username, password);
		if (user != null) {
			LOGGER.log(Level.INFO, "Login success for: ", user.getUsername());

			Stage stage = (Stage) login_btn.getScene().getWindow();
			Model.getInstance().getViewFactory().closeStage(stage);
			Model.getInstance().setCurrentUser(user);

			if (user.isAdmin()) {
				Model.getInstance().getViewFactory().showAdminWindow();
			} else {
				Model.getInstance().getViewFactory().showUserWindow();
			}
		} else {
			LOGGER.log(Level.INFO, "Login failed for: ", username);
			AlertManager.showError("login.error");
		}
	}
}
