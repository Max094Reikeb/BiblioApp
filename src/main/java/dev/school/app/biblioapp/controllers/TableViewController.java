package dev.school.app.biblioapp.controllers;

import dev.school.app.biblioapp.Main;
import dev.school.app.biblioapp.models.Book;
import dev.school.app.biblioapp.views.AboutWindow;
import dev.school.app.biblioapp.views.BookPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableViewController implements Initializable {

	private static final Logger LOGGER = Main.getLogger();
	private final ResourceBundle bundle = Main.getBundle();

	private List<Book> books = new ArrayList<>();
	private File currentFile = null;

	@FXML
	private MenuItem openMenuItem;
	@FXML
	private MenuItem exportMenuItem;
	@FXML
	private MenuItem quitMenuItem;

	@FXML
	private MenuItem saveMenuItem;
	@FXML
	private MenuItem saveAsMenuItem;

	@FXML
	private MenuItem aboutMenuItem;

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

	/**
	 * Fonction principale se lançant lors de l'initialisation du controller.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		openMenuItem.setOnAction(this::openFile);
		exportMenuItem.setOnAction(this::exportToPDF);
		quitMenuItem.setOnAction(this::closeApp);
		saveMenuItem.setOnAction(this::saveFile);
		saveAsMenuItem.setOnAction(this::saveAsFile);
		aboutMenuItem.setOnAction(e -> new AboutWindow());

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
						Image image = new Image(pathImage, true);
						imageView.setImage(image);
						imageView.setFitWidth(100);
						imageView.setPreserveRatio(true);
						setGraphic(imageView);
					} catch (Exception e) {
						setGraphic(null);
					}
				}
			}
		});
	}

	/**
	 * Fonction s'exécutant lorsque l'utilisateur veut fermer l'application. (via le bouton "Quitter")
	 *
	 * @param event Évènement / Action utilisateur.
	 */
	@FXML
	void closeApp(ActionEvent event) {
		Stage stage = (Stage) ((javafx.scene.control.MenuItem) event.getSource())
				.getParentPopup()
				.getOwnerWindow();

		LOGGER.info("Closing app...");
		stage.close();
	}

	/**
	 * Fonction s'exécutant lorsque l'utilisateur veut ouvrir un fichier XML contenant des livres. (via le bouton "Ouvrir")
	 *
	 * @param event Évènement / Action utilisateur.
	 */
	@FXML
	void openFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(bundle.getString("filechooser.open"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
		LOGGER.info("Opening file...");

		Stage stage = (Stage) ((javafx.scene.control.MenuItem) event.getSource())
				.getParentPopup()
				.getOwnerWindow();

		File selectedFile = fileChooser.showOpenDialog(stage);
		LOGGER.info("Opening file: " + selectedFile);

		if (selectedFile != null) {
			currentFile = selectedFile; // On met à jour le fichier actuel
			books.clear(); // On clear books pour éviter de la duplication
			try {
				books.addAll(parseXML(selectedFile)); // On load les livres depuis le fichier sélectionné
				bookTable.getItems().clear(); // On clear books depuis la bookTable pour aussi éviter une dupplication
				bookTable.getItems().addAll(books); // On update la bookTable avec les nouveaux livres
				LOGGER.info("Loaded " + books.size() + " books");
				books.forEach(book -> LOGGER.info("Book loaded: " + book));
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Failed to load books from file: " + selectedFile, e);
			}
		} else {
			LOGGER.log(Level.SEVERE, "No file selected");
		}
	}

	/**
	 * Fonction s'exécutant lorsque l'utilisateur veut sauvegarder la bibliothèque. (via le bouton "Sauvegarder")
	 *
	 * @param event Évènement / Action utilisateur.
	 */
	@FXML
	void saveFile(ActionEvent event) {
		if (currentFile != null) {
			save(currentFile);
		} else {
			saveAsFile(event);
		}
	}

	/**
	 * Fonction s'exécutant lorsque l'utilisateur veut sauvegarder la bibliothèqye dans un nouveau fichier XML. (via le bouton "Sauvegarder sous...")
	 *
	 * @param event Évènement / Action utilisateur.
	 */
	@FXML
	void saveAsFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(bundle.getString("filechooser.saveAs"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));

		Stage stage = (Stage) ((javafx.scene.control.MenuItem) event.getSource())
				.getParentPopup()
				.getOwnerWindow();

		File file = fileChooser.showSaveDialog(stage);
		LOGGER.info("Saving in file: " + file);

		if (file != null) {
			currentFile = file; // On update le fichier actuel
			save(file); // On sauvegarde vers le fichier choisi
		} else {
			LOGGER.log(Level.SEVERE, "No file selected");
		}
	}

	/**
	 * Fonction s'exécutant lorsque l'utilisateur veut exporter la bibliothèque en un fichier PDF. (via le bouton "Exporter")
	 *
	 * @param event Évènement / Action utilisateur.
	 */
	@FXML
	void exportToPDF(ActionEvent event) {
		if (books.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle(bundle.getString("pdfExport.error.title"));
			alert.setHeaderText(bundle.getString("pdfExport.error.header"));
			alert.setContentText(bundle.getString("pdfExport.error.message"));
			LOGGER.info("Empty book list, cannot export to PDF!");
			alert.showAndWait();
			return;
		}

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(bundle.getString("filechooser.exportPDF"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

		Stage stage = (Stage) ((javafx.scene.control.MenuItem) event.getSource())
				.getParentPopup()
				.getOwnerWindow();

		File file = fileChooser.showSaveDialog(stage);
		LOGGER.info("Exporting to PDF: " + file);

		if (file != null) {
			try (PDDocument document = new PDDocument()) {
				// TODO: Page de couverture
				PDPage titlePage = new PDPage(PDRectangle.A4);
				document.addPage(titlePage);
				try (var contentStream = new PDPageContentStream(document, titlePage)) {
					contentStream.beginText();
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
					contentStream.newLineAtOffset(100, 750);
					contentStream.showText(bundle.getString("pdfExport.titlePage.title"));
					contentStream.endText();
				}

				// TODO: Page sommaire avec liens cliquables
				PDPage tocPage = new PDPage(PDRectangle.A4);
				document.addPage(tocPage);
				try (PDPageContentStream contentStream = new PDPageContentStream(document, tocPage)) {
					contentStream.beginText();
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
					contentStream.newLineAtOffset(50, 750);
					contentStream.showText(bundle.getString("pdfExport.tocPage.title"));
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

				for (Book book : books) {
					new BookPage(document, book); // Une page par livre
				}

				// TODO: Page avec tableau de livres empruntés
				PDPage borrowedPage = new PDPage(PDRectangle.A4);
				document.addPage(borrowedPage);
				try (PDPageContentStream contentStream = new PDPageContentStream(document, borrowedPage)) {
					contentStream.beginText();
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
					contentStream.newLineAtOffset(50, 750);
					contentStream.showText(bundle.getString("pdfExport.borrowedPage.title"));

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

				document.save(file); // On sauvegarde le document PDF
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Failed to export to PDF: " + e.getMessage(), e);
			}
		} else {
			LOGGER.log(Level.SEVERE, "No file selected");
		}
	}

	/**
	 * Fonction permettant de parser un fichier XML en une liste de @Book.
	 *
	 * @param file Fichier XML à parser
	 * @return Une liste de @Book
	 * @throws Exception Exception retournée si le fichier XML ne contient pas de livres valides.
	 */
	private List<Book> parseXML(File file) throws Exception {
		List<Book> books = new ArrayList<>();
		LOGGER.info("Parsing XML file: " + file + " into java objects...");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(file);

		document.getDocumentElement().normalize(); // On normalize la structure XML

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
		LOGGER.info("Parsing complete!");
		return books;
	}

	/**
	 * Fonction permettant de sauvegarder les livres chargés dans la bibliothèque dans un fichier XML.
	 *
	 * @param file Le fichier XML de sauvegarde.
	 */
	private void save(File file) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			LOGGER.info("Saving books into XML file: " + file);

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

			LOGGER.info("Books saved to " + file.getAbsolutePath());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "There was an issue while saving the books: " + e.getMessage(), e);
		}
	}
}
