package dev.school.app.biblioapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AboutWindow {

	private static final Logger LOGGER = Main.getLogger();

	/**
	 * Classe type permettant d'afficher la fenêtre "À propos".
	 * Permet à l'utilisateur de voir le nom de l'application, la version en cours, et les photos des auteurs.
	 */
	public AboutWindow() {
		Stage aboutStage = new Stage();
		ResourceBundle bundle = Main.getBundle();
		aboutStage.initModality(Modality.APPLICATION_MODAL);
		aboutStage.initStyle(StageStyle.UTILITY);
		aboutStage.setTitle(bundle.getString("menu.aboutBiblioApp"));

		GridPane gridPane = new GridPane(); // Layout (en grille)
		gridPane.setHgap(20);
		gridPane.setVgap(20);
		gridPane.setPadding(new Insets(20));
		gridPane.setAlignment(Pos.CENTER);

		String[] authors = {"Maxence", "Iliès", "Olivier", "Davisson"};
		String[] photoPaths = {
				"/dev/school/app/biblioapp/photos/photo_maxence.png",
				"/dev/school/app/biblioapp/photos/photo_ilies.png",
				"/dev/school/app/biblioapp/photos/photo_olivier.png",
				"/dev/school/app/biblioapp/photos/photo_davisson.png"
		};

		VBox titleBox = new VBox();
		titleBox.setAlignment(Pos.CENTER);
		titleBox.setSpacing(10);
		titleBox.getChildren().addAll(
				new Label(bundle.getString("menu.aboutBiblioApp") + "..."),
				new Label(bundle.getString("version") + " " + Main.getVersion()),
				new Label(bundle.getString("authors") + ":")
		);

		for (int i = 0; i < authors.length; i++) {
			VBox vbox = new VBox();
			vbox.setAlignment(Pos.CENTER);
			vbox.setSpacing(10);

			ImageView imageView = new ImageView();
			try {
				Image image = new Image(AboutWindow.class.getResource(photoPaths[i]).toExternalForm());
				imageView.setImage(image);
				LOGGER.info("Loaded image for author: " + authors[i]);
			} catch (NullPointerException | IllegalArgumentException e) {
				LOGGER.log(Level.SEVERE, "Error loading image: " + e.getMessage(), e);
				imageView.setImage(new Image("/dev/school/app/biblioapp/photos/placeholder.png")); // Fallback placeholder
			}

			imageView.setFitWidth(100);
			imageView.setFitHeight(100);
			imageView.setPreserveRatio(true);

			Label nameLabel = new Label(authors[i]);

			vbox.getChildren().addAll(imageView, nameLabel);

			int row = i / 2;
			int col = i % 2;
			gridPane.add(vbox, col, row);
		}

		VBox fullBox = new VBox();
		fullBox.setAlignment(Pos.CENTER);
		fullBox.setSpacing(10);
		fullBox.getChildren().addAll(titleBox, gridPane);

		Scene aboutScene = new Scene(fullBox);
		aboutStage.setScene(aboutScene);
		aboutStage.showAndWait();
	}
}
