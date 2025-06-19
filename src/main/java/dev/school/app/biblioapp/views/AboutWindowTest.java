package main.java.dev.school.app.biblioapp.views;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class AboutWindowTest extends ApplicationTest {

    private AboutWindow aboutWindow;

    @Override
    public void start(Stage stage) {
        // Ne pas utiliser le Stage passé car AboutWindow crée sa propre fenêtre modale
    }

    @Test
    public void testAboutWindowCreation() {
        // On vérifie que la création ne lève pas d’exception
        assertDoesNotThrow(() -> aboutWindow = new AboutWindow());
    }
}
