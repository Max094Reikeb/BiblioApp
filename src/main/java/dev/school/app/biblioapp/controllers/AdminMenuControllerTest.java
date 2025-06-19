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
		loader.setResources(ResourceBundle.getBundle("dev.school.app.biblioapp.bundles.lang", Locale.FRENCH)); 
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
	}

    @Test
    void testClickCreateBookButton(FxRobot robot) {
    robot.clickOn("#create_book_btn");

    FxRobot waitRobot = new FxRobot();
    Stage dialogStage = waitRobot.listTargetWindows().stream()
            .filter(w -> w instanceof Stage)
            .map(w -> (Stage) w)
            .filter(Stage::isShowing)
            .filter(stage -> stage.getTitle().equals("Ajouter un livre")) 
            .findFirst()
            .orElse(null);

    assertNotNull(dialogStage, "La fenêtre de dialogue pour ajouter un livre devrait s’ouvrir.");
}
}