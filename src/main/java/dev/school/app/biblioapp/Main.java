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

public class Main extends Application {

	public static void main(String[] args) {
		String os = System.getProperty("os.name");
		if (os.contains("Mac") || os.contains("OS X")) {
			System.setProperty("apple.awt.application.name", "BiblioApp");
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		launch();
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));

		BorderPane root = new BorderPane();
		root.setCenter(fxmlLoader.load());

		MainController controller = fxmlLoader.getController();
		MenuBar menuBar = new MenuBar();

		Menu fileMenu = new Menu("Fichier");
		MenuItem openItem = new MenuItem("Ouvrir...");
		openItem.setOnAction(controller::openFile);
		MenuItem exportItem = new MenuItem("Exporter");
		exportItem.setOnAction((event) -> System.out.print(""));
		MenuItem closeItem = new MenuItem("Quitter");
		closeItem.setOnAction(controller::closeApp);
		fileMenu.getItems().addAll(openItem, closeItem);

		Menu editMenu = new Menu("Édition");
		MenuItem saveItem = new MenuItem("Sauvegarder");
		saveItem.setOnAction(controller::saveFile);
		MenuItem saveAsItem = new MenuItem("Sauvegarder sous...");
		saveAsItem.setOnAction(controller::saveAsFile);
		editMenu.getItems().addAll(saveItem, saveAsItem);

		Menu aboutMenu = new Menu("À propos");
		MenuItem aboutItem = new MenuItem("À propos de BiblioApp");
		aboutItem.setOnAction(e -> AboutWindow.showAboutWindow());
		aboutMenu.getItems().add(aboutItem);

		menuBar.getMenus().addAll(fileMenu, editMenu, aboutMenu);
		root.setTop(menuBar);

		Scene scene = new Scene(root, 1168, 602);
		stage.setTitle("BiblioApp");
		stage.setScene(scene);
		stage.show();
	}
}
