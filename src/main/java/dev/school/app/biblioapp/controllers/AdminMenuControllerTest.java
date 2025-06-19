package dev.school.app.biblioapp.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
public class AdminMenuControllerTest {

	private AdminMenuController controller;

	@Start
	public void start(Stage stage) throws Exception {
		var loader = new javafx.fxml.FXMLLoader(getClass().getResource("/dev/school/app/biblioapp/fxml/admin-menu.fxml"));
		loader.setResources(ResourceBundle.getBundle("dev.school.app.biblioapp.bundles.lang", Locale.FRENCH)); // adapte si nécessaire
		AnchorPane root = loader.load();
		controller = loader.getController();

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Test
	void testCreateBookButtonExists(FxRobot robot) {
		Button createButton = robot.lookup("#create_book_btn").queryAs(Button.class);
		assertNotNull(createButton, "Le bouton 'create_book_btn' devrait exister dans la vue.");
	}

	@Test
	void testClickCreateBookButton(FxRobot robot) {
		robot.clickOn("#create_book_btn");
		// TODO : ajouter une vérification après le clic si un dialogue s’ouvre
		// Par exemple : vérifier si un Stage modal est ouvert (si tu extrais la logique dans une méthode testable)
	}
}