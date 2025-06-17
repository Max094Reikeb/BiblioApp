package dev.school.app.biblioapp.models;

import dev.school.app.biblioapp.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ResourceBundle;
import java.util.function.Consumer;

public class AlertManager {

	private static final ResourceBundle bundle = Main.getBundle();

	public static void showAlert(Alert.AlertType alertType, String alertTitle, String alertHeader, String alertContent,
								 Consumer<ButtonType> responseHandler, ButtonType... buttonTypes) {
		Alert alert = new Alert(alertType);
		alert.setTitle(alertTitle);
		alert.setHeaderText(alertHeader);
		alert.setContentText(alertContent);
		alert.getButtonTypes().setAll(buttonTypes);
		alert.showAndWait().ifPresent(responseHandler);
	}

	public static void showAlert(Alert.AlertType alertType, String alertTitle, String alertHeader, String alertContent) {
		Alert alert = new Alert(alertType);
		alert.setTitle(alertTitle);
		alert.setHeaderText(alertHeader);
		alert.setContentText(alertContent);
		alert.showAndWait();
	}

	public static void showError(String message) {
		showAlert(Alert.AlertType.ERROR, bundle.getString("global.error"), null, bundle.getString(message));
	}
}
