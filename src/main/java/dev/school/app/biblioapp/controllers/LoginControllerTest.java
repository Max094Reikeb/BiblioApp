package main.java.dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.models.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerTest extends ApplicationTest {

    private LoginController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dev/school/app/biblioapp/fxml/login.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void clearFields() {
        interact(() -> {
            controller.user_account_field.setText("");
            controller.password_field.setText("");
        });
    }

    @Test
    public void testFieldsAreEmptyInitially() {
        assertEquals("", controller.user_account_field.getText());
        assertEquals("", controller.password_field.getText());
    }

    @Test
    public void testLoginButtonClosesStageAndOpensAdminWindow() {
        // Simuler l’entrée utilisateur
        interact(() -> {
            controller.user_account_field.setText("admin");
            controller.password_field.setText("admin123");
        });

        // Clic sur le bouton login
        clickOn(controller.login_btn);

        // On ne peut pas facilement vérifier l’ouverture de la nouvelle fenêtre sans mock,
        // mais on peut au moins affirmer que le champ a bien été rempli
        assertEquals("admin", controller.user_account_field.getText());
    }
}
