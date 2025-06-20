package dev.school.app.biblioapp.views;

import dev.school.app.biblioapp.Main;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewFactory {

	private static final Logger LOGGER = Main.getLogger();

	private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
	private AnchorPane tableView;
	private AnchorPane usersTableView;

	public ViewFactory() {
		this.adminSelectedMenuItem = new SimpleObjectProperty<>();
	}

	public AnchorPane getTableView() {
		if (tableView == null) {
			try {
				FXMLLoader tableViewLoader = new FXMLLoader(getClass().getResource("/dev/school/app/biblioapp/fxml/tableview.fxml"));
				tableViewLoader.setResources(getBundle());
				tableView = tableViewLoader.load();
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return tableView;
	}

	public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
		return adminSelectedMenuItem;
	}

	public AnchorPane getUsersTableWindow() {
		if (usersTableView == null) {
			try {
				FXMLLoader usersTableLoader = new FXMLLoader(getClass().getResource("/dev/school/app/biblioapp/fxml/userview.fxml"));
				usersTableLoader.setResources(getBundle());
				usersTableView = usersTableLoader.load();
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return usersTableView;
	}

	public void showUserWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/dev/school/app/biblioapp/fxml/tableview.fxml"));
		loader.setResources(getBundle());
		createStage(loader);
	}

	public void showAdminWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/dev/school/app/biblioapp/fxml/admin.fxml"));
		loader.setResources(getBundle());
		createStage(loader);
	}

	public void showLoginPageWindow() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/dev/school/app/biblioapp/fxml/login.fxml"));
		loader.setResources(getBundle());
		createStage(loader);
	}

	private void createStage(FXMLLoader loader) {
		Scene scene;
		try {
			scene = new Scene(loader.load(), 1740, 750);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/dev/school/app/biblioapp/images/biblioapp.png"))));
			stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/dev/school/app/biblioapp/images/biblioapp.icns"))));
			stage.setResizable(true);
			stage.setTitle(getBundle().getString("app.title"));
			stage.show();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public void closeStage(Stage stage) {
		stage.close();
	}

	private ResourceBundle getBundle() {
		return Main.getBundle();
	}
}
