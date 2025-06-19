package dev.school.app.biblioapp.views;

import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ViewFactoryTest {

	@Test
	public void testAdminSelectedMenuItemIsNotNull() {
		ViewFactory viewFactory = new ViewFactory();
		assertNotNull(viewFactory.getAdminSelectedMenuItem(), "La propriété adminSelectedMenuItem ne doit pas être nulle");
	}

	@Test
	public void testGetTableViewLoadsFXML() {
		ViewFactory viewFactory = new ViewFactory();
		AnchorPane tableView = viewFactory.getTableView();
		assertNotNull(tableView, "Le tableView doit être chargé");
	}

	@Test
	public void testCloseStage() {
		// On peut juste vérifier qu'on peut appeler closeStage sans erreur
		ViewFactory viewFactory = new ViewFactory();
		// Pas de Stage réel pour un test simple, mais on vérifie qu'on peut appeler la méthode sans exception
		assertDoesNotThrow(() -> viewFactory.closeStage(new javafx.stage.Stage()));
	}
}
