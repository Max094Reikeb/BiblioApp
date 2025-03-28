package com.biblio.bibliotheque.Controllers.Admin;

import com.biblio.bibliotheque.Models.Model;
import com.biblio.bibliotheque.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button book_list_btn;
    public Button create_book_btn;
    public Button borrowing_book_list_btn;
    public Button create_user_btn;
    public Button logout_bnt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        addListener();
    }

    private void addListener(){
        book_list_btn.setOnAction(event -> onBooks());
        create_book_btn.setOnAction(event -> onCreateBook());
        create_user_btn.setOnAction(event -> onCreateUser());

    }


    private void onBooks(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.LIST_BOOKS);
    }
    private void onCreateBook(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE_BOOK);
    }
    private void onCreateUser(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE_USER);
    }


}
