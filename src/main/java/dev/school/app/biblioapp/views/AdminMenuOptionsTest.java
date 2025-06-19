package main.java.dev.school.app.biblioapp.views;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdminMenuOptionsTest {

    @Test
    public void testEnumValues() {
        AdminMenuOptions[] options = AdminMenuOptions.values();

        assertEquals(3, options.length, "Le nombre de valeurs de l'enum doit être 3");
        assertEquals(AdminMenuOptions.LIST_BOOKS, options[0]);
        assertEquals(AdminMenuOptions.BORROWING_BOOKS, options[2]);
    }

    @Test
    public void testValueOf() {
        assertEquals(AdminMenuOptions.CREATE_BOOK, AdminMenuOptions.valueOf("CREATE_BOOK"));
    }
}
