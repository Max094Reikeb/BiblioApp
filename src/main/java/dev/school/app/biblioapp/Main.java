package dev.school.app.biblioapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
			FileHandler fileHandler = new FileHandler("biblio.log", true);
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
		loadLanguage("fr");
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
	 * @throws IOException Exception retournée en cas de problème.
	 */
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
		fxmlLoader.setResources(bundle);

		BorderPane root = new BorderPane();
		root.setCenter(fxmlLoader.load());

		MainController controller = fxmlLoader.getController();
		MenuBar menuBar = new MenuBar();

		Menu fileMenu = new Menu(bundle.getString("menu.file"));
		MenuItem openItem = new MenuItem(bundle.getString("menu.open"));
		openItem.setOnAction(controller::openFile);
		MenuItem exportItem = new MenuItem(bundle.getString("menu.export"));
		exportItem.setOnAction(controller::exportToPDF);
		MenuItem closeItem = new MenuItem(bundle.getString("menu.quit"));
		closeItem.setOnAction(controller::closeApp);
		fileMenu.getItems().addAll(openItem, exportItem, closeItem);

		Menu editMenu = new Menu(bundle.getString("menu.edit"));
		MenuItem saveItem = new MenuItem(bundle.getString("menu.save"));
		saveItem.setOnAction(controller::saveFile);
		MenuItem saveAsItem = new MenuItem(bundle.getString("menu.saveAs"));
		saveAsItem.setOnAction(controller::saveAsFile);
		editMenu.getItems().addAll(saveItem, saveAsItem);

		Menu aboutMenu = new Menu(bundle.getString("menu.about"));
		MenuItem aboutItem = new MenuItem(bundle.getString("menu.aboutBiblioApp"));
		aboutItem.setOnAction(e -> new AboutWindow());
		aboutMenu.getItems().add(aboutItem);

		menuBar.getMenus().addAll(fileMenu, editMenu, aboutMenu);
		root.setTop(menuBar);

		Scene scene = new Scene(root, 1168, 602);
		scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}
