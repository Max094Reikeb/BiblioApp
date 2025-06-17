package dev.school.app.biblioapp.views;

import dev.school.app.biblioapp.Main;
import dev.school.app.biblioapp.models.Book;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookPage {

	private static final Logger LOGGER = Main.getLogger();
	private final ResourceBundle bundle = Main.getBundle();
	private final PDPage page;

	/**
	 * Classe type générant une page PDF pour un livre de la bibliothèque.
	 *
	 * @param document Document PDF.
	 * @param book     Livre dont on veut la page PDF.
	 * @throws IOException Exception générée en cas d'erreur.
	 */
	@SuppressWarnings("exports")
	public BookPage(PDDocument document, Book book) throws IOException {
		this.page = new PDPage(PDRectangle.A4);
		document.addPage(page);

		try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 14);
			contentStream.newLineAtOffset(50, 750);

			contentStream.showText(MessageFormat.format(bundle.getString("order.colon"), bundle.getString("book.title")) + " " + book.getTitle());
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText(MessageFormat.format(bundle.getString("order.colon"), bundle.getString("book.author")) + " " + book.getAuthorFirstName() + " " + book.getAuthorLastName());
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText(MessageFormat.format(bundle.getString("order.colon"), bundle.getString("book.resume")) + " " + book.getDescription());
			contentStream.newLineAtOffset(0, -20);
			contentStream.showText(MessageFormat.format(bundle.getString("order.colon"), bundle.getString("book.year.pub")) + " " + book.getPublicationYear());
			contentStream.newLineAtOffset(0, -20);
			contentStream.endText();

			if (book.getPathImage() != null && !book.getPathImage().isBlank()) {
				try {
					PDImageXObject image = loadImageFromUrl(document, book.getPathImage());
					if (image != null) {
						contentStream.drawImage(image, 50, 500, 100, 150);
					} else {
						LOGGER.log(Level.SEVERE, "Image for book " + book.getTitle() + " could not be loaded!");
					}
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Error while loading image for book " + book.getTitle() + "! " + e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * Télécharge une image d'une URL et crée un PDImageXObject pour une PDFBox.
	 *
	 * @param document Le PDDocument où l'image appartient.
	 * @param imageUrl L'URL de l'image.
	 * @return Un PDImageXObject pour l'image ou null si le téléchargement échoue.
	 * @throws IOException Exception générée en cas d'erreur pendant le téléchargement.
	 */
	private PDImageXObject loadImageFromUrl(PDDocument document, String imageUrl) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
		connection.setRequestProperty("Referer", "https://booknode.com");

		try (InputStream inputStream = connection.getInputStream()) {
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				byte[] imageBytes = inputStream.readAllBytes();
				return PDImageXObject.createFromByteArray(document, imageBytes, "image");
			} else {
				LOGGER.log(Level.SEVERE, "Unexpected response code: " + connection.getResponseCode() + "for URL: " + imageUrl);
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to load image from URL: " + imageUrl + " - " + e.getMessage(), e);
		} finally {
			connection.disconnect();
		}
		return null;
	}

	public PDPage getPage() {
		return this.page;
	}
}
