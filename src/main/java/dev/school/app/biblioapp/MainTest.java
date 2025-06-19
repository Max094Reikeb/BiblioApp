package main.java.dev.school.app.biblioapp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    public void testGetVersion() {
        String version = Main.getVersion();
        assertNotNull(version);
        assertEquals("1.0", version);
    }

    @Test
    public void testLoadLanguageAndGetBundle() {
        // Charger le bundle en français
        Main.loadLanguage("fr");
        ResourceBundle bundleFr = Main.getBundle();
        assertNotNull(bundleFr);

        // On s'assure que la clé "app.title" existe dans le bundle fr (à adapter selon ton fichier messages_fr.properties)
        assertTrue(bundleFr.containsKey("app.title"));

        // Charger le bundle en anglais (si tu as les fichiers)
        Main.loadLanguage("en");
        ResourceBundle bundleEn = Main.getBundle();
        assertNotNull(bundleEn);
        assertTrue(bundleEn.containsKey("app.title"));

        // On peut comparer que les bundles ne sont pas forcément égaux
        assertNotEquals(bundleFr, bundleEn);
    }

    @Test
    public void testGetLogger() {
        Logger logger = Main.getLogger();
        assertNotNull(logger);

        // Vérifier que le niveau est ALL
        assertEquals(java.util.logging.Level.ALL, logger.getLevel());
    }
}
