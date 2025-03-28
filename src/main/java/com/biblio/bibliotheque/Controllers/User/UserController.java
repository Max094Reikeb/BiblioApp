package com.biblio.bibliotheque.Controllers.User;

import com.biblio.bibliotheque.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    public BorderPane user_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal){
                case PROFILE -> user_parent.setCenter(Model.getInstance().getViewFactory().getProfileView());
                default -> user_parent.setCenter(Model.getInstance().getViewFactory().getTableView());
            }
        });
    }
}
