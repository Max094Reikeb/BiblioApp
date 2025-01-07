module dev.school.app.biblioapp {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.xml;


	opens dev.school.app.biblioapp to javafx.fxml;
	exports dev.school.app.biblioapp;
}