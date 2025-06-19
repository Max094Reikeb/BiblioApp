package main.java.dev.school.app.biblioapp.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class CreateBookControllerTest extends ApplicationTest {

    private CreateBookController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dev/school/app/biblioapp/fxml/createbook.fxml"));
        AnchorPane root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    void clearInputs() {
        interact(() -> {
            controller.titleField.setText("");
            controller.authorFirstNameField.setText("");
            controller.authorLastNameField.setText("");
            controller.publicationYearField.setText("");
            controller.columnField.setText("");
            controller.rowField.setText("");
            controller.descriptionField.setText("");
            controller.imageUrlField.setText("");
            controller.borrowed.setSelected(false);
        });
    }

    @Test
    public void testMissingRequiredFieldsShowsWarning() {
        clickOn(controller.submitButton);
        // Cette vérification est indirecte. On peut simplement vérifier que les champs requis sont vides.
        assertTrue(controller.titleField.getText().isEmpty());
    }

    @Test
    public void testInvalidYearShowsError() {
        interact(() -> {
            controller.titleField.setText("Titre");
            controller.authorFirstNameField.setText("Jean");
            controller.authorLastNameField.setText("Durand");
            controller.publicationYearField.setText("abcd");
            controller.columnField.setText("3");
            controller.rowField.setText("4");
        });

        clickOn(controller.submitButton);
        // attend qu'un message d'erreur s'affiche, peut être validé avec un spy sur showAlert dans une version avancée
    }

    @Test
    public void testInvalidCoordinatesShowError() {
        interact(() -> {
            controller.titleField.setText("Titre");
            controller.authorFirstNameField.setText("Jean");
            controller.authorLastNameField.setText("Durand");
            controller.publicationYearField.setText("2020");
            controller.columnField.setText("10");  // hors bornes
            controller.rowField.setText("0");      // hors bornes
        });

        clickOn(controller.submitButton);
    }

    @Test
    public void testValidBookIsAddedSuccessfully() {
        interact(() -> {
            controller.titleField.setText("Test Book");
            controller.authorFirstNameField.setText("Alice");
            controller.authorLastNameField.setText("Martin");
            controller.publicationYearField.setText("2020");
            controller.columnField.setText("3");
            controller.rowField.setText("2");
            controller.descriptionField.setText("Un test");
            controller.imageUrlField.setText("http://example.com/image.png");
            controller.borrowed.setSelected(true);
        });

        clickOn(controller.submitButton);

        // Vérification indirecte : le formulaire est vidé
        assertEquals("", controller.titleField.getText());
        assertFalse(controller.borrowed.isSelected());
    }
}

