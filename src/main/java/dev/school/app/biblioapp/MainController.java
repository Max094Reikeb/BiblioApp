package dev.school.app.biblioapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainController {

	private List<Book> books = new ArrayList<>();

	@FXML
	private MenuItem closeButton;

	@FXML
	private MenuItem openFileButton;

	@FXML
	private Font x1;

	@FXML
	private Color x2;

	@FXML
	private Font x3;

	@FXML
	private Color x4;

	@FXML
	void closeApp(ActionEvent event) {
	}

	@FXML
	void openFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open File");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));

		Stage stage = (Stage) ((javafx.scene.control.MenuItem) event.getSource())
				.getParentPopup()
				.getOwnerWindow();

		File selectedFile = fileChooser.showOpenDialog(stage);

		if (selectedFile != null) {
			try {
				List<Book> newBooks = parseXML(selectedFile); // We parse XML
				books.clear(); // We clear the global list
				books.addAll(newBooks); // We add new books to the global list
				books.forEach(System.out::println);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private List<Book> parseXML(File file) throws Exception {
		List<Book> books = new ArrayList<>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(file);

		document.getDocumentElement().normalize(); // We normalize the XML structure

		NodeList nodeList = document.getElementsByTagName("livre");

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;

				String title = element.getElementsByTagName("titre").item(0).getTextContent();
				String authorLastName = element.getElementsByTagName("nom").item(0).getTextContent();
				String authorFirstName = element.getElementsByTagName("prenom").item(0).getTextContent();
				String description = element.getElementsByTagName("presentation").item(0).getTextContent();
				int publicationYear = Integer.parseInt(element.getElementsByTagName("parution").item(0).getTextContent());
				int column = Integer.parseInt(element.getElementsByTagName("colonne").item(0).getTextContent());
				int row = Integer.parseInt(element.getElementsByTagName("rangee").item(0).getTextContent());

				books.add(new Book(title, authorFirstName, authorLastName, description, publicationYear, column, row));
			}
		}
		return books;
	}
}
