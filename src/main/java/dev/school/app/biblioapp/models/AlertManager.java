package dev.school.app.biblioapp.models;

import dev.school.app.biblioapp.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ResourceBundle;
import java.util.function.Consumer;

public class AlertManager {

	private static final ResourceBundle bundle = Main.getBundle();

	/**
	 * Displays an alert dialog with the specified configuration.
	 *
	 * @param alertType       The type of alert to be displayed (e.g., INFO, WARNING, ERROR, etc.).
	 * @param alertTitle      The title of the alert dialog.
	 * @param alertHeader     The header text of the alert dialog, providing additional context.
	 * @param alertContent    The main content or message of the alert dialog.
	 * @param responseHandler A consumer to handle the button clicked by the user, receiving the selected {@code ButtonType}.
	 * @param buttonTypes     The set of buttons to display in the alert dialog, enabling custom actions.
	 */
	public static void showAlert(Alert.AlertType alertType, String alertTitle, String alertHeader, String alertContent,
								 Consumer<ButtonType> responseHandler, ButtonType... buttonTypes) {
		Alert alert = new Alert(alertType);
		alert.setTitle(alertTitle);
		alert.setHeaderText(alertHeader);
		alert.setContentText(alertContent);
		alert.getButtonTypes().setAll(buttonTypes);
		alert.showAndWait().ifPresent(responseHandler);
	}

	/**
	 * Displays an alert dialog with the given properties.
	 *
	 * @param alertType    The type of the alert (e.g., INFORMATION, WARNING, ERROR).
	 * @param alertTitle   The title of the alert to be displayed.
	 * @param alertHeader  The header text displayed in the alert.
	 * @param alertContent The main content or message displayed in the alert.
	 */
	public static void showAlert(Alert.AlertType alertType, String alertTitle, String alertHeader, String alertContent) {
		Alert alert = new Alert(alertType);
		alert.setTitle(alertTitle);
		alert.setHeaderText(alertHeader);
		alert.setContentText(alertContent);
		alert.showAndWait();
	}

	/**
	 * Displays an error alert dialog with a specified message.
	 *
	 * @param message The key used to retrieve the error message from the resource bundle.
	 */
	public static void showError(String message) {
		showAlert(Alert.AlertType.ERROR, bundle.getString("global.error"), null, bundle.getString(message));
	}
}
