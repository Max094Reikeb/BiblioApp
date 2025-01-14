package dev.school.app.biblioapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
		aboutItem.setOnAction(e -> showAboutWindow());
		aboutMenu.getItems().add(aboutItem);

		menuBar.getMenus().addAll(fileMenu, editMenu, aboutMenu);
		root.setTop(menuBar);

		Scene scene = new Scene(root, 1168, 602);
		stage.setTitle("BiblioApp");
		stage.setScene(scene);
		stage.show();
	}

	private void showAboutWindow() {
		Stage aboutStage = new Stage();
		aboutStage.initModality(Modality.APPLICATION_MODAL);
		aboutStage.initStyle(StageStyle.UTILITY);
		aboutStage.setTitle("À propos de BiblioApp");

		VBox aboutBox = new VBox(10);
		aboutBox.setStyle("-fx-padding: 10;");
		aboutBox.getChildren().addAll(
				new Label("BiblioApp"),
				new Label("Version: 1.0"),
				new Label("Auteurs: Maxence, ...")
		);

		Scene aboutScene = new Scene(aboutBox);
		aboutStage.setScene(aboutScene);
		aboutStage.showAndWait();
	}
}
