package com.biblio.bibliotheque.Controllers.User;

import com.biblio.bibliotheque.Models.Model;
import com.biblio.bibliotheque.Views.UserMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable {
    public Button books_bnt_list;
    public Button profile_bnt;
    public Button logout_bnt;
    public Button report_bnt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }


    private void addListeners() {
        books_bnt_list.setOnAction(event -> onTableView());
        profile_bnt.setOnAction(event -> onProfileView());
    }

    private void onTableView() {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(UserMenuOptions.TABLEVIEW);
    }

    private void onProfileView() {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(UserMenuOptions.PROFILE);
    }

}
