package dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.models.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class AdminControllerTest extends ApplicationTest {

    private AdminController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dev/school/app/biblioapp/fxml/admin.fxml"));
        AnchorPane root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    void setUp() {
        // Initialisation possible ici
    }

    @Test
    void testControllerIsNotNull() {
        assertNotNull(controller, "AdminController should be loaded and not null");
    }

    @Test
    void testLeftContainerIsLoaded() {
        assertFalse(controller.left_container.getChildren().isEmpty(), "Left container should contain the admin menu");
    }

    @Test
    void testInitialCenterViewIsLoaded() {
        assertFalse(controller.center_container.getChildren().isEmpty(), "Center container should contain the default view");
    }
}
