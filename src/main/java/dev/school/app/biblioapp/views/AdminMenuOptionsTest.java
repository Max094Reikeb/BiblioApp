package dev.school.app.biblioapp.views;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminMenuOptionsTest {

	@Test
	public void testEnumValues() {
		AdminMenuOptions[] options = AdminMenuOptions.values();

		assertEquals(3, options.length, "Il doit y avoir 2 valeurs dans l'enum");
		assertEquals(AdminMenuOptions.LIST_BOOKS, options[0]);
		assertEquals(AdminMenuOptions.BORROWING_BOOKS, options[2]);
	}

	@Test
	public void testValueOf() {
		assertEquals(AdminMenuOptions.LIST_BOOKS, AdminMenuOptions.valueOf("LIST_BOOKS"));
	}
}
