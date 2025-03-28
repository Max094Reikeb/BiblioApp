package com.biblio.bibliotheque.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable {
    public TextField FName_fld;
    public TextField LName_fld;
    public TextField email_fld;
    public TextField password_fld;
    public Button create_user_btn;
    public Label error_lbl;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
