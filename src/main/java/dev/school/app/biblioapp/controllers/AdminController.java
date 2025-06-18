package dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.Main;
import dev.school.app.biblioapp.models.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminController implements Initializable {

	private static final Logger LOGGER = Main.getLogger();
	private final ResourceBundle bundle = Main.getBundle();

	@FXML
	private BorderPane admin_parent;
	@FXML
	private AnchorPane left_container;
	@FXML
	private AnchorPane center_container;

	/**
	 * Fonction principale se lançant lors de l'initialisation du controller.
	 *
	 * @param url            l'URL de l'objet root object, ou null si aucun emplacement n'est spécifié.
	 * @param resourceBundle le ResourceBundle permettant de traduire l'objet root, ou null si aucun bundle n'est spécifié.
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/dev/school/app/biblioapp/fxml/adminmenu.fxml"));
			menuLoader.setResources(bundle);
			Parent menu = menuLoader.load();
			left_container.getChildren().setAll(menu);
			AnchorPane.setTopAnchor(menu, 0.0);
			AnchorPane.setBottomAnchor(menu, 0.0);
			AnchorPane.setLeftAnchor(menu, 0.0);
			AnchorPane.setRightAnchor(menu, 0.0);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}

		Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
			AnchorPane view = switch (newVal) {
				case MANAGE_USERS -> Model.getInstance().getViewFactory().getUsersTableWindow();
				default -> Model.getInstance().getViewFactory().getTableView();
			};
			center_container.getChildren().setAll(view);
			AnchorPane.setTopAnchor(view, 0.0);
			AnchorPane.setBottomAnchor(view, 0.0);
			AnchorPane.setLeftAnchor(view, 0.0);
			AnchorPane.setRightAnchor(view, 0.0);
		});

		AnchorPane view = Model.getInstance().getViewFactory().getTableView();
		center_container.getChildren().setAll(view);
		AnchorPane.setTopAnchor(view, 0.0);
		AnchorPane.setBottomAnchor(view, 0.0);
		AnchorPane.setLeftAnchor(view, 0.0);
		AnchorPane.setRightAnchor(view, 0.0);
	}
}
