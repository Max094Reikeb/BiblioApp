package dev.school.app.biblioapp.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AdminControllerTest extends ApplicationTest {

	private AdminController controller;
	private AnchorPane root;

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/dev/school/app/biblioapp/fxml/admin.fxml")
		);
		root = loader.load();
		controller = loader.getController();
		stage.setScene(new Scene(root));
		stage.show();
	}

	@BeforeEach
	void setUp() {
		assertNotNull(controller, "Le contrôleur AdminController ne doit pas être null.");
	}

	@Test
	void testControllerIsNotNull() {
		assertNotNull(controller, "Le contrôleur AdminController ne doit pas être null.");
	}

	@Test
	void testLeftContainerExistsAndNotEmpty() {
		assertNotNull(controller.getLeftContainer(), "left_container doit être initialisé.");
		assertFalse(controller.getLeftContainer().getChildren().isEmpty(),
				"left_container doit contenir au moins un élément.");
	}

	@Test
	void testCenterContainerExistsAndNotEmpty() {
		assertNotNull(controller.getCenterContainer(), "center_container doit être initialisé.");
		assertFalse(controller.getCenterContainer().getChildren().isEmpty(),
				"center_container doit contenir une vue par défaut.");
	}
}
