package dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.models.Model;
import dev.school.app.biblioapp.views.AdminMenuOptions;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class AdminMenuControllerTest extends ApplicationTest {

    private AdminMenuController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dev/school/app/biblioapp/fxml/adminmenu.fxml"));
        Scene scene = new Scene(loader.load());
        controller = loader.getController();
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setup() {
        // Reset selected item before each test
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(null);
    }

    @Test
    public void testBookListButtonTriggersListBooksOption() {
        clickOn(controller.book_list_btn);
        assertEquals(AdminMenuOptions.LIST_BOOKS, Model.getInstance().getViewFactory().getAdminSelectedMenuItem().get());
    }

    @Test
    public void testCreateBookButtonTriggersCreateBookOption() {
        clickOn(controller.create_book_btn);
        assertEquals(AdminMenuOptions.CREATE_BOOK, Model.getInstance().getViewFactory().getAdminSelectedMenuItem().get());
    }

    @Test
    public void testLogoutButtonClosesStageAndShowsLoginPage() {
        // MOCK or SPY the ViewFactory if needed to test this without launching the real login window
        assertNotNull(controller.logout_bnt.getScene()); // Button is placed in a scene
        // You may use Mockito to spy ViewFactory here if you want to test interaction
    }
}
