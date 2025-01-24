module dev.school.app.biblioapp {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.xml;
	requires java.logging;
	requires org.apache.pdfbox;


	opens dev.school.app.biblioapp to javafx.fxml;
	exports dev.school.app.biblioapp;
}