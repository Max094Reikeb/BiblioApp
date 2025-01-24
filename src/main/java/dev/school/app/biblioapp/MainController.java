package dev.school.app.biblioapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
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
import java.io.IOException;
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
		imageColumn.setCellValueFactory(new PropertyValueFactory<>("pathImage"));
		borrowedColumn.setCellValueFactory(new PropertyValueFactory<>("borrowed"));

		imageColumn.setCellFactory(column -> new TableCell<>() {
			private final ImageView imageView = new ImageView();

			@Override
			protected void updateItem(String pathImage, boolean empty) {
				super.updateItem(pathImage, empty);
				if (empty || pathImage == null || pathImage.isBlank()) {
					setGraphic(null);
				} else {
					try {
						Image image = new Image(pathImage, true); // Asynchronous loading
						imageView.setImage(image);
						imageView.setFitWidth(100); // Resize as needed
						imageView.setPreserveRatio(true);
						setGraphic(imageView);
					} catch (Exception e) {
						setGraphic(null);
					}
				}
			}
		});
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

	@FXML
	void exportToPDF(ActionEvent event) {
		if (books.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erreur lors de l'export");
			alert.setHeaderText("Aucun livre chargé !");
			alert.setContentText("Vous ne pouvez pas exporter un fichier PDF car il n'y a pas de livre chargé dans la bibliothèque.");
			alert.showAndWait();
			return;
		}

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

		// Get the current stage
		Stage stage = (Stage) ((javafx.scene.control.MenuItem) event.getSource())
				.getParentPopup()
				.getOwnerWindow();

		File file = fileChooser.showSaveDialog(stage);

		if (file != null) {
			try (PDDocument document = new PDDocument()) {
				// TODO: Title page
				PDPage titlePage = new PDPage(PDRectangle.A4);
				document.addPage(titlePage);
				try (var contentStream = new PDPageContentStream(document, titlePage)) {
					contentStream.beginText();
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
					contentStream.newLineAtOffset(100, 750);
					contentStream.showText("Library Report");
					contentStream.endText();
				}

				// TODO: Table of Contents
				PDPage tocPage = new PDPage(PDRectangle.A4);
				document.addPage(tocPage);
				try (PDPageContentStream contentStream = new PDPageContentStream(document, tocPage)) {
					contentStream.beginText();
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
					contentStream.newLineAtOffset(50, 750);
					contentStream.showText("Table of Contents");
					contentStream.setFont(PDType1Font.HELVETICA, 14);

					int yOffset = 700;
					int pageIndex = 2; // Start after TOC
					for (Book book : books) {
						if (yOffset < 50) {
							contentStream.endText();
							yOffset = 750;
							contentStream.beginText();
							contentStream.newLineAtOffset(50, yOffset);
						}
						contentStream.newLineAtOffset(0, -20);
						yOffset -= 20;
						contentStream.showText(book.getTitle() + " (Page " + pageIndex + ")");
						pageIndex++;
					}
					contentStream.endText();
				}

				// TODO: Available Books
				for (Book book : books) {
					new BookPage(document, book);
				}

				// TODO: Borrowed Books
				PDPage borrowedPage = new PDPage(PDRectangle.A4);
				document.addPage(borrowedPage);
				try (PDPageContentStream contentStream = new PDPageContentStream(document, borrowedPage)) {
					contentStream.beginText();
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
					contentStream.newLineAtOffset(50, 750);
					contentStream.showText("Borrowed Books");

					contentStream.setFont(PDType1Font.HELVETICA, 12);
					int yOffset = 700;
					contentStream.newLineAtOffset(0, -20);
					for (Book book : books.stream().filter(Book::isBorrowed).toList()) {
						if (yOffset < 50) {
							contentStream.endText();
							yOffset = 750;
							contentStream.beginText();
							contentStream.newLineAtOffset(50, yOffset);
						}
						contentStream.newLineAtOffset(0, -15);
						yOffset -= 15;
						contentStream.showText(book.getTitle() + " | " + book.getAuthorFirstName() + " " + book.getAuthorLastName() + " | " + book.getDescription() + " | " + book.getPublicationYear());
					}
					contentStream.endText();
				}

				document.save(file); // We save the PDF file
			} catch (IOException e) {
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
				String pathImage = element.getElementsByTagName("image").item(0).getTextContent();
				boolean borrowed = Boolean.parseBoolean(element.getElementsByTagName("emprunte").item(0).getTextContent());

				books.add(new Book(title, authorFirstName, authorLastName, description, publicationYear, column, row, pathImage, borrowed));
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

				Element pathImageElement = document.createElement("image");
				pathImageElement.appendChild(document.createTextNode(book.getPathImage()));
				bookElement.appendChild(pathImageElement);

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
