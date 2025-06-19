package main.java.dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.models.Book;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class BookDialogControllerTest extends ApplicationTest {

    private BookDialogController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dev/school/app/biblioapp/fxml/bookdialog.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setDialogStage(stage);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    void setupEach() {
        interact(() -> controller.setBook(null, false)); // Mode création
    }

    @Test
    void testEmptyTitleShowsError() {
        interact(() -> {
            controller.authorFirstNameField.setText("Victor");
            controller.authorLastNameField.setText("Hugo");
            controller.publicationYearField.setText("1862");
            controller.columnField.setText("2");
            controller.rowField.setText("3");
            controller.handleSave();
        });

        assertFalse(controller.isSaved(), "Le livre ne devrait pas être sauvegardé si le titre est vide.");
    }

    @Test
    void testValidInputCreatesBook() {
        interact(() -> {
            controller.titleField.setText("Les Misérables");
            controller.authorFirstNameField.setText("Victor");
            controller.authorLastNameField.setText("Hugo");
            controller.publicationYearField.setText("1862");
            controller.columnField.setText("2");
            controller.rowField.setText("3");
            controller.imageUrlField.setText("img.jpg");
            controller.borrowed.setSelected(false);
            controller.handleSave();
        });

        assertTrue(controller.isSaved(), "Le livre doit être sauvegardé avec des données valides.");
        Book createdBook = controller.getBook();
        assertNotNull(createdBook);
        assertEquals("Les Misérables", createdBook.getTitle());
    }
}
