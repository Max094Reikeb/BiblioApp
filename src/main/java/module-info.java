open module dev.school.app.biblioapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.logging;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires de.jensd.fx.glyphs.fontawesome;
    requires transitive javafx.base;

    opens dev.school.app.biblioapp to javafx.fxml;
    opens dev.school.app.biblioapp.controllers to javafx.fxml;

    exports dev.school.app.biblioapp.controllers;
    exports dev.school.app.biblioapp.views;
    exports dev.school.app.biblioapp.models;
    exports dev.school.app.biblioapp;
}
