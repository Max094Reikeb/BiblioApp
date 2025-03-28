module com.biblio.bibliotheque {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.desktop;

    opens com.biblio.bibliotheque to javafx.fxml;
    exports com.biblio.bibliotheque;
    exports com.biblio.bibliotheque.Controllers;
    exports com.biblio.bibliotheque.Controllers.Admin;
    exports com.biblio.bibliotheque.Controllers.User;
    exports com.biblio.bibliotheque.Models;
    exports com.biblio.bibliotheque.Views;


}