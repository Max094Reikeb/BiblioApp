package dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.Main;
import dev.school.app.biblioapp.models.Model;
import dev.school.app.biblioapp.models.User;
import dev.school.app.biblioapp.models.UserManager;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {

	private static final Logger LOGGER = Main.getLogger();
	private final ResourceBundle bundle = Main.getBundle();

	private UserManager userManager;

	public TextField user_account_field;
	public TextField password_field;
	public Button login_btn;
	public Label user_account_lbl;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userManager = new UserManager("src/main/resources/dev/school/app/biblioapp/users.xml");
		login_btn.setOnAction(event -> onLogin());
	}

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
			user_account_lbl.setText(bundle.getString("login.error"));
		}
	}
}
