package dev.school.app.biblioapp;

import dev.school.app.biblioapp.models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe principale du programme.
 * Contient les fonctions les plus importantes et chargeant la fenêtre principale.
 */
public class Main extends Application {

	/**
	 * VERSION est la version actuelle de l'application.
	 * La stocker dans un string fixe, privé et final au début de la classe est plus sûr et plus facile pour de futurs changements.
	 */
	private static final String VERSION = "1.0";

	/**
	 * LOGGER de l'application.
	 * Permet de suivre à la trace tout changement de l'application.
	 */
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	private static ResourceBundle bundle;

	/**
	 * Permet d'enregistrer les logs dans un fichier .log et lié au Logger de l'application.
	 */
	static {
		try {
			File logDir = new File("logs");
			if (!logDir.exists())
				logDir.mkdirs();

			String fileName = "logs/biblio-" + System.currentTimeMillis() + ".log"; // Nom de fichier de log
			FileHandler fileHandler = new FileHandler(fileName, true);
			fileHandler.setLevel(Level.ALL);
			fileHandler.setFormatter(new java.util.logging.SimpleFormatter());

			ConsoleHandler consoleHandler = new ConsoleHandler();
			consoleHandler.setLevel(Level.ALL);

			LOGGER.addHandler(fileHandler);
			LOGGER.addHandler(consoleHandler);

			LOGGER.setUseParentHandlers(false);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to initialize logger handlers", e);
		}

		LOGGER.setLevel(Level.ALL);
	}

	/**
	 * Fonction principale lancée avec le programme.
	 *
	 * @param args Arguments du programme.
	 */
	public static void main(String[] args) {
		String os = System.getProperty("os.name");
		if (os.contains("Mac") || os.contains("OS X")) {
			System.setProperty("apple.awt.application.name", "BiblioApp");
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		loadLanguage("fr"); // Langue de l'app par défaut (fr = français)
		launch();
	}

	/**
	 * Ouvre un langage en fonction du code rentré en paramètre.
	 *
	 * @param languageCode Code de langage (ex: fr, es, ...)
	 */
	public static void loadLanguage(String languageCode) {
		Locale locale = new Locale(languageCode);
		bundle = ResourceBundle.getBundle("messages", locale);
	}

	/**
	 * Getter public du bundle de l'application.
	 *
	 * @return Le bundle de l'application.
	 */
	public static ResourceBundle getBundle() {
		return bundle;
	}

	/**
	 * Getter public de la veersion actuelle de l'applicqtion.
	 *
	 * @return Version actuelle de l'application.
	 */
	public static String getVersion() {
		return VERSION;
	}

	/**
	 * Getter public du Logger de l'application.
	 *
	 * @return Logger de l'application.
	 */
	public static Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Fonction lancée avec la fenêtre de l'application.
	 *
	 * @param stage Fenêtre de l'application.
	 */
	@Override
	public void start(Stage stage) {
		Model.getInstance().getViewFactory().showAdminWindow();
	}
}
