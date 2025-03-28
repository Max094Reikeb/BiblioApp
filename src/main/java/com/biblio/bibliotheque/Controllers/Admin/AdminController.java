package com.biblio.bibliotheque.Controllers.Admin;

import com.biblio.bibliotheque.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            //Add Switch statement
            switch (newVal){
                case CREATE_USER -> admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateUserView());
                case CREATE_BOOK -> admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateBookView());
                default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getBooksView());
            }
        });
    }
}
