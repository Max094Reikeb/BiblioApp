package com.biblio.bibliotheque.Controllers;

import com.biblio.bibliotheque.Models.Model;
import com.biblio.bibliotheque.Views.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox<AccountType> acc_selector;
    public Label user_account_lbl;
    public TextField user_account_field;
    public TextField password_field;
    public Button login_btn;
    public Label error_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.USER, AccountType.ADMIN));
        acc_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        acc_selector.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginAccountType(acc_selector.getValue()));
        login_btn.setOnAction(event -> onLogin());
    }
    private void onLogin(){
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        if (Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.USER){
            Model.getInstance().getViewFactory().showUserWindow();
        }else{
            Model.getInstance().getViewFactory().showAdminWindow();
        }
    }
}
