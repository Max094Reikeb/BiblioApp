package dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

	public Label user_account_lbl;
	public TextField user_account_field;
	public TextField password_field;
	public Button login_btn;
	public Label error_lbl;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		login_btn.setOnAction(event -> onLogin());
	}

	private void onLogin() {
		Stage stage = (Stage) error_lbl.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
		Model.getInstance().getViewFactory().showAdminWindow();
	}
}
