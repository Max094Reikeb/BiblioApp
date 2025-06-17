package dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.models.Model;
import dev.school.app.biblioapp.views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {

	public Button book_list_btn;
	public Button create_book_btn;
	public Button borrowing_book_list_btn;
	public Button manage_users_btn;
	public Button logout_bnt;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addListener();
	}

	private void addListener() {
		book_list_btn.setOnAction(event -> onBooks());
		create_book_btn.setOnAction(event -> onCreateBook());
		manage_users_btn.setOnAction(event -> onManageUsers());
		logout_bnt.setOnAction(event -> onLogout());
	}

	private void onBooks() {
		Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.LIST_BOOKS);
	}

	private void onCreateBook() {
		Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE_BOOK);
	}

	private void onManageUsers() {
		Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.MANAGE_USERS);
	}

	private void onLogout() {
		Stage stage = (Stage) logout_bnt.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
		Model.getInstance().getViewFactory().showLoginPageWindow();
	}
}
