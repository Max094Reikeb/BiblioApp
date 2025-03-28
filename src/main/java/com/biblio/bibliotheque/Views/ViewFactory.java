package com.biblio.bibliotheque.Views;

import com.biblio.bibliotheque.Controllers.Admin.AdminController;
import com.biblio.bibliotheque.Controllers.User.UserController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {

    private AccountType loginAccountType;


    //User views
    private final ObjectProperty<UserMenuOptions> userSelectedMenuItem;
    private AnchorPane tableView;
    private AnchorPane profileView;


    //Admin Views
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AnchorPane createUserView;
    private AnchorPane booksView;
    private AnchorPane createBookView;

    public ViewFactory() {
        this.loginAccountType = AccountType.USER;
        this.userSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    /*
    *Client Views Section
    * */
    public ObjectProperty<UserMenuOptions> getUserSelectedMenuItem() {
        return userSelectedMenuItem;
    }

    public AnchorPane getTableView() {
        if (tableView == null) {
            try {
                tableView = new FXMLLoader(getClass().getResource("/Fxml/User/UserTableView.fxml")).load();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tableView;
    }

    public AnchorPane getProfileView() {
        if (profileView == null) {
            try {
                profileView = new FXMLLoader(getClass().getResource("/Fxml/User/UserProfile.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return profileView;
    }


    public void showUserWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/User/User.fxml"));
        UserController userController = new UserController();
        loader.setController(userController);
        createStage(loader);


    }

    /*
    *Admin Views Section
    */
    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }



    public AnchorPane getBooksView() {
        if (booksView == null) {
            try {
                booksView = new FXMLLoader(getClass().getResource("/Fxml/Admin/BookList.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return booksView;
    }

    public AnchorPane getCreateBookView() {
        if (createBookView == null) {
            try{
                createBookView = new FXMLLoader(getClass().getResource("/Fxml/Admin/CreateBook.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return createBookView;
    }

    public AnchorPane getCreateUserView() {
        if (createUserView == null) {
            try {
                createUserView = new FXMLLoader(getClass().getResource("/Fxml/Admin/CreateUser.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return createUserView;
    }


    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController controller = new AdminController();
        loader.setController(controller);
        createStage(loader);
    }


    public void showLoginPageWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        }catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/stack-of-books.png"))));
        stage.setResizable(false);

        stage.setTitle("Bibliotheque");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
