package dev.school.app.biblioapp.views;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminMenuOptionsTest {

    @Test
    void testEnumValuesExist() {
        assertNotNull(AdminMenuOptions.valueOf("LIST_BOOKS"));
        assertNotNull(AdminMenuOptions.valueOf("BORROWING_BOOKS"));
        assertNotNull(AdminMenuOptions.valueOf("MANAGE_USERS"));
    }

    @Test
    void testEnumValuesCount() {
        assertEquals(3, AdminMenuOptions.values().length, "L'enum doit contenir exactement 3 éléments.");
    }

    @Test
    void testEnumOrder() {
        AdminMenuOptions[] expectedOrder = {
                AdminMenuOptions.LIST_BOOKS,
                AdminMenuOptions.BORROWING_BOOKS,
                AdminMenuOptions.MANAGE_USERS
        };
        assertArrayEquals(expectedOrder, AdminMenuOptions.values(), "L'ordre des éléments dans l'enum doit être respecté.");
    }
}
