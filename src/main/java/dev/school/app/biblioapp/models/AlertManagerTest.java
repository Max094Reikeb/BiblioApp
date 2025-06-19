package main.java.dev.school.app.biblioapp.models;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AlertManagerTest extends ApplicationTest {

    @BeforeAll
    static void initJFX() throws Exception {
        // Initialize JavaFX toolkit
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new IllegalStateException("JavaFX platform failed to start.");
        }
    }

    @Test
    void testShowAlertWithoutHandler() {
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> AlertManager.showAlert(
                Alert.AlertType.INFORMATION,
                "Test Title",
                "Test Header",
                "Test Content"
            ));
        });
    }

    @Test
    void testShowAlertWithHandler() {
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> AlertManager.showAlert(
                Alert.AlertType.CONFIRMATION,
                "Test Confirm",
                "Are you sure?",
                "Click OK or Cancel",
                response -> assertNotNull(response),
                ButtonType.OK, ButtonType.CANCEL
            ));
        });
    }

    @Test
    void testShowError() {
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> AlertManager.showError("global.error.unexpected"));
        });
    }
}
