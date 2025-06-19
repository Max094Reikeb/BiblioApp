package dev.school.app.biblioapp.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

	private User user;

	@BeforeEach
	void setUp() {
		user = new User("john_doe", "securePass123", true);
	}

	@Test
	void testUsernameGetter() {
		assertEquals("john_doe", user.getUsername());
	}

	@Test
	void testPasswordGetter() {
		assertEquals("securePass123", user.getPassword());
	}

	@Test
	void testIsAdminGetter() {
		assertTrue(user.isAdmin());
	}

	@Test
	void testUsernameProperty() {
		assertEquals("john_doe", user.usernameProperty().get());
	}

	@Test
	void testPasswordProperty() {
		assertEquals("securePass123", user.passwordProperty().get());
	}

	@Test
	void testIsAdminProperty() {
		assertTrue(user.isAdminProperty().get());
	}

	@Test
	void testToString() {
		String expected = "User{username='john_doe', password='securePass123', isAdmin=true}";
		assertEquals(expected, user.toString());
	}
}
