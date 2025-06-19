package dev.school.app.biblioapp.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dev.school.app.biblioapp.Main;
import dev.school.app.biblioapp.models.AlertManager;
import dev.school.app.biblioapp.models.Book;
import dev.school.app.biblioapp.models.Model;
import dev.school.app.biblioapp.models.User;
import dev.school.app.biblioapp.views.AboutWindow;
import dev.school.app.biblioapp.views.BookPage;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitDestination;
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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableViewController implements Initializable {

	private static final Logger LOGGER = Main.getLogger();
	private final ResourceBundle bundle = Main.getBundle();

	private List<Book> books;
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

	@FXML
	private TableColumn<Book, Void> editColumn;

	@FXML
	private TableColumn<Book, Void> deleteColumn;

	/**
	 * Fonction principale se lançant lors de l'initialisation du controller.
	 *
	 * @param location  l'URL de l'objet root object, ou null si aucun emplacement n'est spécifié.
	 * @param resources le ResourceBundle permettant de traduire l'objet root, ou null si aucun bundle n'est spécifié.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		books = Model.getInstance().getBooks();
		User currentUser = Model.getInstance().getCurrentUser();

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

		borrowedColumn.setCellValueFactory(cellData ->
				new SimpleBooleanProperty(!cellData.getValue().isBorrowed()));

		borrowedColumn.setCellFactory(column -> new TableCell<>() {
			@Override
			protected void updateItem(Boolean isAvailable, boolean empty) {
				super.updateItem(isAvailable, empty);
				if (empty || isAvailable == null) {
					setText(null);
				} else {
					setText(isAvailable ? bundle.getString("book.status.available") : bundle.getString("book.status.borrowed"));
				}
			}
		});


		if (currentUser == null || !currentUser.isAdmin()) {
			editColumn.setVisible(false);
			deleteColumn.setVisible(false);
		}
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

		editColumn.setCellFactory(column -> new TableCell<>() {
			private final Button editButton = new Button();

			{
				FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
				editIcon.setGlyphSize(16);
				editIcon.getStyleClass().add("edit-icon");

				editButton.setGraphic(editIcon);
				editButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

				editButton.setOnAction(e -> {
					Book bookToEdit = getTableView().getItems().get(getIndex());
					onEditBook(bookToEdit);
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(editButton);
				}
			}
		});

		deleteColumn.setCellFactory(column -> new TableCell<>() {
			private final Button deleteButton = new Button();

			{
				FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
				icon.setGlyphSize(16);
				icon.getStyleClass().add("delete-icon");

				deleteButton.setGraphic(icon);
				deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

				deleteButton.setOnAction(e -> {
					Book book = getTableView().getItems().get(getIndex());

					AlertManager.showAlert(Alert.AlertType.CONFIRMATION,
							bundle.getString("global.delete.title"), bundle.getString("global.delete.header"),
							MessageFormat.format(bundle.getString("book.delete.message"), book.getTitle()),
							response -> {
								if (response == ButtonType.OK) {
									bookTable.getItems().remove(book);
									books.remove(book);
								}
							},
							ButtonType.OK, ButtonType.CANCEL
					);
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(deleteButton);
				}
			}
		});

		bookTable.setItems(Model.getInstance().getBooks());
	}

	/**
	 * Gère la fermeture de l'application.
	 *
	 * @param event évènement / action utilisateur.
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
	 * Gère l'ouverture d'un fichier XML contenant des livres.
	 *
	 * @param event évènement / action utilisateur.
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
	 * Gère la sauvegarde de la bibliothèque.
	 *
	 * @param event évènement / action utilisateur.
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
	 * Gère la sauvegarde de la bibliothèqye dans un nouveau fichier XML.
	 *
	 * @param event évènement / action utilisateur.
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
	 * Gère l'exportation de la bibliothèque en un fichier PDF.
	 *
	 * @param event évènement / action utilisateur.
	 */
	@FXML
	void exportToPDF(ActionEvent event) {
		if (books.isEmpty()) {
			AlertManager.showAlert(Alert.AlertType.ERROR, bundle.getString("pdfExport.error.title"),
					bundle.getString("pdfExport.error.header"), bundle.getString("pdfExport.error.message"));
			LOGGER.info("Empty book list, cannot export to PDF!");
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
				// Variables globales
				int mainFontSize = 26;
				int titleFontSize = 20;
				PDFont titleFont = PDType1Font.HELVETICA_BOLD;
				PDFont smallFont = PDType1Font.HELVETICA;

				// 1. Page de couverture
				PDPage titlePage = new PDPage(PDRectangle.A4);
				document.addPage(titlePage);
				String mainTitle = bundle.getString("pdfExport.titlePage.title");
				float mainTitleWidth = titleFont.getStringWidth(mainTitle) / 1000 * mainFontSize;
				float mainTitleHeight = titleFont.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * mainFontSize;
				try (PDPageContentStream contentStream = new PDPageContentStream(document, titlePage)) {
					contentStream.beginText();
					contentStream.setFont(titleFont, mainFontSize);
					contentStream.newLineAtOffset((titlePage.getMediaBox().getWidth() - mainTitleWidth) / 2, (titlePage.getMediaBox().getHeight() / 2) - (mainTitleHeight / 2));
					contentStream.showText(mainTitle);
					contentStream.endText();

					File imgFile = new File("src/main/resources/dev/school/app/biblioapp/images/biblioapp.png");
					if (imgFile.exists()) {
						PDImageXObject img = PDImageXObject.createFromFileByExtension(imgFile, document);
						int imgWidth = 250;
						int imgHeight = 150;
						contentStream.drawImage(img, (titlePage.getMediaBox().getWidth() - imgWidth) / 2, (float) ((titlePage.getMediaBox().getHeight() / 2) + (mainTitleHeight * 1.5)), imgWidth, imgHeight);
					} else {
						LOGGER.log(Level.SEVERE, "Image not found: " + imgFile);
					}
				}

				// 2. Génération des pages des livres
				List<PDPage> bookPages = new ArrayList<>();
				for (Book book : books) {
					PDPage page = new BookPage(document, book).getPage();
					bookPages.add(page);
				}

				// 3. Page sommaire avec liens cliquables
				PDPage tocPage = new PDPage(PDRectangle.A4);
				document.getPages().insertAfter(tocPage, document.getPage(0));

				try (PDPageContentStream contentStream = new PDPageContentStream(document, tocPage)) {
					float startY = 750;
					contentStream.setFont(titleFont, titleFontSize);
					contentStream.beginText();
					contentStream.newLineAtOffset(50, startY);
					contentStream.showText(bundle.getString("pdfExport.tocPage.title"));
					contentStream.endText();
					contentStream.setFont(smallFont, 14);
					startY -= 40;

					for (int i = 0; i < books.size(); i++) {
						Book book = books.get(i);
						PDPage bookPage = bookPages.get(i);
						int pageNumber = document.getPages().indexOf(bookPage) + 1;

						String title = book.getTitle();
						String pageStr = String.valueOf(pageNumber);
						float titleWidth = smallFont.getStringWidth(title) / 1000 * 14;
						float pageWidth = smallFont.getStringWidth(pageStr) / 1000 * 14;
						float dotsWidth = smallFont.getStringWidth(".") / 1000 * 14;

						int dotCount = (int) ((500 - titleWidth - pageWidth) / dotsWidth);
						if (dotCount < 0) dotCount = 0;

						String dots = ".".repeat(dotCount);
						String line = title + dots + pageStr;
						float textY = startY - (i * 20f);

						contentStream.beginText();
						contentStream.newLineAtOffset(50, textY);
						contentStream.showText(line);
						contentStream.endText();

						PDPageDestination dest = new PDPageFitDestination();
						dest.setPage(bookPage);
						PDActionGoTo action = new PDActionGoTo();
						action.setDestination(dest);

						PDAnnotationLink link = new PDAnnotationLink();
						PDRectangle linkRect = new PDRectangle(50, textY - 3, 500, 15);
						link.setRectangle(linkRect);
						link.setAction(action);
						tocPage.getAnnotations().add(link);
					}
				}

				// 4. Page avec tableau de livres empruntés
				PDPage borrowedPage = new PDPage(PDRectangle.A4);
				document.addPage(borrowedPage);

				List<Book> borrowedBooks = books.stream().filter(Book::isBorrowed).toList();
				float leading = 1.5f * 10;
				float margin = 50;
				float yStart = 750;
				float minY = 50;
				float tableWidth = PDRectangle.A4.getWidth() - 2 * margin;
				float[] colPercents = {0.2f, 0.2f, 0.45f, 0.15f};
				float[] colWidths = new float[colPercents.length];
				for (int i = 0; i < colPercents.length; i++) {
					colWidths[i] = tableWidth * colPercents[i];
				}
				String[] headers = {"Titre", "Auteur", "Description", "Année"};

				String sectionTitle = bundle.getString("pdfExport.borrowedPage.title");
				float y = yStart;

				BiFunction<String, Float, List<String>> wrapText = (text, width) -> {
					List<String> lines = new ArrayList<>();
					String[] words = text.split(" ");
					StringBuilder line = new StringBuilder();
					for (String word : words) {
						String testLine = line.isEmpty() ? word : line + " " + word;
						try {
							float w = smallFont.getStringWidth(testLine) / 1000 * 10;
							if (w > width) {
								lines.add(line.toString());
								line = new StringBuilder(word);
							} else {
								line = new StringBuilder(testLine);
							}
						} catch (IOException e) {
							lines.add(line.toString());
							line = new StringBuilder(word);
						}
					}
					if (!line.isEmpty()) lines.add(line.toString());
					return lines;
				};

				int rowIdx = 0;
				while (rowIdx < borrowedBooks.size()) {
					try (PDPageContentStream contentStream = new PDPageContentStream(document, borrowedPage)) {
						contentStream.setFont(titleFont, titleFontSize);
						y -= 10;
						contentStream.beginText();
						contentStream.newLineAtOffset(margin, y);
						contentStream.showText(sectionTitle);
						contentStream.endText();
						y -= 30;

						contentStream.setFont(titleFont, 10);
						float textX = margin + 2;
						float textY = y - 10;
						for (int i = 0; i < headers.length; i++) {
							contentStream.beginText();
							contentStream.newLineAtOffset(textX, textY);
							contentStream.showText(headers[i]);
							contentStream.endText();
							textX += colWidths[i];
						}
						y -= 20;

						contentStream.setFont(smallFont, 10);

						while (rowIdx < borrowedBooks.size()) {
							Book book = borrowedBooks.get(rowIdx);
							String[] row = {
									book.getTitle(),
									book.getAuthorFirstName() + " " + book.getAuthorLastName(),
									book.getDescription(),
									String.valueOf(book.getPublicationYear())
							};

							List<List<String>> wrappedCells = new ArrayList<>();
							int maxLines = 1;
							for (int i = 0; i < row.length; i++) {
								List<String> wrapped = wrapText.apply(row[i], colWidths[i] - 4);
								wrappedCells.add(wrapped);
								if (wrapped.size() > maxLines) maxLines = wrapped.size();
							}

							float rowHeight = leading * maxLines + 5;
							if (y - rowHeight < minY) break;

							for (int line = 0; line < maxLines; line++) {
								textX = margin + 2;
								textY = y - 10;
								for (int i = 0; i < wrappedCells.size(); i++) {
									String txt = line < wrappedCells.get(i).size() ? wrappedCells.get(i).get(line) : "";
									contentStream.beginText();
									contentStream.newLineAtOffset(textX, textY);
									contentStream.showText(txt);
									contentStream.endText();
									textX += colWidths[i];
								}
								y -= leading;
							}

							contentStream.moveTo(margin, y);
							contentStream.lineTo(margin + tableWidth, y);
							contentStream.stroke();
							y -= 5;

							rowIdx++;
						}
					} catch (IOException e) {
						LOGGER.log(Level.SEVERE, e.getMessage(), e);
					}

					if (rowIdx < borrowedBooks.size()) {
						borrowedPage = new PDPage(PDRectangle.A4);
						document.addPage(borrowedPage);
						y = yStart;
					}
				}

				// 5. En-têtes
				String headerText = MessageFormat.format(bundle.getString("pdfExport.header.export"), file.getName(), java.time.LocalDate.now());
				for (int i = 2; i < document.getNumberOfPages(); i++) {
					PDPage page = document.getPage(i);
					try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
						contentStream.beginText();
						contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
						contentStream.newLineAtOffset(50, 800);
						contentStream.showText(headerText);
						contentStream.endText();
					}
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
	 * Gère la modification d'un {@Book} en ouvrant une popup.
	 *
	 * @param selectedBook l'objet {@Book} à modifier.
	 */
	private void onEditBook(Book selectedBook) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/dev/school/app/biblioapp/fxml/bookdialog.fxml"),
					bundle
			);
			AnchorPane pane = loader.load();

			BookDialogController controller = loader.getController();
			Stage dialogStage = new Stage();
			dialogStage.setTitle(bundle.getString("book.dialog.title.edit"));
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setScene(new Scene(pane));
			controller.setDialogStage(dialogStage);
			controller.setBook(selectedBook, true);

			dialogStage.showAndWait();

			if (controller.isSaved()) {
				bookTable.refresh();
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to load BookDialog.fxml: " + e.getMessage(), e);
		}
	}

	/**
	 * Parse un fichier XML en une liste d'objets {@Book}.
	 *
	 * @param file fichier XML à parser.
	 * @return une liste d'objets {@Book}.
	 * @throws Exception si le fichier XML ne contient pas de livres valides.
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
	 * Sauvegarde les livres chargés dans la bibliothèque dans un fichier XML.
	 *
	 * @param file le fichier XML de sauvegarde.
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
