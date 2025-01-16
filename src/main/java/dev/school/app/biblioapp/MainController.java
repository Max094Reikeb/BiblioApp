package dev.school.app.biblioapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainController {

	private List<Book> books = new ArrayList<>();
	private File currentFile = null;

	@FXML
	private TableView<Book> bookTable;

	@FXML
	private TableColumn<Book, String> titleColumn;

	@FXML
	private TableColumn<Book, String> authorFirstNameColumn;

	@FXML
	private TableColumn<Book, String> authorLastNameColumn;

	@FXML
	private TableColumn<Book, String> descriptionColumn;

	@FXML
	private TableColumn<Book, Integer> yearColumn;

	@FXML
	private TableColumn<Book, Double> columnColumn;

	@FXML
	private TableColumn<Book, String> rowColumn;

	@FXML
	private TableColumn<Book, String> imageColumn;

	@FXML
	private TableColumn<Book, Boolean> borrowedColumn;

	@FXML
	private TableColumn<Book, Void> actionColumn;

	@FXML
	public void initialize() {
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		authorFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("authorFirstName"));
		authorLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("authorLastName"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
		columnColumn.setCellValueFactory(new PropertyValueFactory<>("column"));
		rowColumn.setCellValueFactory(new PropertyValueFactory<>("row"));
		imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
		borrowedColumn.setCellValueFactory(new PropertyValueFactory<>("borrowed"));
	}

	@FXML
	void closeApp(ActionEvent event) {
		Stage stage = (Stage) ((javafx.scene.control.MenuItem) event.getSource())
				.getParentPopup()
				.getOwnerWindow();

		stage.close();
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
			currentFile = selectedFile; // We update the current file
			books.clear(); // We clear existing books to avoid duplication
			try {
				books.addAll(parseXML(selectedFile)); // We load books from the selected file
				bookTable.getItems().clear(); // We clear books from the bookTable
				bookTable.getItems().addAll(books); // We update the bookTable with new loaded books
				books.forEach(System.out::println);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Failed to load books from file.");
			}
		}
	}

	@FXML
	void saveFile(ActionEvent event) {
		if (currentFile != null) {
			save(currentFile);
		} else {
			saveAsFile(event);
		}
	}

	@FXML
	void saveAsFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save As");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));

		// Get the current stage
		Stage stage = (Stage) ((javafx.scene.control.MenuItem) event.getSource())
				.getParentPopup()
				.getOwnerWindow();

		File file = fileChooser.showSaveDialog(stage);

		if (file != null) {
			currentFile = file; // Update the current file
			save(file); // Save to the chosen file
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
				boolean borrowed = Boolean.parseBoolean(element.getElementsByTagName("emprunte").item(0).getTextContent());

				books.add(new Book(title, authorFirstName, authorLastName, description, publicationYear, column, row, borrowed));
			}
		}
		return books;
	}

	private void save(File file) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();

			Element root = document.createElement("bibliotheque");
			document.appendChild(root);

			for (Book book : books) {
				Element bookElement = document.createElement("livre");

				Element titleElement = document.createElement("titre");
				titleElement.appendChild(document.createTextNode(book.getTitle()));
				bookElement.appendChild(titleElement);

				Element authorElement = document.createElement("auteur");

				Element lastNameElement = document.createElement("nom");
				lastNameElement.appendChild(document.createTextNode(book.getAuthorLastName()));
				authorElement.appendChild(lastNameElement);

				Element firstNameElement = document.createElement("prenom");
				firstNameElement.appendChild(document.createTextNode(book.getAuthorFirstName()));
				authorElement.appendChild(firstNameElement);

				bookElement.appendChild(authorElement);

				Element descriptionElement = document.createElement("presentation");
				descriptionElement.appendChild(document.createTextNode(book.getDescription()));
				bookElement.appendChild(descriptionElement);

				Element yearElement = document.createElement("parution");
				yearElement.appendChild(document.createTextNode(String.valueOf(book.getPublicationYear())));
				bookElement.appendChild(yearElement);

				Element columnElement = document.createElement("colonne");
				columnElement.appendChild(document.createTextNode(String.valueOf(book.getColumn())));
				bookElement.appendChild(columnElement);

				Element rowElement = document.createElement("rangee");
				rowElement.appendChild(document.createTextNode(String.valueOf(book.getRow())));
				bookElement.appendChild(rowElement);

				Element borrowedElement = document.createElement("emprunte");
				borrowedElement.appendChild(document.createTextNode(String.valueOf(book.isBorrowed())));
				bookElement.appendChild(borrowedElement);

				root.appendChild(bookElement);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(file);
			transformer.transform(domSource, streamResult);

			System.out.println("Books saved to " + file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
