package dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.models.Model;
import dev.school.app.biblioapp.views.AdminMenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {

	@FXML
	public Button book_list_btn;
	@FXML
	public Button create_book_btn;
	@FXML
	public Button borrowing_book_list_btn;
	@FXML
	public Button manage_users_btn;
	@FXML
	public Button logout_bnt;

	/**
	 * Fonction principale se lançant lors de l'initialisation du controller.
	 *
	 * @param location  l'URL de l'objet root object, ou null si aucun emplacement n'est spécifié.
	 * @param resources le ResourceBundle permettant de traduire l'objet root, ou null si aucun bundle n'est spécifié.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addListener();
	}

	/**
	 * Attache les event handlers aux boutons correspondents dans le controller.
	 */
	private void addListener() {
		book_list_btn.setOnAction(event -> onBooks());
		create_book_btn.setOnAction(event -> onCreateBook());
		manage_users_btn.setOnAction(event -> onManageUsers());
		logout_bnt.setOnAction(event -> onLogout());
	}

	/**
	 * Gère l'action de sélection pour la section "Livres" dans la sidebar.
	 */
	private void onBooks() {
		Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.LIST_BOOKS);
	}

	/**
	 * Gère l'action de sélection pour la section "Ajouter un livre" dans la sidebar.
	 */
	private void onCreateBook() {
		Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE_BOOK);
	}

	/**
	 * Gère l'action de sélection pour la section "Gérer les utilisateurs" dans la sidebar.
	 */
	private void onManageUsers() {
		Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.MANAGE_USERS);
	}

	/**
	 * Gère l'action de déconnexion de l'utilisateur.
	 */
	private void onLogout() {
		Stage stage = (Stage) logout_bnt.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
		Model.getInstance().getViewFactory().showLoginPageWindow();
	}
}
